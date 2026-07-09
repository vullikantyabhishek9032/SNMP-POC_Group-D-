package com.hcl.Customer.Plan.Management.System.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "customer_recharge_history")
@Data
public class RechargeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;

    private String planId;

    private Timestamp rechargeDate;
}