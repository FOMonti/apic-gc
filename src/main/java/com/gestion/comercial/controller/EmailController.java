package com.gestion.comercial.controller;

import com.gestion.comercial.dto.ClienteResponse;
import com.gestion.comercial.dto.CustomErrorResponse;
import com.gestion.comercial.service.EmailService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Email", description = "Endpoints para operaciones de envío de emails")
@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }


    @PostMapping("/enviar-pdf")
    @ApiResponse(responseCode = "200", description = "Email enviado exitosamente",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json", schema = @Schema (implementation = CustomErrorResponse.class)))
    public ResponseEntity<String> enviarPDF(@RequestParam String emailReceptor, @RequestParam Long idCotizacionVenta){
        try {
            emailService.enviarCotizacion(emailReceptor,idCotizacionVenta);
        }catch (DocumentException | IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el PDF por correo electrónico.");
        }
        return ResponseEntity.ok().body("Email enviado exitosamente");
    }
}
