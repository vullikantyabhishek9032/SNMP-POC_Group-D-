package com.poc.alert_read_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "traps")
public class Trap {

    @Id
    private String trapId;

    private String host;

    private String severity;

    private String oid;

    private String message;

    private LocalDateTime receivedAt;

    public Trap() {
    }

    public String getTrapId() {
        return trapId;
    }

    public void setTrapId(String trapId) {
        this.trapId = trapId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}