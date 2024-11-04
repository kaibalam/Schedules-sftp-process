package com.claro.gdt.le.job.services.imp;

import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.mappermod.DictionaryMapper;
import com.claro.gdt.le.job.models.mappermod.TagsResponseMapper;
import com.claro.gdt.le.job.models.requests.*;
import com.claro.gdt.le.job.models.responses.*;
import com.claro.gdt.le.job.services.IDictionaryDAO;
import com.jcraft.jsch.JSchException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.claro.gdt.le.job.utils.Constants.*;

@Component
public class IDictionaryDAOImp implements IDictionaryDAO {
    private static final Logger LOG = LoggerFactory.getLogger(IDictionaryDAOImp.class);

    private static final String ADD_TAG = "Agregado Lista Excepciones";
    private static final String DEL_TAG = "Retirado Lista Excepciones";

    private int procesado = 0;
    private int noProcesaro = 0;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public IDictionaryDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DictionaryRequest> getAllDictionaryTags() throws SGTException {
        String sqlQuery = "SELECT * FROM TBL_GDT_DICCIONARIO";
        return jdbcTemplate.query(sqlQuery, new DictionaryMapper());
    }

    @Override
    public List<TagsValuesResponse> getAllTags() throws SGTException {
        String sql = "select DIC_TAG, DIC_VALOR from TBL_GDT_DICCIONARIO";
        return jdbcTemplate.query(sql, new TagsResponseMapper());
    }

