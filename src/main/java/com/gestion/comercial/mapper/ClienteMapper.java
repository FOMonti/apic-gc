package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.ClienteRequest;
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

    public ClienteRequest clienteEntityARequest(Cliente cliente){
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setDni(cliente.getDni());
        clienteRequest.setNombre(cliente.getNombre());
        clienteRequest.setApellido(cliente.getApellido());
        clienteRequest.setNumTelefono(cliente.getNumTelefono());
        clienteRequest.setEmail(cliente.getEmail());
        clienteRequest.setDireccion(cliente.getDireccion());
        return clienteRequest;
    }

    public Cliente clienteRequestAEntity(ClienteRequest clienteRequest){
        Cliente cliente = new Cliente();
        cliente.setDni(clienteRequest.getDni());
        cliente.setNombre(clienteRequest.getNombre());
        cliente.setApellido(clienteRequest.getApellido());
        cliente.setEmail(clienteRequest.getEmail());
        cliente.setDireccion(clienteRequest.getDireccion());
        cliente.setNumTelefono(clienteRequest.getNumTelefono());
        return cliente;
    }
}
