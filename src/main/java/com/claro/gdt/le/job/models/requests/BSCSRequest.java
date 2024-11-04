package com.claro.gdt.le.job.models.requests;

import java.util.Date;

public class BSCSRequest {
    private String numeroOrigen;
    private String imsi;
    private String imei;
    private Date fechaCarga;

    public BSCSRequest() {
    }

    public BSCSRequest(String numeroOrigen, String imsi, String imei, Date fechaCarga) {
        this.numeroOrigen = numeroOrigen;
        this.imsi = imsi;
        this.imei = imei;
        this.fechaCarga = fechaCarga;
    }

    public String getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(String numeroOrigen) {
        this.numeroOrigen = numeroOrigen;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }
}
