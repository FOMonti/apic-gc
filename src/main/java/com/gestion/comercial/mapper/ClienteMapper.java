package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.ClienteResponse;
import com.gestion.comercial.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteMapper {

    public ClienteResponse clienteEntityAResponse(Cliente cliente) {
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setDni(cliente.getDni());
        clienteResponse.setNombre(cliente.getNombre());
        clienteResponse.setApellido(cliente.getApellido());
        clienteResponse.setEmail(cliente.getEmail());
        clienteResponse.setDireccion(cliente.getDireccion());
        clienteResponse.setNumTelefono(cliente.getNumTelefono());
        clienteResponse.setFecha(cliente.getFecha());
        return clienteResponse;
    }

    public List<ClienteResponse> clientesEntityAResponse(List<Cliente> clientes) {
        List<ClienteResponse> clientesRet = new ArrayList<>();
        clientes.forEach(cliente -> clientesRet.add(clienteEntityAResponse(cliente)));
        return clientesRet;
    }

}
