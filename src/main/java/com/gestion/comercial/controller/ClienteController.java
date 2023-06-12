package com.gestion.comercial.controller;

import com.gestion.comercial.dto.ClienteRequest;
import com.gestion.comercial.dto.ClienteResponse;
import com.gestion.comercial.dto.CustomErrorResponse;
import com.gestion.comercial.service.ClienteService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @ApiResponse(responseCode = "200", description = "Clientes encontrados",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ClienteResponse.class))))
    @ApiResponse(responseCode = "404", description = "No se encontró ningún Cliente", content = @Content)
    public ResponseEntity<List<ClienteResponse>> getAll(){
        List<ClienteResponse> clienteResponses = clienteService.getAll();
        if(clienteResponses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clienteResponses,HttpStatus.OK);
    }

    @GetMapping("/cliente")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "No se encontró el Cliente", content = @Content)
    public ResponseEntity<ClienteResponse> getClienteByDni(@RequestParam("dni") @Size(min = 8, max = 8) String dni){
        Optional<ClienteResponse> clienteResponse = clienteService.getClienteByDni(dni);
        return clienteResponse.map(response ->
                new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/save")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    public ResponseEntity<ClienteResponse> saveCliente(@Valid @RequestBody ClienteRequest clienteRequest){
        return new ResponseEntity<>(clienteService.save(clienteRequest), HttpStatus.CREATED);
    }
}
