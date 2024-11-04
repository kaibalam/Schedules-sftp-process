package com.claro.gdt.le.job.models.responses;

public class ResponseSoap {
    private String estatus;
    private ResponseImeiQuery response;

    public ResponseSoap() {
    }

    public ResponseSoap(String estatus, ResponseImeiQuery response) {
        this.estatus = estatus;
        this.response = response;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public ResponseImeiQuery getResponse() {
        return response;
    }

    public void setResponse(ResponseImeiQuery response) {
        this.response = response;
    }
}
