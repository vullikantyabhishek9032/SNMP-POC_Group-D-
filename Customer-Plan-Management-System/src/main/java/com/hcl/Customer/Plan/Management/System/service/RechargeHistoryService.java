package com.hcl.Customer.Plan.Management.System.service;


import com.hcl.Customer.Plan.Management.System.dto.RechargeHistoryDTO;
import com.hcl.Customer.Plan.Management.System.entity.RechargeHistory;

import java.util.List;

public interface RechargeHistoryService {

    RechargeHistory saveRecharge(RechargeHistoryDTO dto);

    List<RechargeHistory> getAllRecharges();

    RechargeHistory getRechargeById(Long id);

    RechargeHistory updateRecharge(Long id, RechargeHistoryDTO dto);

    void deleteRecharge(Long id);
}