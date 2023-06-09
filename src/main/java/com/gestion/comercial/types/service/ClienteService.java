package com.gestion.comercial.types.service;

import com.gestion.comercial.dto.ClienteRequest;
import com.gestion.comercial.dto.ClienteResponse;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.mapper.ClienteMapper;
import com.gestion.comercial.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper){
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteResponse> getAll() {
        return clienteMapper.clientesEntityAResponse(clienteRepository.findAll());
    }

    public Optional<ClienteResponse> getClienteByDni(String dni){
        return clienteRepository.getClienteByDni(dni)
                .map(clienteMapper::clienteEntityAResponse);
    }
    public ClienteResponse save(ClienteRequest clienteRequest){
        Cliente cliente = clienteMapper.clienteRequestAEntity(clienteRequest);
        return clienteMapper.clienteEntityAResponse(clienteRepository.save(cliente));
    }
}