    @Override
    public Integer getNewValidation(String file, String type) throws SGTException {
        String sql = String.format("SELECT COUNT(NOMBRE_ARCHIVO) AS VALOR FROM TBL_GDT_ARCHIVOS_SUTEL WHERE NOMBRE_ARCHIVO = '%s' AND TIPO_ARCHIVO = '%s'", file, type);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    public Integer saveNewFile(String fileName, String type) throws SGTException {
        return jdbcTemplate.update("INSERT INTO TBL_GDT_ARCHIVOS_SUTEL(NOMBRE_ARCHIVO,TIPO_ARCHIVO,FECHA) VALUES(?,?,SYSDATE)",
                fileName, type);
    }

    @Override
    public void mainTask(Map<String, String> mapTags, String filePath) throws SGTException, JSchException, FileNotFoundException {
        LOG.info("Starts readh files");
        File file = null;
        FileReader reader = null;
        Map<String, Integer> procesados = new HashMap<>();

        String imei = null;
        String msisdn = null;
        String imsi = null;
        String excepCause = null;
        String obsrns = null;
        String operator = null;
        String status = null;
        String userId = null;

        int cantImei = 0;
        int maxImei = 0;
        maxImei = Integer.parseInt(mapTags.get("GDT_MAXIMEI_LSINC"));
        file = new File(filePath);
        LOG.info("file paht es {}", filePath);
        reader = new FileReader(file);
        LOG.info("reader inicializado {}",reader);
        try (BufferedReader br = new BufferedReader(reader)) {

            LOG.info("iniciando buffer reader");
            String line;
            while ((line = br.readLine()) != null) {
                LOG.info(line);
                boolean isNumber = validado(line);
                if (isNumber) {
                    cantImei = Integer.parseInt(line);
                    LOG.info("cantidad lineas a procesar {}", cantImei);
                    if (cantImei >= maxImei) {
                        return;
                    }
                } else {
                    boolean ends = line.contains("END");
                    if (ends) {
                        LOG.info("Es la última linea, salir");
                        return;
                    }
                    LOG.info("=========================== Inicio de proceso de lectura de linea ===========================");
                    String[] lineObj = line.split("\\|");
                    imei = lineObj[0];
                    msisdn = lineObj[1];
                    imsi = lineObj[2];
                    excepCause = lineObj[3];
                    obsrns = lineObj[4];
                    operator = lineObj[5];
                    status = lineObj[6];
                    userId = lineObj[7];

                    Map<String, Boolean> isValidLine = fieldLenthgValidation(lineObj, mapTags);
                    msisdn = msisdn.length() == 8 ? "506".concat(msisdn) : msisdn;
                    String counryCode = msisdn.length() > 8 && msisdn.length() <= 11 ? msisdn.substring(0, 3) : "";

                    Date processDate = new Date();
                    boolean isSaved;
                    if (isValidLine.get(INVALID_IMEI) || isValidLine.get(INVALID_MSISDN) || isValidLine.get(INVALID_IMSI) || isValidLine.get(INVALID_OP) || isValidLine.get(INVALID_STS)) {
                        LOG.info("Validación, parametro errado, no operar ---> ignorar {}", isValidLine);
                        if (operator.equals("1921") && Boolean.TRUE.equals(isValidLine.get(INVALID_STS))){
                            LOG.info(!status.isEmpty() ? "estado viene poblado continua" : "estado viene vacio ignorar");
                            LogRegisterRequest req = new LogRegisterRequest(msisdn, imei, imsi, counryCode, processDate, "No operado", isValidLine.toString(), "No se operó estado viene vació o es invalido ");
                            isSaved = saveLog(req);
                        }
                    } else {
                        LOG.info("is valid line params : {}", isValidLine);
                        LOG.info("Operador a validar = " + operator + " : " + (!operator.equals("1921") ? "No es Claro ignorar" : "Es Claro continua"));
                        if (operator.equals("1921")) {
                            LOG.info(!status.isEmpty() ? "estado viene poblado continua" : "estado viene vacio ignorar");
                        }
                        if (operator.equals("1921") && !status.isEmpty()) {
                            LOG.info("Consultar excepciones");
                            String wholeImei = null;
                            if(imei.length() == 14) {
                                wholeImei = calcImei(imei);
                            } else {
                                wholeImei = imei;
                            }
                            ExceptionDataRequest queryExceptions = askExceptions(wholeImei, mapTags);

                            if (queryExceptions.getDatos().getCantidad() >= 0 || queryExceptions.getDatos().getCantidad() < 3) {
                                LOG.info("Consulta historial");
                                HistoricResponse historicRes = askHistory(wholeImei, mapTags);
                                if (historicRes.getData().isEmpty()) {
                                    if (status.equals("0")) {
                                        procesado++;
                                        LOG.info("estado es 0 agregar excepcion, inicia proceso de agregar lista de excepciones");
                                        BlockUnblockinRequest blockUnblockinRequest = new BlockUnblockinRequest(imsi, wholeImei, userId, "", obsrns, "APLICACION_CRM_PERIFERICOS", msisdn);
                                        LOG.info("payload para enviar {}", blockUnblockinRequest);
                                        ResponseAddDelException blockRes = addException(mapTags, blockUnblockinRequest);
                                        LOG.info("Agregado a lista de excepciones");
                                        LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "Agregado Lista Excepciones", blockUnblockinRequest.toString(), blockRes.toString());
                                        isSaved = saveLog(req);
                                        LOG.info("Guardando historial {}", wholeImei);
                                    } else {
                                        procesado++;
                                        LOG.info("estado 1 retirar de la lista de excepciones, iniciando proceso de retirado lista de excepciones");
                                        BlockUnblockinRequest blockingRequest = new BlockUnblockinRequest(imsi, wholeImei, userId, "", obsrns, "APLICACION_CRM_PERIFERICOS", msisdn);
                                        LOG.info("payload para enviar {}", blockingRequest);
                                        ResponseAddDelException unblock = delException(mapTags, blockingRequest);
                                        LOG.info("Retirado lista de excepciones");
                                        LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "Retirado Lista Excepciones", blockingRequest.toString(), unblock.toString());
                                        isSaved = saveLog(req);
                                        LOG.info("Guardado historial {}", wholeImei);
                                    }
                                } else {
                                    LOG.info("El imei posee historial validar ultimos movimientos y ultimas imsis");
                                    List<HistoricDataResponse> addDelList = new ArrayList<>();
                                    historicRes.getData()
                                            .forEach(f -> {
                                                HistoricDataResponse addMe = new HistoricDataResponse(f.getTRANSACCION(), f.getIMEI(), f.getFECHA(),f.getIDENTIFICACION());
                                                if (f.getIDENTIFICACION() != null && (f.getTRANSACCION().equals(ADD_TAG) || f.getTRANSACCION().equals(DEL_TAG))) {
                                                    addDelList.add(addMe);
                                                }
                                            });
                                    addDelList.sort(Comparator.comparing(HistoricDataResponse::getFECHA));
                                    int ultimoValor = addDelList.size() - 1;
                                    HistoricDataResponse lasReg = addDelList.get(ultimoValor);
                                    String transaction = lasReg.getTRANSACCION();
                                    boolean lastImsi = false;
                                    if (queryExceptions.getDatos().getCantidad() >= 0) {
                                        lastImsi = validateLastImsi(queryExceptions.getDatos(), imsi, msisdn);
                                    }
                                    int cantidadExcepciones = queryExceptions.getDatos().getCantidad();

                                    switch (status) {
                                        case "0":
                                            /*
                                            Si se desea agregar otra excepción se podrá si la IMSI no esta entre las agregadas anteriormente
                                            y si si la cantidad de excepciones maxima lo permite
                                            */
                                            String motivoFallo = "";

                                            if ((!transaction.equals(ADD_TAG) && !lastImsi && cantidadExcepciones < 3) ||
                                                    (transaction.equals(ADD_TAG) && !lastImsi  && cantidadExcepciones < 3)) {
                                                //Agregar Lista Excepciones
                                                BlockUnblockinRequest blockingRequest = new BlockUnblockinRequest(imsi, wholeImei, userId, "", obsrns, "APLICACION_CRM_PERIFERICOS", msisdn);
                                                ResponseAddDelException request = addException(mapTags, blockingRequest);
                                                if (request.getStatus().equals("false")){
                                                    LOG.info("Intento exitoso, No se agregó a la lista con HI, status {}",request.getStatus());
                                                    LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "No se agregó a lista", blockingRequest.toString(), request.toString());
                                                    isSaved = saveLog(req);
                                                    LOG.info("Guardado historial {}", imei);
                                                } else {
                                                    LOG.info("agregado a lista de excepiones con HI, status {}",request.getStatus());
                                                    LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "Agregado Lista Excepciones", blockingRequest.toString(), request.toString());
                                                    isSaved = saveLog(req);
                                                    LOG.info("Guardado historial {}", imei);
                                                }
                                            } else {
                                                LOG.info("No se agrego a lista de excepciones ya tiene agregada una excepcion con la IMSI solicitada {}", imsi);
                                                motivoFallo = String.format("No se agregó a la lista de excepciones por algúno de los siguientes motivos: Ultima transaccion = %s, Ultima IMSI existia = %s, limite de excepciones = %s", transaction, lastImsi, cantidadExcepciones);
                                                LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "No se agregó a lista", motivoFallo, null);
                                                isSaved = saveLog(req);
                                            }
                                            break;
                                        case "1":
                                            if ((transaction.equals(DEL_TAG) && lastImsi  && cantidadExcepciones > 0) ||
                                                    (transaction.equals(ADD_TAG) && lastImsi && cantidadExcepciones > 0)) {
                                                //Retirar Lista Excepciones
                                                BlockUnblockinRequest blockingRequest = new BlockUnblockinRequest(imsi, wholeImei, userId, "", obsrns, "APLICACION_CRM_PERIFERICOS", msisdn);
                                                ResponseAddDelException request = delException(mapTags, blockingRequest);
                                                if (request.getStatus().equals("false")){
                                                    LOG.info("No se retiró de lista de excepciones con HI, status {}",request.getStatus());
                                                    LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "No se retiró de la lista", blockingRequest.toString(), request.toString());
                                                    isSaved = saveLog(req);
                                                    LOG.info("Guardado historial {}", wholeImei);
                                                } else {
                                                    LOG.info("retirado de la lista de excepiones con HI {}", wholeImei);
                                                    LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "Retirado Lista Excepciones", blockingRequest.toString(), request.toString());
                                                    isSaved = saveLog(req);
                                                    LOG.info("Guardado historial {}", wholeImei);
                                                }
                                            } else {
                                                LOG.info("No se retiró de la lista de excepciones ya que la IMSI solicitada no la tiene agregada: {}", imsi);
                                                motivoFallo = String.format("No se retiró de la lista de excepciones por algúno de los siguientes motivos: Ultima transaccion = %s, IMSI no existe en excepciones = %s, excepcion es 0 = %s", transaction, lastImsi, cantidadExcepciones);
                                                LogRegisterRequest req = new LogRegisterRequest(msisdn, wholeImei, imsi, counryCode, processDate, "No se retiró de la lista", motivoFallo, null);
                                                isSaved = saveLog(req);
                                            }
                                            break;
                                    }
                                }
                            }
                        } else if (operator.equals("1921")) {
                            noProcesaro++;
                            LogRegisterRequest req = new LogRegisterRequest(msisdn, imei, imsi, counryCode, processDate, "No_procesado", null, isValidLine.toString());
                            isSaved = saveLog(req);
                            LOG.info("Registro no procesado {}", imei);
                            LOG.info("No procesados");
                        }
                        LOG.info("=========================== Fin de proceso de lectura de linea ===========================");
                        sleepyTime();
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        procesados.put("procesados", procesado);
        procesados.put("noProcesados", noProcesaro);
        LOG.info("procesados: {}", procesados.get("procesados"));
        LOG.info("no procesados: {}", procesados.get("noProcesados"));

    }
    private static void sleepyTime() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveLog(LogRegisterRequest rq) throws SGTException {
        String sql = "select max(id_log) as id from log_gdt";
        int id = jdbcTemplate.queryForObject(sql, Integer.class);
        id++;
        return jdbcTemplate.update("insert into log_gdt (id_log,numero,imei,imsi,cod_pais,fecha,accion,request,response)values (?,?,?,?,?,?,?,?,?)",
                id, rq.getMsisdn(), rq.getImei(), rq.getImsi(), rq.getCodCountry(), rq.getDateProcess(), rq.getAction(),
                rq.getRequestData(), rq.getResponseData()) > 0;
    }

    public boolean validado(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '|'))) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Boolean> fieldLenthgValidation(String[] data, Map<String, String> tags) {

        Map<String, Boolean> valid = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            switch (i) {
                case 0:
                    if (data[i].length() == Integer.parseInt(tags.get("GDT_FISI_IMEI")) && isValidNumber(data[i])) {
                        valid.put(INVALID_IMEI, false);
                    } else {
                        valid.put(INVALID_IMEI, true);
                    }
                case 1:
                    if ((data[1].length() >= Integer.parseInt(tags.get("GDT_FISI_DNMIN")) && data[1].length() <= Integer.parseInt(tags.get("GDT_FISI_DNMAX"))) && isValidNumber(data[1])) {
                        valid.put(INVALID_MSISDN, false);
                    } else {
                        valid.put(INVALID_MSISDN, true);
                    }
                case 2:
                    if (data[2].length() >= Integer.parseInt(tags.get("GDT_FISI_IMSIMIN")) && data[2].length() <= Integer.parseInt(tags.get("GDT_FISI_IMSIMAX")) && isValidNumber(data[2])) {
                        valid.put(INVALID_IMSI, false);
                    } else {
                        valid.put(INVALID_IMSI, true);
                    }
                case 3:
                    if (data[3].length() >= Integer.parseInt(tags.get("GDT_FISI_CAUMIN")) && data[3].length() >= Integer.parseInt(tags.get("GDT_FISI_CAUMAX"))) {
                        valid.put("Invalid CAUSE_EXCEPTION", false);
                    } else {
                        valid.put("Invalid CAUSE_EXCEPTION", true);
                    }
                case 4:
                    if (data[4].length() >= Integer.parseInt(tags.get("GDT_FISI_OBSMIN")) && data[4].length() <= Integer.parseInt(tags.get("GDT_FISI_OBSMAX"))) {
                        valid.put("Invalid OBSERVATIONS", false);
                    } else {
                        valid.put("Invalid OBSERVATIONS", true);
                    }
                case 5:
                    if (data[5].length() >= Integer.parseInt(tags.get("GDT_FISI_OPRMAX")) && isValidNumber(data[5])) {
                        valid.put(INVALID_OP, false);
                    } else {
                        valid.put(INVALID_OP, true);
                    }
                case 6:
                    if (isValidStatus(data[6])) {
                        valid.put(INVALID_STS, false);
                    } else {
                        valid.put(INVALID_STS, true);
                    }
                case 7:
                    if (data[7].length() >= Integer.parseInt(tags.get("GDT_FISI_IDUMIN")) && data[7].length() <= Integer.parseInt(tags.get("GDT_FISI_IDUMAX"))) {
                        valid.put("Invalid USER_ID", false);
                    } else {
                        valid.put("Invalid USER_ID", true);
                    }
                default:
                    return valid;
            }
        }
        return valid;
    }

    public ExceptionDataRequest askExceptions(String imei, Map<String, String> tags) {
        String resp = "";
        String jsonRes = "";
        Client client = Client.create().create();
        WebResource rootUri = client.resource(tags.get("GDT_WSWEB_SRC").concat(tags.get("GDT_WSASK_EXC"))).queryParam("imei",imei);
        ClientResponse res = rootUri.header("Content-Type","application/json").get(ClientResponse.class);
        resp = res.getEntity(String.class);
        ExceptionDataRequest response = new ExceptionDataRequest();
        DatosQueryExceptions queryExceptions = new DatosQueryExceptions();
        try {
            JSONObject jsonCli = new JSONObject(resp);
            response.setStatus(jsonCli.getBoolean("status"));
            response.setIdentificacion(jsonCli.getString("identificacion"));
            jsonRes = jsonCli.getString("datos");
            JSONObject jsonDatos = new JSONObject(jsonRes);
            queryExceptions.setCantidad(jsonDatos.getInt("cantidad"));
            int cant = jsonDatos.getInt("cantidad");
            switch (cant){
                case 0 :
                    queryExceptions.setFecha1(jsonDatos.getString("fecha1"));
                    queryExceptions.setFecha2(jsonDatos.getString("fecha2"));
                    queryExceptions.setFecha3(jsonDatos.getString("fecha3"));
                    queryExceptions.setImei1(jsonDatos.getString("imei1"));
                    queryExceptions.setImei2(jsonDatos.getString("imei2"));
                    queryExceptions.setImei3(jsonDatos.getString("imei3"));
                    queryExceptions.setImsi1(jsonDatos.getString("imsi1"));
                    queryExceptions.setImsi2(jsonDatos.getString("imsi2"));
                    queryExceptions.setImsi3(jsonDatos.getString("imsi3"));
                    break;
                case 1:
                    queryExceptions.setImei1(jsonDatos.getString("imei1"));
                    queryExceptions.setFecha1(jsonDatos.getString("fecha1"));
                    queryExceptions.setImsi1(jsonDatos.getString("imsi1"));
                    queryExceptions.setNumero1(jsonDatos.getString("numero1"));
                    break;
                case 2:
                    queryExceptions.setImei1(jsonDatos.getString("imei1"));
                    queryExceptions.setFecha1(jsonDatos.getString("fecha1"));
                    queryExceptions.setImsi1(jsonDatos.getString("imsi1"));
                    queryExceptions.setNumero1(jsonDatos.getString("numero1"));
                    queryExceptions.setImei2(jsonDatos.getString("imei2"));
                    queryExceptions.setFecha2(jsonDatos.getString("fecha2"));
                    queryExceptions.setImsi2(jsonDatos.getString("imsi2"));
                    queryExceptions.setNumero2(jsonDatos.getString("numero2"));
                    break;
                case 3:
                    queryExceptions.setImei1(jsonDatos.getString("imei1"));
                    queryExceptions.setFecha1(jsonDatos.getString("fecha1"));
                    queryExceptions.setImsi1(jsonDatos.getString("imsi1"));
                    queryExceptions.setNumero1(jsonDatos.getString("numero1"));
                    queryExceptions.setImei2(jsonDatos.getString("imei2"));
                    queryExceptions.setFecha2(jsonDatos.getString("fecha2"));
                    queryExceptions.setImsi2(jsonDatos.getString("imsi2"));
                    queryExceptions.setNumero2(jsonDatos.getString("numero2"));
                    queryExceptions.setImei3(jsonDatos.getString("imei3"));
                    queryExceptions.setFecha3(jsonDatos.getString("fecha3"));
                    queryExceptions.setImsi3(jsonDatos.getString("imsi3"));
                    queryExceptions.setNumero3(jsonDatos.getString("numero3"));
                    break;
            }

            response.setDatos(queryExceptions);
            LOG.info("Consulta a excepciones {}", response);
            return response;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateLastImsi(DatosQueryExceptions datos, String imsi, String msisdn) {
        if (datos.getImsi1() != null && imsi.equals(datos.getImsi1()) ||
                datos.getImsi2() != null && imsi.equals(datos.getImsi2()) ||
                datos.getImsi3() != null && imsi.equals(datos.getImsi3())) {
            return true;
        }
        if (datos.getNumero1() != null && msisdn.equals(datos.getNumero1()) ||
            datos.getNumero2() != null && msisdn.equals(datos.getNumero2()) ||
            datos.getNumero3() != null && msisdn.equals(datos.getNumero3())){
            return true;
        }
        return false;
    }

    public HistoricResponse askHistory(String imei, Map<String, String> tags) throws SGTException {
        String res = "";
        String jsonRes = "";
        Client client = Client.create();
        WebResource webResource = client.resource(tags.get("GDT_WSWEB_SRC").concat(tags.get("GDT_WSASK_HISEXC"))).queryParam("imei",imei);
        ClientResponse clientResponse = webResource.header("Content-Type","application/json").get(ClientResponse.class);
        res = clientResponse.getEntity(String.class);
        HistoricResponse hisRes = new HistoricResponse();
        List<Object[]> histList = new ArrayList<>();
        List<HistoricDataResponse> histResList = new ArrayList<>();
        HistoricDataResponse histRes = new HistoricDataResponse();
        try {
            JSONObject jsonHis = new JSONObject(res);
            hisRes.setEstatus(jsonHis.getString("estatus"));
            jsonRes = jsonHis.getString("data");
            JSONArray jsonList = new JSONArray(jsonRes);
            for(int i= 0; i<jsonList.length(); i++){
                String inRes = jsonList.getString(i);
                JSONObject inObj = new JSONObject(inRes);
                if (inObj.getString("TRANSACCION").equals(ADD_TAG) || inObj.getString("TRANSACCION").equals(DEL_TAG)){
                    histRes.setTRANSACCION(inObj.getString("TRANSACCION"));
                    histRes.setIMEI(inObj.getString("IMEI"));
                    histRes.setFECHA(inObj.getString("FECHA"));
                    histRes.setIDENTIFICACION(inObj.getString("IDENTIFICACION"));
                }
                histResList.add(histRes);
            }
            hisRes.setData(histResList);
            return hisRes;
        } catch (JSONException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public ResponseAddDelException addException(Map<String, String> mapTags, BlockUnblockinRequest request) {
        String res = "";
        String jsonRes = "";
        try{
            Client client = Client.create();
            WebResource webResource = client.resource(mapTags.get("GDT_WSWEB_SRC").concat(mapTags.get("GDT_WSADD_EXC")));
            ClientResponse response = webResource.header("Content-Type","application/json").post(ClientResponse.class,request.toString());
            LOG.info("addException response status {}",response.getStatus());
            res = response.getEntity(String.class);
            LOG.info("Entity {}",res);
            ResponseAddDelException addDelExc = new ResponseAddDelException();
            ResultMessage resMes = new ResultMessage();
            JSONObject jsonAddDel = new JSONObject(res);
            addDelExc.setStatus(jsonAddDel.getString("status"));
            if (addDelExc.getStatus().equals("true")) {
                jsonRes = jsonAddDel.getString("result");
                JSONObject jsonResult = new JSONObject(jsonRes);
                resMes.setMensaje(jsonResult.getString("mensaje"));
                resMes.setOnbase(jsonResult.getString("onbase"));
                resMes.setResultado(jsonResult.getInt("resultado"));
                addDelExc.setResult(resMes);
            }
            return addDelExc;
        } catch (ClientHandlerException e){
            LOG.error(e.getMessage());

        } catch (JSONException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public ResponseAddDelException delException(Map<String, String> mapTags, BlockUnblockinRequest request) {
        String res = "";
        String jsonRes = "";
        try{
            Client client = Client.create();
            WebResource webResource = client.resource(mapTags.get("GDT_WSWEB_SRC").concat(mapTags.get("GDT_WSDEL_EXC")));
            ClientResponse response = webResource.header("Content-Type","application/json").post(ClientResponse.class,request.toString());
            LOG.info("delException response status {}",response.getStatus());
            res = response.getEntity(String.class);
            LOG.info("Entity {}",res);
            ResponseAddDelException addDelExc = new ResponseAddDelException();
            ResultMessage resMes = new ResultMessage();
            JSONObject jsonAddDel = new JSONObject(res);
            addDelExc.setStatus(jsonAddDel.getString("status"));
            if (addDelExc.getStatus().equals("true")) {
                jsonRes = jsonAddDel.getString("result");
                JSONObject jsonResult = new JSONObject(jsonRes);
                resMes.setMensaje(jsonResult.getString("mensaje"));
                resMes.setOnbase(jsonResult.getString("onbase"));
                resMes.setResultado(jsonResult.getInt("resultado"));
                addDelExc.setResult(resMes);
            }
            return addDelExc;
        } catch (ClientHandlerException e){
            LOG.error(e.getMessage());

        } catch (JSONException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public boolean isValidNumber(String cadena) {
        boolean respuesta = true;

        if (!cadena.matches("[0-9]*")) {
            respuesta = false;
        }
        return respuesta;
    }

    public boolean isValidStatus(String cadena) {
        boolean respuesta = true;

        if (!cadena.matches("[0-1]*") || cadena.length() != 1) {
            respuesta = false;
        }
        return respuesta;
    }

    public static String calcImei(String imei){
        String im = "0";
        Integer sum = 0;
        int total = 0;
        for (int i = 0; i < imei.length(); i++){
            char test = imei.charAt(i);
            if(i%2!=0){
                sum = Integer.parseInt(String.valueOf(test)) * 2;
                String convertSum = String.valueOf(sum);
                if (convertSum.length() > 1){
                    for(int j = 0; j<convertSum.length(); j++){
                        char itm = convertSum.charAt(j);
                        total += Integer.parseInt(String.valueOf(itm));
                    }
                } else {
                    total += Integer.parseInt(convertSum);
                }
            } else {
                total += Integer.parseInt(String.valueOf(test));
            }
            if (i == imei.length()-1) {
                int nearestClose = 10 - (total % 10);
                im = imei.concat(String.valueOf(nearestClose));
            }
        }
        LOG.info("Imei calculado: {}",im);
        return im;
    }

}
