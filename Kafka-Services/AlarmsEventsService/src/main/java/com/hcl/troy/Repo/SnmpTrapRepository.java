package com.hcl.troy.Repo;

import com.hcl.troy.Entity.SnmpTrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnmpTrapRepository extends JpaRepository<SnmpTrapEntity, Long> {
}