package com.gestion.comercial.service;

import com.gestion.comercial.dto.CotizacionVentaRequest;
import com.gestion.comercial.dto.CotizacionVentaResponse;
import com.gestion.comercial.dto.ReservaResponse;
import com.gestion.comercial.dto.Vehicle;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.entity.GastoAdministrativo;
import com.gestion.comercial.entity.Reserva;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.mapper.CotizacionVentaMapper;
import com.gestion.comercial.mapper.ReservaMapper;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import com.gestion.comercial.repository.GastoAdministrativoRepository;
import com.gestion.comercial.types.CostoAdministrativo;
import com.gestion.comercial.types.EstadoCotizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CotizacionVentaService {

    private final CotizacionVentaMapper cotizacionVentaMapper;
    private final CotizacionVentaRepository cotizacionVentaRepository;
    private final GastoAdministrativoRepository gastoAdministrativoRepository;
    private final ReservaService reservaService;
    private final UtilService utilService;
    private final VehiculoService vehiculoService;
    private final ReservaMapper reservaMapper;

    @Autowired
    public CotizacionVentaService(CotizacionVentaMapper cotizacionVentaMapper,
                                  CotizacionVentaRepository cotizacionVentaRepository,
                                  GastoAdministrativoRepository gastoAdministrativoRepository,
                                  UtilService utilService,VehiculoService vehiculoService,
                                  ReservaService reservaService, ReservaMapper reservaMapper){
        this.cotizacionVentaMapper=cotizacionVentaMapper;
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.gastoAdministrativoRepository = gastoAdministrativoRepository;
        this.utilService = utilService;
        this.vehiculoService = vehiculoService;
        this.reservaService = reservaService;
        this.reservaMapper = reservaMapper;
    }

    public CotizacionVentaResponse save(CotizacionVentaRequest cotizacionVentaRequest){
        Vehicle vehicle = vehiculoService.getVehicleByPlate(cotizacionVentaRequest.getPatente());
        Cliente cliente= utilService.clienteOrElseThrow(cotizacionVentaRequest.getDni(),"/cotizaciones/save");
        CotizacionVenta cotizacionVenta = cotizacionVentaMapper.cotizacionRequestAEntity(cotizacionVentaRequest);
        cotizacionVenta.setCliente(cliente);
        cotizacionVenta.setNumeroCotizacion(numeroCotizacion(cotizacionVenta.getSucursal()));
        cotizacionVenta.setPrecioVenta(vehicle.getSellPrice());
        calcularPrecio(cotizacionVenta);
        List<GastoAdministrativo> gastoAdministrativos = calcularGastosAdministrativos(cotizacionVenta);
        cotizacionVenta.setTotal(cotizacionVenta.getPrecioVenta() + cotizacionVenta.getImporteIVA()
                + cotizacionVenta.getGastosAdministrativos());
        cotizacionVenta.setEstadoCotizacion(EstadoCotizacion.PENDIENTE);
        cotizacionVentaRepository.save(cotizacionVenta);
        CotizacionVentaResponse cotizacionVentaResponse = cotizacionVentaMapper.cotizacionEntityAResponse(cotizacionVenta);
        cotizacionVentaResponse.setGastosAdministrativos(gastoAdministrativos);
        gastoAdministrativos.forEach(gastoAdministrativo -> {
            gastoAdministrativo.setCotizacionVenta(cotizacionVenta);
            gastoAdministrativoRepository.save(gastoAdministrativo);
        });
        relacionarReserva(cotizacionVentaResponse);
        return cotizacionVentaResponse;
    }

    private void relacionarReserva(CotizacionVentaResponse cotizacionVentaResponse) {
        List<Reserva> reservas = reservaService.getAllByClienteAndPagada(cotizacionVentaResponse.getCliente()
                .getDni());
        cotizacionVentaResponse.setImporteReserva(0D);
        cotizacionVentaResponse.setTotalMenosReserva(cotizacionVentaResponse.getTotal());
        if(!reservas.isEmpty()){
            Reserva reserva = reservas.get(0);
            cotizacionVentaResponse.setImporteReserva(reserva.getImporte());
            cotizacionVentaResponse.setTotalMenosReserva(cotizacionVentaResponse.getTotal()-reserva.getImporte());
            ReservaResponse reservaResponse = reservaMapper.entityAResponse(reserva);
            reservaResponse.setClienteResponse(null);
            cotizacionVentaResponse.setReservaResponse(reservaResponse);
        }
    }

    private void calcularPrecio(CotizacionVenta cotizacionVenta) {
        cotizacionVenta.setImporteIVA(cotizacionVenta.getPrecioVenta()*0.21);
    }

    private List<GastoAdministrativo> calcularGastosAdministrativos(CotizacionVenta cotizacionVenta) {
        List<GastoAdministrativo> gastoAdministrativos = new ArrayList<>();
        int linea = 1;
        double totalGastosAdministrativos = 0D;
        for(CostoAdministrativo costoAdministrativo : CostoAdministrativo.values()){
            GastoAdministrativo gastoAdministrativo = new GastoAdministrativo();
            gastoAdministrativo.setLinea(linea);
            gastoAdministrativo.setNombre(costoAdministrativo);
            double importe = cotizacionVenta.getPrecioVenta() * costoAdministrativo.getPorcentaje() / 100;
            if(costoAdministrativo.equals(CostoAdministrativo.GARANTIA))
                gastoAdministrativo.setImporte(Boolean.TRUE.equals(cotizacionVenta.getGarantiaExtendida()) ?
                        importe * 2 : importe);
            else
                gastoAdministrativo.setImporte(importe);
            linea++;
            totalGastosAdministrativos +=importe;
            gastoAdministrativos.add(gastoAdministrativo);
        }
        cotizacionVenta.setGastosAdministrativos(totalGastosAdministrativos);
        return gastoAdministrativos;
    }

    private Long numeroCotizacion(String sucursal){
        return cotizacionVentaRepository.countCotizacionVentaBySucursal(sucursal) + 1L;
    }

    public List<CotizacionVentaResponse> getAll(){
        return cotizacionVentaMapper.cotizacionesVentaListAResponse(cotizacionVentaRepository.findAll());
    }

    public Optional<CotizacionVentaResponse> getCotizacionById(Long id){
        Optional<CotizacionVenta> cotizacionVentaOptional = cotizacionVentaRepository.findById(id);
        CotizacionVentaResponse cotizacionVentaResponse = null;
        if(cotizacionVentaOptional.isPresent()){
            cotizacionVentaResponse = cotizacionVentaMapper.cotizacionEntityAResponse(cotizacionVentaOptional.get());
            cotizacionVentaResponse.setGastosAdministrativos(gastoAdministrativoRepository.findAllByCotizacionVenta(cotizacionVentaOptional.get()));
        }
        return Optional.ofNullable(cotizacionVentaResponse);
    }

    public Optional<CotizacionVentaResponse> anularCotizacion(Long id) {
        Optional<CotizacionVenta> cotizacionVentaOptional = cotizacionVentaRepository.findById(id);
        CotizacionVentaResponse cotizacionVentaResponse = null;
        if(cotizacionVentaOptional.isPresent()){
            CotizacionVenta cotizacionVenta = cotizacionVentaOptional.get();
            if(cotizacionVenta.getEstadoCotizacion().equals(EstadoCotizacion.PAGADA)){
                throw new ValidationException("No se puede anular la cotización ya que la misma esta en estado: PAGADA",
                        "/cotizaciones/anular/{id}");
            }else{
                cotizacionVenta.setEstadoCotizacion(EstadoCotizacion.ANULADO);
                cotizacionVentaRepository.save(cotizacionVenta);
            }
            cotizacionVentaResponse = cotizacionVentaMapper.cotizacionEntityAResponse(cotizacionVenta);
            cotizacionVentaResponse.setGastosAdministrativos(gastoAdministrativoRepository.findAllByCotizacionVenta(cotizacionVentaOptional.get()));
        }
        return Optional.ofNullable(cotizacionVentaResponse);
    }
}
