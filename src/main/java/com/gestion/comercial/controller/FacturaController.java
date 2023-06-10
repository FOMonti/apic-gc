package com.gestion.comercial.controller;

import com.gestion.comercial.dto.CustomErrorResponse;
import com.gestion.comercial.dto.FacturaResponse;
import com.gestion.comercial.dto.GarantiaResponse;
import com.gestion.comercial.service.FacturaService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Factura", description = "Endpoints para operaciones de facturas")
@RestController
@RequestMapping("/facturas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class FacturaController {

    private final FacturaService facturaService;
    @Autowired
    public FacturaController(FacturaService facturaService){
        this.facturaService = facturaService;
    }

    @PostMapping("/save")
    public ResponseEntity<FacturaResponse> save(@RequestParam Long idCotizacion){
        return new ResponseEntity<>(facturaService.save(idCotizacion), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    @PutMapping("/anular")
    public ResponseEntity<String> anular(@RequestParam Long idFactura,
                                         @RequestParam boolean garantiaAnulada){
        facturaService.anular(idFactura,garantiaAnulada);
        return new ResponseEntity<>("Factura anulada Exitosamente", HttpStatus.OK);
    }

    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    @GetMapping("/garantia")
    public ResponseEntity<GarantiaResponse> gantia(@RequestParam Long idFactura){
        return new ResponseEntity<>(facturaService.getGarantia(idFactura), HttpStatus.OK);
    }
}
