package com.gestion.comercial.controller;

import com.gestion.comercial.dto.*;
import com.gestion.comercial.service.MovimientosService;
import com.gestion.comercial.service.ReservaService;
import com.gestion.comercial.service.VehiculoService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import java.util.Optional;

@Tag(name = "Reserva", description = "Endpoints para operaciones de reservas")
@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }

    @PostMapping("/save")
    public ResponseEntity<ReservaResponse> save(@Valid @RequestBody ClienteRequest clienteRequest,
                                                @RequestParam String patente){
        return new ResponseEntity<>(reservaService.save(clienteRequest, patente), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class))))
    @ApiResponse(responseCode = "404", description = "No se encontró ninguna reserva", content = @Content)
    public ResponseEntity<List<ReservaResponse>> getAll() {
        List<ReservaResponse> reservaResponses=reservaService.getAll();
        if(reservaResponses.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reservaResponses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaResponse.class)))
    @ApiResponse(responseCode = "404", description = "No se encontró la reserva", content = @Content)
    public ResponseEntity<ReservaResponse> getById(@PathVariable Long id) {
        Optional<ReservaResponse> reserva = reservaService.getReservaById(id);
        return reserva.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/anular/{id}")
    @ApiResponse(responseCode = "200", description = "Reserva anulada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaResponse.class)))
    @ApiResponse(responseCode = "400", description = "No es posible anular reservas con estado 'PAGADA'",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "No se encontró la cotizacón de venta", content = @Content)
    public ResponseEntity<ReservaResponse> anular(@PathVariable Long id) {
        Optional<ReservaResponse> reservaResponse = reservaService.anularCotizacion(id);
        return reservaResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
