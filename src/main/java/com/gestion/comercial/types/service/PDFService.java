package com.gestion.comercial.types.service;

import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.entity.GastoAdministrativo;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.repository.GastoAdministrativoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sendgrid.helpers.mail.objects.Attachments;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PDFService {

    private final GastoAdministrativoRepository gastoAdministrativoRepository;
    @Autowired
    public PDFService(GastoAdministrativoRepository gastoAdministrativoRepository){
        this.gastoAdministrativoRepository = gastoAdministrativoRepository;
    }

    public Attachments crearAttachments(CotizacionVenta cotizacionVenta) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
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

        Attachments attachment = new Attachments ();
        String base64Content = Base64.encodeBase64String(outputStream.toByteArray());
        attachment.setContent(base64Content);
        attachment.setFilename("cotizacion.pdf");
        attachment.setType("application/pdf");
        return attachment;
    }

    private PdfPTable crearTablaCotizacion(CotizacionVenta cotizacionVenta){
        List<GastoAdministrativo> gastosAdministrativos = gastoAdministrativoRepository.findAllByCotizacionVenta(cotizacionVenta);
        if(gastosAdministrativos.isEmpty()){
            throw new ValidationException("No existen gastos administrativos para la cotización de venta con el id: " +
                    cotizacionVenta.getId(), "/email/enviar-pdf");
        }
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
        table.addCell("COTIZACIÓN ID ");
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
        table.addCell(crearCeldaImporte(cotizacionVenta.getPrecioBase()));

        PdfPCell separatorCell = new PdfPCell(new Phrase("GASTOS ADMINISTRATIVOS", headerFont));
        separatorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        separatorCell.setColspan(2);
        table.addCell(separatorCell);

        for (GastoAdministrativo gasto : gastosAdministrativos) {
            table.addCell(gasto.getNombre().toString());
            table.addCell(crearCeldaImporte(gasto.getImporte()));
        }

        PdfPCell separatorCell2 = new PdfPCell(new Phrase("TOTALES", headerFont));
        separatorCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        separatorCell2.setColspan(2);
        table.addCell(separatorCell2);

        table.addCell("TOTAL DE GASTOS ADMINISTRATIVOS: ");
        table.addCell(crearCeldaImporte(cotizacionVenta.getGastosAdministrativos()));

        table.addCell("TOTAL: ");
        table.addCell(crearCeldaImporte(cotizacionVenta.getTotal()));

        return table;
    }

    private PdfPCell crearCeldaImporte(Double importe){
        PdfPCell pdfPCell = new PdfPCell(new Phrase("$ " + convertir2Decimales(importe)));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return pdfPCell;
    }

    private Double convertir2Decimales(Double numero){
        return BigDecimal.valueOf(numero).setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
