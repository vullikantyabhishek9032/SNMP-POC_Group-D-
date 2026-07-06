package com.example.snmptrapmicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "varbind")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Varbind {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long varbindId;
    
   // @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "trap_id", nullable = false)
   // private Trap trap;
   @ManyToOne
   @JoinColumn(name = "trap_id")
   private Trap trap;
    
    @Column(name = "oid", nullable = false, length = 1024)
    private String oid;
    
    @Column(name = "type", length = 50)
    private String type;
    
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;
    
    @Column(name = "index_num")
    private Integer indexNum = 0;
}