package com.gestion.comercial.controller;

import com.gestion.comercial.dto.CotizacionVentaRequest;
import com.gestion.comercial.dto.CotizacionVentaResponse;
import com.gestion.comercial.service.CotizacionVentaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cotizaciones")
//@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CotizacionVentaController {

    public static final String COTIZACIONES= "/cotizaciones";

    public static final String SAVE= "/save";

    private final CotizacionVentaService cotizacionVentaService;

    @Autowired
    public CotizacionVentaController(CotizacionVentaService cotizacionVentaService){
        this.cotizacionVentaService = cotizacionVentaService;
    }

    @PostMapping("/save")
    public ResponseEntity<CotizacionVentaResponse> saveCotizacionVenta(@Valid @RequestBody
                                                     CotizacionVentaRequest cotizacionVentaRequest){
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
