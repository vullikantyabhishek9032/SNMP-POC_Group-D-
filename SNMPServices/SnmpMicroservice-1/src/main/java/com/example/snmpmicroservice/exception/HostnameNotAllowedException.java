package com.example.snmpmicroservice.exception;


public class HostnameNotAllowedException extends RuntimeException {

    public HostnameNotAllowedException(String hostname) {
        super("Hostname not allowed: " + hostname);
    }
}
