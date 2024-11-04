package com.claro.gdt.le.job.models.requests;

public class RequestAskImei {
    private String parametro;
    private String usuario;

    public RequestAskImei() {
    }

    public RequestAskImei(String parametro, String usuario) {
        this.parametro = parametro;
        this.usuario = usuario;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "{" +
                "\"parametro\":\"" + parametro + "\"," +
                "\"usuario\":\"" + usuario + "\"" +
                "}";
    }
}
