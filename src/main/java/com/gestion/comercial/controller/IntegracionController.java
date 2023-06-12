package com.gestion.comercial.controller;

import com.gestion.comercial.dto.*;
import com.gestion.comercial.service.FacturaService;
import com.gestion.comercial.service.MovimientosService;
import com.gestion.comercial.service.VehiculoService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private final FacturaService facturaService;

    public IntegracionController(MovimientosService movimientosService, VehiculoService vehiculoService,
                                 FacturaService facturaService){
        this.movimientosService = movimientosService;
        this.vehiculoService = vehiculoService;
        this.facturaService = facturaService;
    }

    @GetMapping("/movimientos")
    @ApiResponse(responseCode = "200", description = "Movimientos encontradas", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MovimientoBancario.class))))
    @ApiResponse(responseCode = "404", description = "El modulo 2 no devolvió movimientos", content = @Content)
    public ResponseEntity<List<MovimientoBancario>> getMovimientos(){
        List<MovimientoBancario> movimientoBancarios = movimientosService.movimientoBancarios();
        if(movimientoBancarios.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movimientoBancarios,HttpStatus.OK);
    }

    @GetMapping("/vehiculos")
    @ApiResponse(responseCode = "200", description = "Vehiculo encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CotizacionVentaResponse.class)))
    @ApiResponse(responseCode = "404", description = "El modulo 4 no devolvió un vehiculo con la patente ingresada", content = @Content)
    public ResponseEntity<Vehicle> getVehiculo(@RequestParam String patente){
        Vehicle vehicle = vehiculoService.getVehicleByPlate(patente);
        return new ResponseEntity<>(vehicle,HttpStatus.OK);
    }

    @PostMapping("/facturas")
    @ApiResponse(responseCode = "200", description = "Pago generado con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GarantiaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    private ResponseEntity<String> aprobarPagoFactura(@RequestParam Long idFactura){
        facturaService.aprobarFactura(idFactura);
        return new ResponseEntity<>("Pago generado con éxito",HttpStatus.OK);
    }
}
