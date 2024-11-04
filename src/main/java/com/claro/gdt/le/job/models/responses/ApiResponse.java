package com.claro.gdt.le.job.models.responses;




import com.claro.gdt.le.job.configurations.ApiDataSerializer;
import com.claro.gdt.le.job.models.enums.ApiResponseType;
import com.claro.gdt.le.job.models.enums.ApiStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ApiResponse {
    private ApiResponseType responseType;
    private ApiStatus status;
    private String errorCode;
    private String errorDescription;
    private ApiData data;

    public ApiResponse() {
    }

    public ApiResponse(ApiResponseType responseType, ApiStatus status, String errorCode, String errorDescription,
                       ApiData data) {
        this.responseType = responseType;
        this.status = status;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.data = data;
    }

    public ApiResponseType getResponseType() {
        return responseType;
    }

    public ApiResponse setResponseType(ApiResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public ApiStatus getStatus() {
        return status;
    }

    public ApiResponse setStatus(ApiStatus status) {
        this.status = status;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getErrorCode() {
        return errorCode;
    }

    public ApiResponse setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getErrorDescription() {
        return errorDescription;
    }

    public ApiResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = ApiDataSerializer.class)
    public ApiData getData() {
        return data;
    }

    public ApiResponse setData(ApiData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{responseType=%s, status=%s, errorCode=%s, errorDescription=%s, data=%s}", responseType,
                status, errorCode, errorDescription, data);
    }
}
