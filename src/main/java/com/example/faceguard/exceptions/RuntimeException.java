package com.example.faceguard.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Data
@ResponseStatus(HttpStatus.FORBIDDEN)
public class RuntimeException extends java.lang.RuntimeException {
    String type;
    String com;

    public RuntimeException(String message) {

        super(message);
    }
}