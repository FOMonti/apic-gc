package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.PlanRequest;
import com.gestion.comercial.dto.PlanResponse;
import com.gestion.comercial.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public Plan requestToEntity(PlanRequest pageRequest){
        Plan plan = new Plan();
        plan.setIdPlan(pageRequest.getIdPlan());
        plan.setOrdinal(pageRequest.getOrdinal());
        plan.setScoringAsociado(pageRequest.getScoringAsociado());
        plan.setTasaInteres(pageRequest.getTasaInteres());
        plan.setMontoInteres(pageRequest.getMontoConInteres());
        plan.setCantCuotas(pageRequest.getCantCuotas());
        plan.setValorCuota(pageRequest.getValorCuota());
        return plan;
    }

    public PlanResponse entityToResponse(Plan plan) {
        PlanResponse planResponse = new PlanResponse();
        planResponse.setId(plan.getId());
        planResponse.setIdPlan(plan.getIdPlan());
        planResponse.setOrdinal(plan.getOrdinal());
        planResponse.setCantCuotas(plan.getCantCuotas());
        planResponse.setScoringAsociado(plan.getScoringAsociado());
        planResponse.setMontoInteres(plan.getMontoInteres());
        planResponse.setTasaInteres(plan.getTasaInteres());
        planResponse.setValorCuota(plan.getValorCuota());
        return planResponse;
    }
}
