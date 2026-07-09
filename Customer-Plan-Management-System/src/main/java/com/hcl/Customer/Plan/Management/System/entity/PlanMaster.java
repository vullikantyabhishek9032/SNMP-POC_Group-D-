package com.hcl.Customer.Plan.Management.System.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "plan_master")
@Data
public class PlanMaster {

    @Id
    private String planId;

    private String planName;

    private Double dataLimit;

    private String benefits;

    private String nextPlan;
}