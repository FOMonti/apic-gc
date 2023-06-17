package com.gestion.comercial.service;

import com.gestion.comercial.dto.Vehicle;
import com.gestion.comercial.dto.VehicleResponse;
import com.gestion.comercial.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class VehiculoService {

    private final RestTemplate restTemplate;

    private final String URL_GET_PATENTE = "https://gadmin-backend-production2.up.railway.app/api/v1/vehicle/getByPlate/";

    private final String URL_UPDATE_STATUS = "https://gadmin-backend-production2.up.railway.app/api/v1/vehicle/updateStatus";
    @Autowired
    public VehiculoService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Vehicle getVehicleByPlate(String plate) {
        String url = URL_GET_PATENTE + plate;

        ResponseEntity<VehicleResponse> responseEntity = restTemplate.getForEntity(url, VehicleResponse.class);
        VehicleResponse vehicleResponse = responseEntity.getBody();
        if(vehicleResponse.getError().getDataErrors().get(0).getCode().equals("400 BAD_REQUEST")){
            throw new ValidationException("No se encontro un vehiculo con la patente: " + plate,"path");
        }
        return vehicleResponse.getResult();
    }

    public void actualizarEstado(String plate, String status){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("plate", plate);
        requestBody.put("status", status);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange(URL_UPDATE_STATUS, HttpMethod.POST, requestEntity, String.class);
    }
}
