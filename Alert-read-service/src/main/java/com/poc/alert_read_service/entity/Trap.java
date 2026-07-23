package com.poc.alert_read_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "snmp_traps")
public class Trap {

    @Id
    @Column(name = "trap_id")
    private Long trapId;

    @Column(name = "community")
    private String community;

    @Column(name = "host")
    private String host;

    @Column(name = "message")
    private String message;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "severity")
    private String severity;

    @Column(name = "snmp_trap_oid")
    private String snmpTrapOid;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "trap_type")
    private String trapType;

    @Column(name = "version")
    private String version;

    @Column(name = "oid")
    private String oid;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    public Trap() {
    }

    public Long getTrapId() {
        return trapId;
    }

    public void setTrapId(Long trapId) {
        this.trapId = trapId;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSnmpTrapOid() {
        return snmpTrapOid;
    }

    public void setSnmpTrapOid(String snmpTrapOid) {
        this.snmpTrapOid = snmpTrapOid;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrapType() {
        return trapType;
    }

    public void setTrapType(String trapType) {
        this.trapType = trapType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}