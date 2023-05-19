package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.ConsultaRequest;
import com.gestion.comercial.dto.ConsultaResponse;
import com.gestion.comercial.entity.Consulta;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsultaMapper {
    public Consulta consultaRequestAEntity(ConsultaRequest consultaRequest) {
        Consulta consulta = new Consulta();
        consulta.setNombre(consultaRequest.getNombre());
        consulta.setApellido(consultaRequest.getApellido());
        consulta.setEmail(consultaRequest.getEmail());
        consulta.setNumTelefono(consultaRequest.getNumTelefono());
        return consulta;
    }

    public ConsultaResponse consultaEntityAResponse(Consulta consulta) {
        ConsultaResponse consultaResponse = new ConsultaResponse();
        consultaResponse.setId(consulta.getId());
        consultaResponse.setNombre(consulta.getNombre());
        consultaResponse.setApellido(consulta.getApellido());
        consultaResponse.setEmail(consulta.getEmail());
        consultaResponse.setNumTelefono(consulta.getNumTelefono());
        consultaResponse.setFecha(consulta.getFecha());
        return consultaResponse;
    }

    public List<ConsultaResponse> consultasListAResponse(List<Consulta> consultas) {
        List<ConsultaResponse> consultaResponses = new ArrayList<>();
        consultas.forEach(consulta -> consultaResponses.add(consultaEntityAResponse(consulta)));
        return consultaResponses;
    }
}
