package com.gestion.comercial.service;

import com.gestion.comercial.dto.FacturaResponse;
import com.gestion.comercial.dto.GarantiaResponse;
import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.entity.Factura;
import com.gestion.comercial.entity.Reserva;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.mapper.FacturaMapper;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import com.gestion.comercial.repository.FacturaRepository;
import com.gestion.comercial.repository.ReservaRepository;
import com.gestion.comercial.types.EstadoFactura;
import com.gestion.comercial.types.EstadoReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class FacturaService {

    private final FacturaMapper facturaMapper;
    private final CotizacionVentaRepository cotizacionVentaRepository;
    private final FacturaRepository facturaRepository;
    private final ReservaService reservaService;
    private final ReservaRepository reservaRepository;
    private final UtilService utilService;


    @Autowired
    public FacturaService(FacturaMapper facturaMapper, CotizacionVentaRepository cotizacionVentaRepository,
                          FacturaRepository facturaRepository, ReservaService reservaService,
                          ReservaRepository reservaRepository, UtilService utilService){
        this.facturaMapper = facturaMapper;
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.facturaRepository = facturaRepository;
        this.reservaService = reservaService;
        this.reservaRepository  = reservaRepository;
        this.utilService = utilService;
    }

    public FacturaResponse save(Long idCotizacion) {
        CotizacionVenta cotizacionVenta = utilService.cotizacionOrElseThrow(idCotizacion,"/facturas/save");
        validarCotizacionXFactura(cotizacionVenta.getPatente());

        Factura factura = new Factura();
        factura.setSucursal(cotizacionVenta.getSucursal());
        factura.setPatente(cotizacionVenta.getPatente());
        factura.setIdVendedor(cotizacionVenta.getIdVendedor());
        factura.setClienteDni(cotizacionVenta.getCliente().getDni());
        factura.setEstado(EstadoFactura.PENDIENTE);
        factura.setCotizacionID(idCotizacion);
        factura.setNumeroFactura(numeroFactura(cotizacionVenta.getSucursal()));
        factura.setImporte(cotizacionVenta.getTotal());
        relacionarReserva(factura);
        factura.setGarantiaAnulada(Boolean.FALSE);
        factura.setGarantiaExtendida(cotizacionVenta.getGarantiaExtendida());
        facturaRepository.save(factura);
        return facturaMapper.entityAResponse(factura);
    }

    public Long numeroFactura(String sucursal){
        return facturaRepository.countFacturaBySucursal(sucursal);
    }

    private void relacionarReserva(Factura factura){
        List<Reserva> reservas = reservaService.getAllByClienteAndPagada(factura.getClienteDni());
        if(!reservas.isEmpty()){
            Reserva reserva = reservas.get(0);
            factura.setImporte(factura.getImporte()-reserva.getImporte());
            factura.setReservaId(reserva.getId());
            reserva.setEstadoReserva(EstadoReserva.PROCESADA);
            reservaRepository.save(reserva);
        }
    }

    private void validarCotizacionXFactura(String patente){
        List<Factura> facturaList = facturaRepository.findFacturaByPatente(patente);
        if(!facturaList.isEmpty()){
            throw new ValidationException(
                    "Ya existe una factura para el vehiculo con patente: {"+patente+"}","/facturas/save");
        }
    }

    public void anular(Long idFactura, boolean estadoGarantia) {
        Factura factura = utilService.facturaOrElseThrow(idFactura, "/facturas/anular");
        factura.setGarantiaAnulada(estadoGarantia);
        facturaRepository.save(factura);
    }

    public GarantiaResponse getGarantia(Long idFactura) {
        Factura factura = utilService.facturaOrElseThrow(idFactura, "/facturas/garantia");
        GarantiaResponse garantiaResponse = new GarantiaResponse();
        garantiaResponse.setGarantiaAnulada(factura.getGarantiaAnulada());
        garantiaResponse.setGarantiaExtendida(factura.getGarantiaExtendida());
        garantiaResponse.setFecha(factura.getFechaPago());
        return garantiaResponse;
    }

    public void aprobarFactura(Long idFactura) {
        Factura factura = utilService.facturaOrElseThrow(idFactura, "/integracion/facturas");
        if(!factura.getEstado().equals(EstadoFactura.PENDIENTE)){
            throw new ValidationException(
                    "Para aprobar una factura, está debe tener estado PENDIENTE","/integracion/facturas");
        }
        Date fechaActual = new Date();
        factura.setEstado(EstadoFactura.PAGADA);
        factura.setFechaPago(new Timestamp(fechaActual.getTime()));
        facturaRepository.save(factura);
    }
}
