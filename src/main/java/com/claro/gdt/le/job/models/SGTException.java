package com.claro.gdt.le.job.models;


import com.claro.gdt.le.job.models.responses.ApiResponse;

public class SGTException extends Exception {

    private static final long serialVersionUID = -5106569310303427930L;

    private ApiResponse apiResponse;

    public SGTException(ApiResponse apiResponse){
        super(apiResponse.getErrorDescription());
        this.apiResponse = apiResponse;
    }

    public SGTException() {
        super();
    }

    public SGTException(String message, ApiResponse apiResponse) {
        super(message);
        this.apiResponse = apiResponse;
    }

    public SGTException(String message, Throwable cause, ApiResponse apiResponse) {
        super(message, cause);
        this.apiResponse = apiResponse;
    }

    public SGTException(Throwable cause, ApiResponse apiResponse) {
        super(cause);
        this.apiResponse = apiResponse;
    }

    public SGTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ApiResponse apiResponse) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.apiResponse = apiResponse;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }
}
