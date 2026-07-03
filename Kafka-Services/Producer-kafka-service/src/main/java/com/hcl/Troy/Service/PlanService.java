package com.hcl.Troy.Service;

import com.hcl.Troy.DTO.CustomerUsage;
import com.hcl.Troy.DTO.RecommendationDTO;
import com.hcl.Troy.Entity.PlanMaster;
import com.hcl.Troy.Entity.RechargeHistory;
import com.hcl.Troy.Repo.PlanMasterRepository;
import com.hcl.Troy.Repo.RechargeHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final RechargeHistoryRepository rechargeRepo;
    private final PlanMasterRepository planRepo;

    public PlanService(RechargeHistoryRepository rechargeRepo, PlanMasterRepository planRepo) {
        this.rechargeRepo = rechargeRepo;
        this.planRepo = planRepo;
    }

    public RecommendationDTO getRecommendation(CustomerUsage usage) {

        List<RechargeHistory> history = rechargeRepo.findByCustomerIdOrderByRechargeDateDesc(usage.getCustomerId());
        System.out.println("========== LAST 3 MONTHS PLAN HISTORY ==========");

        history.forEach(h -> System.out.println("Customer : " + h.getCustomerId() + " Plan : " + h.getPlanId() + " Recharge Date : " + h.getRechargeDate()));


        if(history.isEmpty()) {
            return null;
        }

        RechargeHistory latestRecharge = history.get(0);

        String currentPlanId = latestRecharge.getPlanId();

        PlanMaster currentPlan = planRepo.findById(currentPlanId).orElse(null);

        if(currentPlan == null) {
            return null;
        }

        if(usage.getUsage() > currentPlan.getDataLimit()) {

            PlanMaster nextPlan = planRepo.findById(currentPlan.getNextPlan()).orElse(null);

            if(nextPlan == null) {
                return null;
            }

            RecommendationDTO dto = new RecommendationDTO();

            dto.setCustomerId(usage.getCustomerId());

            dto.setCurrentPlan(currentPlan.getPlanName());

            dto.setRecommendedPlan(nextPlan.getPlanName());

            dto.setUsage(usage.getUsage());

            dto.setBenefits(nextPlan.getBenefits());

            dto.setTimestamp(usage.getTimestamp());

            return dto;
        }

        return null;
    }
}
