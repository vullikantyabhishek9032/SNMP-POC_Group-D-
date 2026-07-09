package com.hcl.Customer.Plan.Management.System.service.impl;

import com.hcl.Customer.Plan.Management.System.dto.PlanMasterDTO;
import com.hcl.Customer.Plan.Management.System.entity.PlanMaster;
import com.hcl.Customer.Plan.Management.System.repository.PlanMasterRepository;
import com.hcl.Customer.Plan.Management.System.service.PlanMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanMasterServiceImpl implements PlanMasterService {

    @Autowired
    private PlanMasterRepository repository;

    @Override
    public PlanMaster savePlan(PlanMasterDTO dto) {

        PlanMaster plan = new PlanMaster();
        plan.setPlanId(dto.getPlanId());
        plan.setPlanName(dto.getPlanName());
        plan.setDataLimit(dto.getDataLimit());
        plan.setBenefits(dto.getBenefits());
        plan.setNextPlan(dto.getNextPlan());

        return repository.save(plan);
    }

    @Override
    public List<PlanMaster> getAllPlans() {
        return repository.findAll();
    }

    @Override
    public PlanMaster getPlanById(String planId) {
        return repository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan Not Found"));
    }

    @Override
    public PlanMaster updatePlan(String planId, PlanMasterDTO dto) {

        PlanMaster plan = repository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan Not Found"));

        plan.setPlanName(dto.getPlanName());
        plan.setDataLimit(dto.getDataLimit());
        plan.setBenefits(dto.getBenefits());
        plan.setNextPlan(dto.getNextPlan());

        return repository.save(plan);
    }

    @Override
    public void deletePlan(String planId) {

        repository.deleteById(planId);
    }
}