package com.hcl.Customer.Plan.Management.System.dto;

import lombok.Data;

@Data
public class PlanMasterDTO {

    private String planId;
    private String planName;
    private Double dataLimit;
    private String benefits;
    private String nextPlan;
}