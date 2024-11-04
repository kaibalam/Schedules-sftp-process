package com.claro.gdt.le.job.models.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricResponse{
  private String estatus;
  private List<HistoricDataResponse> data;

    public HistoricResponse() {
    }

    public String isEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<HistoricDataResponse> getData() {
        return data;
    }

    public void setData(List<HistoricDataResponse> data) {
        this.data = data;
    }
}
