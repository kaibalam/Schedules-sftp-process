package com.claro.gdt.le.job.services.imp;



import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.requests.LogRegisterRequest;
import com.claro.gdt.le.job.services.LogRegisterService;
import com.claro.gdt.le.job.services.SutelDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;


@Component
public class LogRegisterServiceImp implements LogRegisterService {
    private static final Logger LOG = LoggerFactory.getLogger(LogRegisterService.class);
    private static final String ADD_TAG = "Agregado Lista Excepciones";
    private static final String DEL_TAG = "Agregado Lista Excepciones";

    private int procesado = 0;
    private int noProcesado = 0;

    private boolean isSaved = false;
    int wasProcess = 0;
    int wasSaved = 0;

    @Autowired
    private SutelDataService sutelService;

    @Autowired
    JdbcTemplate jdbcGevenueTemplate;


    @Autowired
    public LogRegisterServiceImp(DataSource dataSource) {
        jdbcGevenueTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<LogRegisterRequest> listAllLogs() throws SGTException {
        return null;
    }

    @Override
    public LogRegisterRequest findById(Integer id) throws SGTException {
        return null;
    }

    @Override
    public boolean saveLog(LogRegisterRequest rq) throws SGTException {
        String sql = "select max(id_log) as id from log_gdt";
        int id = jdbcGevenueTemplate.queryForObject(sql, Integer.class);
        id++;
        return jdbcGevenueTemplate.update("insert into log_gdt (id_log,numero,imei,imsi,cod_pais,fecha,accion,request,response)values (?,?,?,?,?,?,?,?,?)",
                id, rq.getMsisdn(), rq.getImei(), rq.getImsi(), rq.getCodCountry(), rq.getDateProcess(), rq.getAction(),
                rq.getRequestData(), rq.getResponseData()) > 0;
    }



}
