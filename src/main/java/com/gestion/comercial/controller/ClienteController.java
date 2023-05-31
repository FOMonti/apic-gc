package com.gestion.comercial.controller;

import com.gestion.comercial.dto.ClienteResponse;
import com.gestion.comercial.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Cliente", description = "Endpoints para operaciones de clientes")
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ClienteResponse>> getAll(){
        List<ClienteResponse> clienteResponses = clienteService.getAll();
        if(clienteResponses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clienteResponses,HttpStatus.OK);
    }

    @GetMapping("/cliente")
    public ResponseEntity<ClienteResponse> getClienteByDni(@RequestParam("dni") @Size(min = 8, max = 8) String dni){
        Optional<ClienteResponse> clienteResponse = clienteService.getClienteByDni(dni);
        return clienteResponse.map(response ->
                new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
