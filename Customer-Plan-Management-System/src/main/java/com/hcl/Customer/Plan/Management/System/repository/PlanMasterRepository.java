package com.hcl.Customer.Plan.Management.System.repository;


import com.hcl.Customer.Plan.Management.System.entity.PlanMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanMasterRepository extends JpaRepository<PlanMaster, String> {
}