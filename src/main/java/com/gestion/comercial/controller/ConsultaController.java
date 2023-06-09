package com.gestion.comercial.controller;

import com.gestion.comercial.dto.ConsultaRequest;
import com.gestion.comercial.dto.ConsultaResponse;
import com.gestion.comercial.dto.CustomErrorResponse;
import com.gestion.comercial.types.service.ConsultaService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Consulta", description = "Endpoints para operaciones de consultas")
@RestController
@RequestMapping("/consultas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ConsultaController {
    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService){
        this.consultaService = consultaService;
    }

    @PostMapping("/save")
    @ApiResponse(responseCode = "201", description = "Consulta creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConsultaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    public ResponseEntity<ConsultaResponse> saveConsulta(@Valid @RequestBody ConsultaRequest consultaRequest){

        return new ResponseEntity<>(consultaService.save(consultaRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ConsultaResponse>> getAll(){
        List<ConsultaResponse> consultaResponses = consultaService.getAll();
        if(consultaResponses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(consultaResponses,HttpStatus.OK);
    }
}
