package io.recheck.exceptionhandler;

import lombok.Data;

@Data
public class ApiValidationError {
    private String field;
    private String message;
}