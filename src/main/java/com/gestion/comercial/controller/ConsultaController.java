package com.gestion.comercial.controller;

import com.gestion.comercial.dto.ConsultaRequest;
import com.gestion.comercial.dto.ConsultaResponse;
import com.gestion.comercial.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService){
        this.consultaService = consultaService;
    }

    @PostMapping("/save")
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