package com.gestion.comercial.controller;

import com.gestion.comercial.dto.CotizacionVentaRequest;
import com.gestion.comercial.dto.CotizacionVentaResponse;
import com.gestion.comercial.dto.CustomErrorResponse;
import com.gestion.comercial.service.CotizacionVentaService;
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
@Tag(name = "Cotizaciones", description = "Endpoints para operaciones de cotizaciones")
@RestController
@RequestMapping("/cotizaciones")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CotizacionVentaController {

    private final CotizacionVentaService cotizacionVentaService;

    @Autowired
    public CotizacionVentaController(CotizacionVentaService cotizacionVentaService){
        this.cotizacionVentaService = cotizacionVentaService;
    }

    @PostMapping("/save")
    @ApiResponse(responseCode = "201", description = "Cotizacion creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CotizacionVentaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    public ResponseEntity<CotizacionVentaResponse> saveCotizacionVenta(@Valid @RequestBody CotizacionVentaRequest cotizacionVentaRequest){
        return new ResponseEntity<>(cotizacionVentaService.save(cotizacionVentaRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public List<CotizacionVentaResponse> getCotizacionesConFiltros(@RequestParam(required = false) String sucursal,
                                                      @RequestParam(required = false) Integer idVendedor,
                                                      @RequestParam(required = false) String patente,
                                                      @RequestParam(required = false) String dniCliente) {
        return cotizacionVentaService.getCotizacionesConFiltros(sucursal, idVendedor, patente, dniCliente);
    }

    @GetMapping("/getAll")
    public List<CotizacionVentaResponse> getAll() {
        return cotizacionVentaService.getAll();
    }
}
