package com.gestion.comercial.service;

import com.gestion.comercial.dto.MovimientoBancario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovimientosService {

    private final RestTemplate restTemplate;
    private final String URL_G2_ADMINISTRACION = "https://karu-administracion-back-produccion.azurewebsites.net/v1/movimientos/0000000000000000000001/?format=json";

    @Autowired
    public MovimientosService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<MovimientoBancario> movimientoBancarios(){
        List<MovimientoBancario> movimientoBancarios = new ArrayList<>();
        ResponseEntity<MovimientoBancario[]> response = restTemplate.getForEntity(URL_G2_ADMINISTRACION,
                MovimientoBancario[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            MovimientoBancario[] movimientos = response.getBody();
            if (movimientos != null) movimientoBancarios = Arrays.asList(movimientos);
        }
        return movimientoBancarios;
    }
}
