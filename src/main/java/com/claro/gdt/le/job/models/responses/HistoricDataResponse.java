package com.claro.gdt.le.job.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricDataResponse {
    private String ID_HISTORIAL;
    private String TRANSACCION;
    private String IDENTIFICACION;
    private String MOTIVO;
    private String NUMERO;
    private String FECHA;
    private String ID_ONBASE;
    private String IMEI;
    private String OBSERVACION;
    private String PLATAFORMA;
    private String USUARIO;
    private String PAIS;

    public HistoricDataResponse() {
    }

    public HistoricDataResponse(String TRANSACCION, String IMEI, String FECHA, String IDENTIFICACION) {
        this.TRANSACCION = TRANSACCION;
        this.IMEI = IMEI;
        this.FECHA= FECHA;
        this.IDENTIFICACION = IDENTIFICACION;
    }

    public String getID_HISTORIAL() {
        return ID_HISTORIAL;
    }

    public void setID_HISTORIAL(String ID_HISTORIAL) {
        this.ID_HISTORIAL = ID_HISTORIAL;
    }

    public String getTRANSACCION() {
        return TRANSACCION;
    }

    public void setTRANSACCION(String TRANSACCION) {
        this.TRANSACCION = TRANSACCION;
    }

    public String getIDENTIFICACION() {
        return IDENTIFICACION;
    }

    public void setIDENTIFICACION(String IDENTIFICACION) {
        this.IDENTIFICACION = IDENTIFICACION;
    }

    public String getMOTIVO() {
        return MOTIVO;
    }

    public void setMOTIVO(String MOTIVO) {
        this.MOTIVO = MOTIVO;
    }

    public String getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(String NUMERO) {
        this.NUMERO = NUMERO;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getID_ONBASE() {
        return ID_ONBASE;
    }

    public void setID_ONBASE(String ID_ONBASE) {
        this.ID_ONBASE = ID_ONBASE;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public void setOBSERVACION(String OBSERVACION) {
        this.OBSERVACION = OBSERVACION;
    }

    public String getPLATAFORMA() {
        return PLATAFORMA;
    }

    public void setPLATAFORMA(String PLATAFORMA) {
        this.PLATAFORMA = PLATAFORMA;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }
}
