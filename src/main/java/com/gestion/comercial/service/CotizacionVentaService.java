package com.gestion.comercial.service;

import com.gestion.comercial.dto.CotizacionVentaRequest;
import com.gestion.comercial.dto.CotizacionVentaResponse;
import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.mapper.CotizacionVentaMapper;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class CotizacionVentaService {

    private final CotizacionVentaMapper cotizacionVentaMapper;
    private final CotizacionVentaRepository cotizacionVentaRepository;

    private final RestTemplate restTemplate;
    private final Double porcentajeGarantia;

    @Autowired
    public CotizacionVentaService(CotizacionVentaMapper cotizacionVentaMapper,
                                  CotizacionVentaRepository cotizacionVentaRepository, RestTemplate restTemplate){
        this.cotizacionVentaMapper=cotizacionVentaMapper;
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.restTemplate = restTemplate;
        this.porcentajeGarantia = 0.017849;
    }

    public CotizacionVentaResponse save(CotizacionVentaRequest cotizacionVentaRequest){
        CotizacionVenta cotizacionVenta = cotizacionVentaMapper.cotizacionRequestAEntity(cotizacionVentaRequest);
        cotizacionVenta.setNumeroCotizacion(numeroCotizacion(cotizacionVenta.getSucursal()));
        calcularCotizacion(cotizacionVenta);
        cotizacionVentaRepository.save(cotizacionVenta);
        return cotizacionVentaMapper.cotizacionEntityAResponse(cotizacionVenta);
    }

    private void calcularCotizacion(CotizacionVenta cotizacionVenta){
        cotizacionVenta.setPrecioTraslado(500D);
        calcularPrecio(cotizacionVenta);
        calcularGarantia(cotizacionVenta);
        calcularGastosAdministrativos(cotizacionVenta);
        cotizacionVenta.setTotal(cotizacionVenta.getPrecioBase() + cotizacionVenta.getImporteIVA() +
                cotizacionVenta.getPrecioTraslado() + cotizacionVenta.getGastosGarantia() + cotizacionVenta.getGastosAdministrativos());
    }

    private void calcularPrecio(CotizacionVenta cotizacionVenta) {
        //Integración con el modulo de administraciíon --> Solicitar el vehiculo por la patente
        Random random = new Random();
        Integer importeAleatorio = random.nextInt(30) + 1;
        Integer multiplicadorAleatorio = random.nextInt(3) + 1;
        Double importe = (1000000D * multiplicadorAleatorio);
        importe+=importe*importeAleatorio/100;
        cotizacionVenta.setPrecioBase(importe);
        cotizacionVenta.setImporteIVA(importe*0.21);
    }

    private void calcularGastosAdministrativos(CotizacionVenta cotizacionVenta) {
        //Integración con el modulo de administraciíon --> Solicitar los gastos administrativos por la patente
        cotizacionVenta.setGastosAdministrativos((cotizacionVenta.getPrecioBase() +
                cotizacionVenta.getImporteIVA()) * 0.02132);
    }

    private void calcularGarantia(CotizacionVenta cotizacionVenta) {
        Double importe = cotizacionVenta.getPrecioBase() + cotizacionVenta.getImporteIVA();
        cotizacionVenta.setGastosGarantia(cotizacionVenta.getGaratiaExtendida() ?
                importe * porcentajeGarantia * 2 : importe * porcentajeGarantia);
    }

    private Long numeroCotizacion(String sucursal){
        return cotizacionVentaRepository.countCotizacionVentaBySucursal(sucursal) + 1l;
    }

    public List<CotizacionVentaResponse> getCotizacionesConFiltros(String sucursal, Integer idVendedor, String patente, String dniCliente) {
        Specification<CotizacionVenta> specification = Specification.where(null);

        if (sucursal != null && !sucursal.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sucursal"), sucursal));
        }

        if (idVendedor != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("idVendedor"), idVendedor));
        }

        if (patente != null && !patente.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("patente"), "%" + patente + "%"));
        }

        if (dniCliente != null && !dniCliente.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("dniCliente"), "%" + dniCliente + "%"));
        }
        List<CotizacionVenta> cotizacionVentas = cotizacionVentaRepository.findAll((Sort) specification);

        return cotizacionVentaMapper.cotizacionesVentaListAResponse(cotizacionVentas);
    }
}
