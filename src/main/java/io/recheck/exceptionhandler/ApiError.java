package io.recheck.exceptionhandler;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {

    private String status;

    private String timestamp;

    private String message;

    private List<ApiValidationError> subErrors;


}