package com.example.snmptrapmicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trap {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trapId;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "host", nullable = false, length = 255)
    private String host;
    
    @Column(name = "community", length = 100)
    private String community;
    
    @Column(name = "trap_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrapType trapType;
    
    @Column(name = "version", nullable = false, length = 10)
    private String version;
    
    @Column(name = "snmp_trap_oid", length = 1024)
    private String snmpTrapOid;
    
    @Column(name = "generic_trap")
    private Integer genericTrap;
    
    @Column(name = "specific_trap")
    private Integer specificTrap;
    
    @Column(name = "enterprise", length = 1024)
    private String enterprise;
    
    @Column(name = "agent_address", length = 100)
    private String agentAddress;
    
    @Column(name = "uptime", length = 100)
    private String uptime;
    
    @Column(name = "severity")
    @Enumerated(EnumType.STRING)
    private Severity severity;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "processed")
    private Boolean processed = false;
    
  //  @OneToMany(mappedBy = "trap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  //  private List<Varbind> varbinds = new ArrayList<>();
  @OneToMany(mappedBy = "trap", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Varbind> varbinds = new ArrayList<>();



    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
    
    public enum TrapType {
        trap, trap2, inform
    }
    
    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

}