package com.claro.gdt.le.job.models.requests;

public class DatosQueryExceptions {
    private String imei1;
    private String imei2;
    private String imei3;
    private String imsi1;
    private String imsi2;
    private String imsi3;
    private String fecha1;
    private String fecha2;
    private String fecha3;
    private String numero1;
    private String numero2;
    private String numero3;
    private int cantidad;


    public DatosQueryExceptions() {
    }

    public DatosQueryExceptions(String imei1, String imsi1, String fecha1, String numero1, int cantidad) {
        this.imei1 = imei1;
        this.imsi1 = imsi1;
        this.fecha1 = fecha1;
        this.numero1 = numero1;
        this.cantidad = cantidad;
    }

    public DatosQueryExceptions(String imei2, String imsi2, String fecha2, String numero2) {
        super();
        this.imei2 = imei2;
        this.imsi2 = imsi2;
        this.fecha2 = fecha2;
        this.numero2 = numero2;
    }


    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getImei3() {
        return imei3;
    }

    public void setImei3(String imei3) {
        this.imei3 = imei3;
    }

    public String getImsi1() {
        return imsi1;
    }

    public void setImsi1(String imsi1) {
        this.imsi1 = imsi1;
    }

    public String getImsi2() {
        return imsi2;
    }

    public void setImsi2(String imsi2) {
        this.imsi2 = imsi2;
    }

    public String getImsi3() {
        return imsi3;
    }

    public void setImsi3(String imsi3) {
        this.imsi3 = imsi3;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    public String getFecha3() {
        return fecha3;
    }

    public void setFecha3(String fecha3) {
        this.fecha3 = fecha3;
    }

    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getNumero2() {
        return numero2;
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }

    public String getNumero3() {
        return numero3;
    }

    public void setNumero3(String numero3) {
        this.numero3 = numero3;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DatosQueryExceptions{" +
                "imei1='" + imei1 + '\'' +
                ", imei2='" + imei2 + '\'' +
                ", imei3='" + imei3 + '\'' +
                ", imsi1='" + imsi1 + '\'' +
                ", imsi2='" + imsi2 + '\'' +
                ", imsi3='" + imsi3 + '\'' +
                ", fecha1='" + fecha1 + '\'' +
                ", fecha2='" + fecha2 + '\'' +
                ", fecha3='" + fecha3 + '\'' +
                ", numero1='" + numero1 + '\'' +
                ", numero2='" + numero2 + '\'' +
                ", numero3='" + numero3 + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
