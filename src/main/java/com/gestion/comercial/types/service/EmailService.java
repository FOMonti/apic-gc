package com.gestion.comercial.types.service;

import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import com.itextpdf.text.*;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    private final SendGrid sendGrid;
    private final CotizacionVentaRepository cotizacionVentaRepository;
    private PDFService pdfService;
    @Value("${app.sendgrid.sender}")
    private String sender;

    @Autowired
    public EmailService(SendGrid sendGrid, CotizacionVentaRepository cotizacionVentaRepository,
                        PDFService pdfService) {
        this.sendGrid = sendGrid;
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.pdfService = pdfService;
    }
    public Response enviarCotizacion(String emailReceptor, Long idCotizacionVenta)
            throws DocumentException, IOException {
        CotizacionVenta cotizacionVenta = cotizacionVentaRepository.findById(idCotizacionVenta)
                .orElseThrow(() -> new ValidationException("No existe una cotización de venta con el id: " +
                        idCotizacionVenta, "/email/enviar-pdf"));
        Mail mail = crearMailCotizacion(emailReceptor, cotizacionVenta);
        return enviarCorreo(mail);
    }

    private Response enviarCorreo(Mail mail) throws IOException{
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return sendGrid.api(request);
    }

    private Mail crearMailCotizacion(String emailReceptor, CotizacionVenta cotizacionVenta)
            throws DocumentException{
        Mail mail = new Mail();
        mail.setFrom(new Email(sender));
        mail.setSubject("Envío de Cotización de Venta");
        mail.addContent(new Content("text/plain",
                "Adjuntamos la cotización de venta en formato PDF."));
        mail.addAttachments(pdfService.crearAttachments(cotizacionVenta));
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(emailReceptor));
        mail.addPersonalization(personalization);
        return mail;
    }

}

