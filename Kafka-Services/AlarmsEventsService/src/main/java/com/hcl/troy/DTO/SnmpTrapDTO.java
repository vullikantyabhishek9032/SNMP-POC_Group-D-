package com.hcl.troy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnmpTrapDTO {

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

    private List<TrapVarbindDTO> varbinds;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
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

    public List<TrapVarbindDTO> getVarbinds() {
        return varbinds;
    }

    public void setVarbinds(List<TrapVarbindDTO> varbinds) {
        this.varbinds = varbinds;
    }
}