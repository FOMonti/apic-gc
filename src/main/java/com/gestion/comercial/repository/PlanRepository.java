package com.gestion.comercial.repository;

import com.gestion.comercial.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findPlanByFacturaId(Long facturaId);
}
