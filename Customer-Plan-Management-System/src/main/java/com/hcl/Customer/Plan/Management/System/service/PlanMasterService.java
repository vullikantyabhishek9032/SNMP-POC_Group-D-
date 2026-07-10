package com.hcl.Customer.Plan.Management.System.service;


import com.hcl.Customer.Plan.Management.System.dto.PlanMasterDTO;
import com.hcl.Customer.Plan.Management.System.entity.PlanMaster;

import java.util.List;

public interface PlanMasterService {

    PlanMaster savePlan(PlanMasterDTO dto);

    List<PlanMaster> getAllPlans();

    PlanMaster getPlanById(String planId);

    PlanMaster updatePlan(String planId, PlanMasterDTO dto);

    void deletePlan(String planId);
}