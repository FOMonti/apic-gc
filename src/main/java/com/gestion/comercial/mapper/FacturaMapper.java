package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.FacturaResponse;
import com.gestion.comercial.entity.Factura;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacturaMapper {
    public FacturaResponse entityAResponse(Factura factura) {
        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setId(factura.getId());
        facturaResponse.setSucursal(factura.getSucursal());
        facturaResponse.setNumeroFactura(factura.getNumeroFactura());
        facturaResponse.setClienteDni(factura.getClienteDni());
        facturaResponse.setIdVendedor(factura.getIdVendedor());
        facturaResponse.setEstado(factura.getEstado());
        facturaResponse.setImporte(factura.getImporte());
        facturaResponse.setFechaCreacion(factura.getFechaCreacion());
        facturaResponse.setFechaPago(factura.getFechaPago());
        facturaResponse.setPatente(factura.getPatente());
        facturaResponse.setCotizacionID(factura.getCotizacionID());
        facturaResponse.setReservaId(factura.getReservaId());
        return facturaResponse;
    }

    public List<FacturaResponse> entityListAResponse(List<Factura> facturas) {
        List<FacturaResponse> facturaResponses = new ArrayList<>();
        facturas.forEach(factura -> facturaResponses.add(entityAResponse(factura)));
        return facturaResponses;
    }
}
