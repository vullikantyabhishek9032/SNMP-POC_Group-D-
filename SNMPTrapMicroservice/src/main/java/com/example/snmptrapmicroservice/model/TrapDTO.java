package com.example.snmptrapmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrapDTO {
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
    private List<VarbindDTO> varbinds;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VarbindDTO {
        private String oid;
        private String type;
        private String value;
    }
}