package com.gestion.comercial.types.service;

import com.gestion.comercial.dto.ConsultaRequest;
import com.gestion.comercial.dto.ConsultaResponse;
import com.gestion.comercial.entity.Consulta;
import com.gestion.comercial.mapper.ConsultaMapper;
import com.gestion.comercial.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaMapper consultaMapper;
    private final ConsultaRepository consultaRepository;

    @Autowired
    public ConsultaService(ConsultaMapper consultaMapper, ConsultaRepository consultaRepository){
        this.consultaMapper = consultaMapper;
        this.consultaRepository = consultaRepository;
    }
    public ConsultaResponse save(ConsultaRequest consultaRequest) {
        Consulta consulta = consultaMapper.consultaRequestAEntity(consultaRequest);
        consultaRepository.save(consulta);
        return consultaMapper.consultaEntityAResponse(consulta);
    }

    public List<ConsultaResponse> getAll(){
        return consultaMapper.consultasListAResponse(consultaRepository.findAll());

    }
}
