package com.gestion.comercial.service;

import com.gestion.comercial.dto.FacturaResponse;
import com.gestion.comercial.entity.Cliente;
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

import java.util.List;
import java.util.Optional;

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

    public FacturaResponse save(Long idCotizacion, String dni) {
        CotizacionVenta cotizacionVenta = utilService.orElseThrow(idCotizacion,"/facturas/save");
        validarCotizacionXFactura(idCotizacion);

        Factura factura = new Factura();
        factura.setSucursal(cotizacionVenta.getSucursal());
        factura.setPatente(cotizacionVenta.getPatente());
        factura.setIdVendedor(cotizacionVenta.getIdVendedor());
        factura.setClienteDni(dni);
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

    private void validarCotizacionXFactura(Long idCotizacion){
        List<Factura> facturaList = facturaRepository.findFacturaByCotizacionID(idCotizacion);
        if(!facturaList.isEmpty()){
            throw new ValidationException("Ya existe una factura para esa cotizacion","/facturas");
        }
    }
}