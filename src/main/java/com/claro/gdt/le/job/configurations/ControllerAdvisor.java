package com.claro.gdt.le.job.configurations;



import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.responses.ApiResponse;
import com.claro.gdt.le.job.utils.ApiResponseUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return ResponseEntity.badRequest().body(ApiResponseUtils.badRequest().setErrorDescription(errors.toString()));
    }

    @ExceptionHandler(SGTException.class)
    public ResponseEntity<Object> handleIEEAException(SGTException exception){
        ApiResponse apiResponse = exception.getApiResponse();
        if (apiResponse == null){
            apiResponse = ApiResponseUtils.genericServerError();
        }
        return ResponseEntity.badRequest().body(apiResponse);
    }

    private static final Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: \\[([^\\]])\\]");


    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() instanceof InvalidFormatException){
            Matcher match =ENUM_MSG.matcher(ex.getCause().getMessage());
            if (match.matches()){
                return ResponseEntity.badRequest()
                        .body(ApiResponseUtils.badRequest().setErrorDescription("value should be: "+match.group()));
            }
        }
        return ResponseEntity.badRequest().body(ApiResponseUtils.badRequest().setErrorDescription(ex.getMessage()));
    }
}
