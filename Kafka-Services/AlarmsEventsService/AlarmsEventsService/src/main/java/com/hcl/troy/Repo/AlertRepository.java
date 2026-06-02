package com.hcl.troy.Repo;

import com.hcl.troy.Entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, String> {

}