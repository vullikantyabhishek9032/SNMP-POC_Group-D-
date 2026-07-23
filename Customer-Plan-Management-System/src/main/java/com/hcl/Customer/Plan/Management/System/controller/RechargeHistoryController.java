package com.hcl.Customer.Plan.Management.System.controller;

import com.hcl.Customer.Plan.Management.System.dto.RechargeHistoryDTO;
import com.hcl.Customer.Plan.Management.System.entity.RechargeHistory;
import com.hcl.Customer.Plan.Management.System.service.RechargeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recharges")
@CrossOrigin(origins ={"http://localhost:3000","http://localhost:30080"})
public class RechargeHistoryController {

    @Autowired
    private RechargeHistoryService service;

    @PostMapping
    public RechargeHistory saveRecharge(
            @RequestBody RechargeHistoryDTO dto) {

        return service.saveRecharge(dto);
    }

    @GetMapping
    public List<RechargeHistory> getAllRecharges() {
        return service.getAllRecharges();
    }

    @GetMapping("/{id}")
    public RechargeHistory getRechargeById(
            @PathVariable Long id) {

        return service.getRechargeById(id);
    }

    @PutMapping("/{id}")
    public RechargeHistory updateRecharge(
            @PathVariable Long id,
            @RequestBody RechargeHistoryDTO dto) {

        return service.updateRecharge(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteRecharge(
            @PathVariable Long id) {

        service.deleteRecharge(id);

        return "Recharge Deleted Successfully";
    }
}