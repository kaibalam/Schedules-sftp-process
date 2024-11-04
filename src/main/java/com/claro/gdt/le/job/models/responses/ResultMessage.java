package com.claro.gdt.le.job.models.responses;

public class ResultMessage {
    private String mensaje;
    private String onbase;
    private Integer resultado;

    public ResultMessage() {
    }

    public ResultMessage(String mensaje, String onbase, Integer resultado) {
        this.mensaje = mensaje;
        this.onbase = onbase;
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getOnbase() {
        return onbase;
    }

    public void setOnbase(String onbase) {
        this.onbase = onbase;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "mensaje='" + mensaje + '\'' +
                ", onbase='" + onbase + '\'' +
                ", resultado=" + resultado +
                '}';
    }
}
