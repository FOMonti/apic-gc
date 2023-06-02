package com.gestion.comercial.service;

import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.entity.GastoAdministrativo;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import com.gestion.comercial.repository.GastoAdministrativoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    //private final SendGrid sendGrid;
    private final CotizacionVentaRepository cotizacionVentaRepository;
    private final GastoAdministrativoRepository gastoAdministrativoRepository;

    @Value("${app.sendgrid.apiKey}")
    private String sendGridApiKey;

    @Value("${app.sendgrid.sender}")
    private String sender;

    public EmailService(/*SendGrid sendGrid,*/ CotizacionVentaRepository cotizacionVentaRepository,
                        GastoAdministrativoRepository gastoAdministrativoRepository) {
        //this.sendGrid = sendGrid;
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.gastoAdministrativoRepository = gastoAdministrativoRepository;
    }

    public void enviarCorreo(String emailReceptor, Long idCotizacionVenta) {

        CotizacionVenta cotizacionVenta = cotizacionVentaRepository.findById(idCotizacionVenta)
                .orElseThrow(() -> new RuntimeException("Cotización de Venta no encontrada"));

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            Paragraph title = new Paragraph("COTIZACIÓN DE VENTA");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);
            PdfPTable table = crearTablaCotizacion(cotizacionVenta);
            document.add(table);
            document.close();
            writer.close();
            // Configura el archivo adjunto en SendGrid
            Attachments attachment = new Attachments ();
            String base64Content = Base64.encodeBase64String(outputStream.toByteArray());
            //attachment.setContent(outputStream.toByteArray());
            //attachment.setContent(Base64.getMimeEncoder().encodeToString(outputStream.toByteArray()));
            attachment.setContent(base64Content);
            attachment.setFilename("cotizacion.pdf");
            attachment.setType("application/pdf");

            // Crea el objeto Email de SendGrid y configura los demás detalles del correo
            Mail mail = new Mail();
            mail.setFrom(new Email(sender));
            mail.setSubject("Envío de Cotización de Venta");
            mail.addContent(new Content("text/plain", "Adjuntamos la cotización de venta en formato PDF."));
            mail.addAttachments(attachment);

            Personalization personalization = new Personalization();
            personalization.addTo(new Email(emailReceptor));
            mail.addPersonalization(personalization);

            SendGrid sendGrid = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            // Envía el correo utilizando el objeto SendGrid previamente configurado
            Response response = sendGrid.api(request);

            if (response.getStatusCode() == HttpStatus.OK.value()) {
                // El correo se envió exitosamente
                // ...
            } else {
                // Hubo un error al enviar el correo
                // ...
            }

        } catch (DocumentException | IOException e) {
            // Manejo de excepciones
            // ...
        }
    }

    private PdfPTable crearTablaCotizacion(CotizacionVenta cotizacionVenta){
        List<GastoAdministrativo> gastosAdministrativos = gastoAdministrativoRepository.findAllByCotizacionVenta(cotizacionVenta);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font cabeceraFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell[] cabeceraCells = new PdfPCell[]{
                new PdfPCell(new Phrase("NOMBRE", cabeceraFont)),
                new PdfPCell(new Phrase("DESCRIPCIÓN", cabeceraFont))
        };
        for (PdfPCell cell : cabeceraCells) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        table.addCell("COTIZACION ID ");
        table.addCell(cotizacionVenta.getId().toString());

        table.addCell("SUCURSAL ");
        table.addCell(cotizacionVenta.getSucursal());

        table.addCell("NÚMERO COTIZACIÓN ");
        table.addCell(cotizacionVenta.getNumeroCotizacion().toString());

        table.addCell("ESTADO COTIZACIÓN ");
        table.addCell(cotizacionVenta.getEstadoCotizacion().toString());

        table.addCell("VENDEDOR ID ");
        table.addCell(cotizacionVenta.getIdVendedor().toString());

        table.addCell("NOMBRE CLIENTE ");
        table.addCell(cotizacionVenta.getNombreCliente());

        table.addCell("EMAIL CLIENTE ");
        table.addCell(cotizacionVenta.getEmail());

        table.addCell("PATENTE ");
        table.addCell(cotizacionVenta.getPatente());

        table.addCell("TIPO GARANTÍA ");
        table.addCell(Boolean.TRUE.equals(cotizacionVenta.getGarantiaExtendida()) ? "EXTENDIDA" : "SIMPLE");

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell[] headerCells = new PdfPCell[]{
                new PdfPCell(new Phrase("DESCRIPCIÓN", headerFont)),
                new PdfPCell(new Phrase("IMPORTE", headerFont))
        };

        for (PdfPCell cell : headerCells) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        table.addCell("PRECIO BASE: ");
        table.addCell("$ " + cotizacionVenta.getPrecioBase());

        PdfPCell separatorCell = new PdfPCell(new Phrase("GASTOS ADMINISTRATIVOS", headerFont));
        separatorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        separatorCell.setColspan(2);
        table.addCell(separatorCell);

        for (GastoAdministrativo gasto : gastosAdministrativos) {
            table.addCell(gasto.getNombre().toString());
            table.addCell("$" + gasto.getImporte());
        }

        PdfPCell separatorCell2 = new PdfPCell(new Phrase("TOTALES", headerFont));
        separatorCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        separatorCell2.setColspan(2);
        table.addCell(separatorCell2);

        table.addCell("TOTAL DE GASTOS ADMINISTRATIVOS: ");
        table.addCell("$ " + cotizacionVenta.getGastosAdministrativos());

        table.addCell("TOTAL: ");
        table.addCell("$ " + cotizacionVenta.getTotal());

        return table;
    }


}

