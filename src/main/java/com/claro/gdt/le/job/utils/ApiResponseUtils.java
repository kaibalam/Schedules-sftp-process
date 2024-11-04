package com.claro.gdt.le.job.utils;


import com.claro.gdt.le.job.models.enums.ApiResponseType;
import com.claro.gdt.le.job.models.enums.ApiStatus;
import com.claro.gdt.le.job.models.responses.ApiData;
import com.claro.gdt.le.job.models.responses.ApiResponse;

public class ApiResponseUtils {

    public static final String ERR_0400 = "ERR_0400";
    public static final String ERR_0404 = "ERR_0404";
    public static final String ERR_0500 = "ERR_0500";
    public static final String ERR_9999 = "ERR_9999";

    private ApiResponseUtils() {
        // should never be initialized
    }

    public static ApiResponse success(ApiResponseType responseType, String name, Object data) {
        return new ApiResponse(responseType, ApiStatus.SUCCESS, null, null, new ApiData(name, data));
    }

    public static ApiResponse error(ApiResponseType responseType, String errorCode, String errorDescription) {
        return new ApiResponse(responseType, ApiStatus.ERROR, errorCode, errorDescription, null);
    }

    public static ApiResponse genericServerError() {
        return error(ApiResponseType.GENERIC_FAILURE, ERR_9999, "Generic failure");
    }

    public static ApiResponse badRequest() {
        return error(ApiResponseType.GENERIC_FAILURE, ERR_0400, "Bad Request");
    }

    public static ApiResponse successConfigurations(String name, Object data) {
        return success(ApiResponseType.CONFIGURATION, name, data);
    }

    public static ApiResponse errorConfigurations(String errorCode, String errorDescription) {
        return error(ApiResponseType.CONFIGURATION, errorCode, errorDescription);
    }

}
