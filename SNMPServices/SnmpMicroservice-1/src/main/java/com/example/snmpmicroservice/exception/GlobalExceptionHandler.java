package com.example.snmpmicroservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.snmpmicroservice.model.SnmpResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle Hostname validation exception
     */
    @ExceptionHandler(HostnameNotAllowedException.class)
    public ResponseEntity<SnmpResponse> handleHostnameNotAllowedException(
            HostnameNotAllowedException ex) {

        log.error("Hostname validation failed: {}", ex.getMessage());

        SnmpResponse response = new SnmpResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle SNMP timeout / connectivity issues
     */
    @ExceptionHandler(SnmpTimeoutException.class)
    public ResponseEntity<SnmpResponse> handleSnmpTimeoutException(
            SnmpTimeoutException ex) {

        log.error("SNMP timeout occurred: {}", ex.getMessage());

        SnmpResponse response = new SnmpResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(response);
    }

    /**
     * Handle IllegalArgumentException (fallback validation)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SnmpResponse> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        log.error("Illegal argument: {}", ex.getMessage());

        SnmpResponse response = new SnmpResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle all unexpected exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SnmpResponse> handleGenericException(Exception ex) {

        log.error("Unexpected error occurred", ex);

        SnmpResponse response = new SnmpResponse();
        response.setSuccess(false);
        response.setMessage("Internal Server Error");
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}