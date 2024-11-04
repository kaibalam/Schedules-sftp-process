package com.claro.gdt.le.job.models.requests;

import java.util.Date;

public class LogRegisterRequest {
    private Integer idLog;
    private String msisdn;
    private String imei;
    private String imsi;
    private String codCountry;
    private Date dateProcess;
    private String action;
    private String requestData;
    private String responseData;

    public LogRegisterRequest() {
    }

    public LogRegisterRequest(String msisdn, String imei, String imsi, String codCountry, Date dateProcess, String action, String requestData, String responseData) {
        this.msisdn = msisdn;
        this.imei = imei;
        this.imsi = imsi;
        this.codCountry = codCountry;
        this.dateProcess = dateProcess;
        this.action = action;
        this.requestData = requestData;
        this.responseData = responseData;
    }

    public LogRegisterRequest(Integer idLog, String msisdn, String imei, String imsi, String codCountry, Date dateProcess, String action, String requestData, String responseData) {
        this.idLog = idLog;
        this.msisdn = msisdn;
        this.imei = imei;
        this.imsi = imsi;
        this.codCountry = codCountry;
        this.dateProcess = dateProcess;
        this.action = action;
        this.requestData = requestData;
        this.responseData = responseData;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getCodCountry() {
        return codCountry;
    }

    public void setCodCountry(String codCountry) {
        this.codCountry = codCountry;
    }

    public Date getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(Date dateProcess) {
        this.dateProcess = dateProcess;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
