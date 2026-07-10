package com.hcl.Customer.Plan.Management.System.repository;


import com.hcl.Customer.Plan.Management.System.entity.RechargeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RechargeHistoryRepository extends JpaRepository<RechargeHistory, Long> {
}