package com.gestion.comercial.service;

import com.gestion.comercial.dto.Vehicle;
import com.gestion.comercial.dto.VehicleResponse;
import com.gestion.comercial.exception.EntityNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class VehiculoService {

    private final RestTemplate restTemplate;

    private final String URL = "https://gadmin-backend-production.up.railway.app/api/v1/vehicle/getByPlate/";

    @Autowired
    public VehiculoService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Optional<Vehicle> getVehicleByPlate(String plate) {
        String url = URL + plate;

        ResponseEntity<VehicleResponse> responseEntity = restTemplate.getForEntity(url, VehicleResponse.class);
        VehicleResponse vehicleResponse = responseEntity.getBody();
        if(vehicleResponse.getError().getDataErrors().get(0).getCode().equals("400 BAD_REQUEST")){
            throw new EntityNotExistException("No se encontro un vehiculo con la patente: " + plate,"path");
        }
        return Optional.of(vehicleResponse).map(VehicleResponse::getResult);
    }

}
