package com.gestion.comercial.controller;

import com.gestion.comercial.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> enviarPDF(@RequestParam String emailReceptor, @RequestParam Long idCotizacionVenta){
        try {
            emailService.enviarCorreo(emailReceptor,idCotizacionVenta);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el PDF por correo electrónico.");
        }
        return ResponseEntity.ok().build();
    }
}
