package com.gestion.comercial.controller;

import com.gestion.comercial.dto.MovimientoBancario;
import com.gestion.comercial.dto.Vehicle;
import com.gestion.comercial.service.MovimientosService;
import com.gestion.comercial.service.VehiculoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Integracion", description = "Endpoints para operaciones de integraciones")
@RestController
@RequestMapping("/integracion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class IntegracionController {

    private final VehiculoService vehiculoService;

    private final MovimientosService movimientosService;

    public IntegracionController(MovimientosService movimientosService, VehiculoService vehiculoService){
        this.movimientosService = movimientosService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientoBancario>> getMovimientos(){
        List<MovimientoBancario> movimientoBancarios = movimientosService.movimientoBancarios();
        if(movimientoBancarios.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movimientoBancarios,HttpStatus.OK);
    }

    @GetMapping("/vehiculos")
    public ResponseEntity<Vehicle> getVehiculo(@RequestParam String patente){
        Vehicle vehicle = vehiculoService.getVehicleByPlate(patente);
        return new ResponseEntity<>(vehicle,HttpStatus.OK);
    }
}
