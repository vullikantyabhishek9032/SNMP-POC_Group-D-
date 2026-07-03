package com.hcl.Troy.Repo;

import com.hcl.Troy.Entity.RechargeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargeHistoryRepository
        extends JpaRepository<RechargeHistory, Long> {

    List<RechargeHistory> findByCustomerIdOrderByRechargeDateDesc(
            String customerId);
}