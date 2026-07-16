package com.hcl.Customer.Plan.Management.System.controller;


import com.hcl.Customer.Plan.Management.System.dto.PlanMasterDTO;
import com.hcl.Customer.Plan.Management.System.entity.PlanMaster;
import com.hcl.Customer.Plan.Management.System.service.PlanMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanMasterController {

    @Autowired
    private PlanMasterService service;

    @PostMapping("/createplans")
    public PlanMaster savePlan(@RequestBody PlanMasterDTO dto) {
        return service.savePlan(dto);
    }

    @GetMapping("fetechPlans")
    public List<PlanMaster> getAllPlans() {
        return service.getAllPlans();
    }

    @GetMapping("/{planId}")
    public PlanMaster getPlanById(@PathVariable String planId) {
        return service.getPlanById(planId);
    }

    @PutMapping("/{planId}")
    public PlanMaster updatePlan(
            @PathVariable String planId,
            @RequestBody PlanMasterDTO dto) {

        return service.updatePlan(planId, dto);
    }

    @DeleteMapping("/{planId}")
    public String deletePlan(@PathVariable String planId) {

        service.deletePlan(planId);

        return "Plan Deleted Successfully";
    }
}