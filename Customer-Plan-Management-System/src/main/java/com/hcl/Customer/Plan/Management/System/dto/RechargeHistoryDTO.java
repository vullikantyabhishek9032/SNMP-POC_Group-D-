package com.hcl.Customer.Plan.Management.System.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RechargeHistoryDTO {

    private Long id;
    private String customerId;
    private String planId;
    private Timestamp rechargeDate;
}