package com.example.snmpmicroservice.exception;

public class SnmpTimeoutException extends RuntimeException {

    public SnmpTimeoutException(String message) {
        super(message);
    }
}

