package com.hcl.Troy.Repo;

import com.hcl.Troy.Entity.PlanMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanMasterRepository
        extends JpaRepository<PlanMaster, String> {
}