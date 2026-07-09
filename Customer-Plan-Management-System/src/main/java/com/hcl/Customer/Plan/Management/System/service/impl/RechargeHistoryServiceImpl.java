package com.hcl.Customer.Plan.Management.System.service.impl;


import com.hcl.Customer.Plan.Management.System.dto.RechargeHistoryDTO;
import com.hcl.Customer.Plan.Management.System.entity.RechargeHistory;
import com.hcl.Customer.Plan.Management.System.repository.RechargeHistoryRepository;
import com.hcl.Customer.Plan.Management.System.service.RechargeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RechargeHistoryServiceImpl implements RechargeHistoryService {

    @Autowired
    private RechargeHistoryRepository repository;

    @Override
    public RechargeHistory saveRecharge(RechargeHistoryDTO dto) {

        RechargeHistory recharge = new RechargeHistory();

        recharge.setCustomerId(dto.getCustomerId());
        recharge.setPlanId(dto.getPlanId());
        recharge.setRechargeDate(dto.getRechargeDate());

        return repository.save(recharge);
    }

    @Override
    public List<RechargeHistory> getAllRecharges() {
        return repository.findAll();
    }

    @Override
    public RechargeHistory getRechargeById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recharge Not Found"));
    }

    @Override
    public RechargeHistory updateRecharge(Long id, RechargeHistoryDTO dto) {

        RechargeHistory recharge = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recharge Not Found"));

        recharge.setCustomerId(dto.getCustomerId());
        recharge.setPlanId(dto.getPlanId());
        recharge.setRechargeDate(dto.getRechargeDate());

        return repository.save(recharge);
    }

    @Override
    public void deleteRecharge(Long id) {

        repository.deleteById(id);
    }
}
