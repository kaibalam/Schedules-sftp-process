package com.claro.gdt.le.job.models.responses;

public class ResponseImeiQuery {
    private String imei;
    private String imsi;
    private String numero;
    private String respuesta;
    private String respuestaSGTM;
    private String resultOp;
    private String descripcion;
    private String cantidadExcepciones;

    public ResponseImeiQuery() {
    }

    public ResponseImeiQuery(String imei, String imsi, String numero, String respuesta, String respuestaSGTM, String resultOp, String descripcion, String cantidadExcepciones) {
        this.imei = imei;
        this.imsi = imsi;
        this.numero = numero;
        this.respuesta = respuesta;
        this.respuestaSGTM = respuestaSGTM;
        this.resultOp = resultOp;
        this.descripcion = descripcion;
        this.cantidadExcepciones = cantidadExcepciones;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuestaSGTM() {
        return respuestaSGTM;
    }

    public void setRespuestaSGTM(String respuestaSGTM) {
        this.respuestaSGTM = respuestaSGTM;
    }

    public String getResultOp() {
        return resultOp;
    }

    public void setResultOp(String resultOp) {
        this.resultOp = resultOp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidadExcepciones() {
        return cantidadExcepciones;
    }

    public void setCantidadExcepciones(String cantidadExcepciones) {
        this.cantidadExcepciones = cantidadExcepciones;
    }
}
