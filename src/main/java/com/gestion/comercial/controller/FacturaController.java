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
    @ApiResponse(responseCode = "201", description = "Factura creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = FacturaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    public ResponseEntity<FacturaResponse> save(@RequestParam Long idCotizacion){
        return new ResponseEntity<>(facturaService.save(idCotizacion), HttpStatus.CREATED);
    }

    @PutMapping("/anular-garantia")
    @ApiResponse(responseCode = "200", description = "Factura anulada exitosamente",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    public ResponseEntity<String> anular(@RequestParam String patente,
                                         @RequestParam boolean garantiaAnulada){
        facturaService.anular(patente,garantiaAnulada);
        return new ResponseEntity<>("Factura anulada exitosamente", HttpStatus.OK);
    }

    @GetMapping("/garantia")
    @ApiResponse(responseCode = "200", description = "Garantia encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = GarantiaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    public ResponseEntity<GarantiaResponse> gantia(@RequestParam String patente){
        return new ResponseEntity<>(facturaService.getGarantia(patente), HttpStatus.OK);
    }
}
