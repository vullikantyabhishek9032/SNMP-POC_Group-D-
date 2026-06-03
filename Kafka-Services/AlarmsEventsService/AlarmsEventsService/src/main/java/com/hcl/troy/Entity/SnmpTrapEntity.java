package com.hcl.troy.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "snmp_traps")
public class SnmpTrapEntity {

    @Id
    private Long trapId;

    private LocalDateTime timestamp;

    private String host;

    private String community;

    private String trapType;

    private String version;

    private String snmpTrapOid;

    private String severity;

    private String message;

    private Boolean processed;

    @OneToMany(
            mappedBy = "trap",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<TrapVarbindEntity> varbinds;

    public Long getTrapId() {
        return trapId;
    }

    public void setTrapId(Long trapId) {
        this.trapId = trapId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    public String getSnmpTrapOid() {
        return snmpTrapOid;
    }

    public void setSnmpTrapOid(String snmpTrapOid) {
        this.snmpTrapOid = snmpTrapOid;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
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

    public List<TrapVarbindEntity> getVarbinds() {
        return varbinds;
    }

    public void setVarbinds(List<TrapVarbindEntity> varbinds) {
        this.varbinds = varbinds;
    }

    public SnmpTrapEntity(Long trapId, LocalDateTime timestamp, String host, String community, String trapType, String version, String snmpTrapOid, String severity, String message, Boolean processed, List<TrapVarbindEntity> varbinds) {
        this.trapId = trapId;
        this.timestamp = timestamp;
        this.host = host;
        this.community = community;
        this.trapType = trapType;
        this.version = version;
        this.snmpTrapOid = snmpTrapOid;
        this.severity = severity;
        this.message = message;
        this.processed = processed;
        this.varbinds = varbinds;
    }

    public SnmpTrapEntity() {
    }
}