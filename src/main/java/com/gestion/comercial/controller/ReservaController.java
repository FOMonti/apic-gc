package com.gestion.comercial.controller;

import com.gestion.comercial.dto.*;
import com.gestion.comercial.service.MovimientosService;
import com.gestion.comercial.service.ReservaService;
import com.gestion.comercial.service.VehiculoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Reserva", description = "Endpoints para operaciones de reservas")
@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ReservaController {

    private final MovimientosService movimientosService;
    private final ReservaService reservaService;
    private final VehiculoService vehiculoService;

    @Autowired
    public ReservaController(MovimientosService movimientosService,ReservaService reservaService, VehiculoService vehiculoService){
        this.movimientosService = movimientosService;
        this.reservaService = reservaService;
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

    @PostMapping("/save")
    public ResponseEntity<ReservaResponse> save(@Valid @RequestBody ClienteRequest clienteRequest,
                                                @RequestParam String patente){
        return new ResponseEntity<>(reservaService.save(clienteRequest, patente), HttpStatus.CREATED);
    }
}
