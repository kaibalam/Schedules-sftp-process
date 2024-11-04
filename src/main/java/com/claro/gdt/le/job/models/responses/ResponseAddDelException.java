package com.claro.gdt.le.job.models.responses;

public class ResponseAddDelException {
    private ResultMessage result;
    private String status;

    public ResponseAddDelException() {
    }

    public ResponseAddDelException(ResultMessage result, String status) {
        this.result = result;
        this.status = status;
    }

    public ResultMessage getResult() {
        return result;
    }

    public void setResult(ResultMessage result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseAddDelException{" +
                "result=" + result +
                ", status='" + status + '\'' +
                '}';
    }
}
