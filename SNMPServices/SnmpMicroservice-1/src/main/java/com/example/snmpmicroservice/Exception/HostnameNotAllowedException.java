package com.example.snmpmicroservice.Exception;


public class HostnameNotAllowedException extends RuntimeException {

    public HostnameNotAllowedException(String hostname) {
        super("Hostname not allowed: " + hostname);
    }
}
