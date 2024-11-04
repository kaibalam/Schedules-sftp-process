package com.claro.gdt.le.job.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockUnblockinRequest {
    private String imsi;
    private String imei;
    private String identificacion;
    private String documento;
    private String observacion;
    private String usuario;
    private String numero;

    public BlockUnblockinRequest() {
    }

    public BlockUnblockinRequest(String imsi, String imei, String identificacion, String documento, String observacion, String usuario, String numero) {
        this.imsi = imsi;
        this.imei = imei;
        this.identificacion = identificacion;
        this.documento = documento;
        this.observacion = observacion;
        this.usuario = usuario;
        this.numero = numero;
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

    public String getidentificacion() {
        return identificacion;
    }

    public void setidentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "{" +
                "\"imsi\":\"" + imsi + "\"," +
                "\"imei\":\"" + imei + "\"," +
                "\"identificacion\":\"" + identificacion + "\"," +
                "\"documento\":\"" + documento + "\"," +
                "\"observacion\": \"" + observacion + "\"," +
                "\"usuario\": \"" + usuario + "\"," +
                "\"numero\": \"" + numero + "\"" +
                '}';
    }
}
