package com.example.snmpmicroservice.Exception;

public class SnmpTimeoutException extends RuntimeException {

    public SnmpTimeoutException(String message) {
        super(message);
    }
}

