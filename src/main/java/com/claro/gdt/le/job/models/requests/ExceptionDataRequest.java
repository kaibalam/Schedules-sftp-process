package com.claro.gdt.le.job.models.requests;

public class ExceptionDataRequest {
    private boolean status;
    private DatosQueryExceptions datos;
    private String identificacion;

    public ExceptionDataRequest() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DatosQueryExceptions getDatos() {
        return datos;
    }

    public void setDatos(DatosQueryExceptions datos) {
        this.datos = datos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return "ExceptionDataRequest{" +
                "status=" + status +
                ", datos=" + datos +
                ", identificacion='" + identificacion + '\'' +
                '}';
    }
}
