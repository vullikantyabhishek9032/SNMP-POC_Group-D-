package com.hcl.troy.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trap_varbinds")
public class TrapVarbindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oid;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String value;

    @ManyToOne
    @JoinColumn(name = "trap_id")
    private SnmpTrapEntity trap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SnmpTrapEntity getTrap() {
        return trap;
    }

    public void setTrap(SnmpTrapEntity trap) {
        this.trap = trap;
    }

    public TrapVarbindEntity() {
    }

    public TrapVarbindEntity(Long id, String oid, String type, String value, SnmpTrapEntity trap) {
        this.id = id;
        this.oid = oid;
        this.type = type;
        this.value = value;
        this.trap = trap;
    }
}