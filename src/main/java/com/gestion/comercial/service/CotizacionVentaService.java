package com.gestion.comercial.service;

import com.gestion.comercial.dto.CotizacionVentaRequest;
import com.gestion.comercial.dto.CotizacionVentaResponse;
import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.entity.GastoAdministrativo;
import com.gestion.comercial.mapper.CotizacionVentaMapper;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import com.gestion.comercial.repository.GastoAdministrativoRepository;
import com.gestion.comercial.types.CostoAdministrativo;
import com.gestion.comercial.types.EstadoCotizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CotizacionVentaService {

    private final CotizacionVentaMapper cotizacionVentaMapper;
    private final CotizacionVentaRepository cotizacionVentaRepository;
    private final GastoAdministrativoRepository gastoAdministrativoRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CotizacionVentaService(CotizacionVentaMapper cotizacionVentaMapper, RestTemplate restTemplate,
                                  CotizacionVentaRepository cotizacionVentaRepository,
                                  GastoAdministrativoRepository gastoAdministrativoRepository){
        this.cotizacionVentaMapper=cotizacionVentaMapper;
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.restTemplate = restTemplate;
        this.gastoAdministrativoRepository = gastoAdministrativoRepository;
    }

    public CotizacionVentaResponse save(CotizacionVentaRequest cotizacionVentaRequest){
        CotizacionVenta cotizacionVenta = cotizacionVentaMapper.cotizacionRequestAEntity(cotizacionVentaRequest);
        cotizacionVenta.setNumeroCotizacion(numeroCotizacion(cotizacionVenta.getSucursal()));
        calcularPrecio(cotizacionVenta);
        List<GastoAdministrativo> gastoAdministrativos = calcularGastosAdministrativos(cotizacionVenta);
        cotizacionVenta.setTotal(cotizacionVenta.getPrecioBase() + cotizacionVenta.getImporteIVA()
                + cotizacionVenta.getGastosAdministrativos());
        cotizacionVenta.setEstadoCotizacion(EstadoCotizacion.PENDIENTE);
        cotizacionVentaRepository.save(cotizacionVenta);
        CotizacionVentaResponse cotizacionVentaResponse = cotizacionVentaMapper.cotizacionEntityAResponse(cotizacionVenta);
        cotizacionVentaResponse.setGastosAdministrativos(gastoAdministrativos);
        gastoAdministrativos.forEach(gastoAdministrativo -> {
            gastoAdministrativo.setCotizacionVenta(cotizacionVenta);
            gastoAdministrativoRepository.save(gastoAdministrativo);
        } );

        return cotizacionVentaResponse;
    }

    private void calcularPrecio(CotizacionVenta cotizacionVenta) {
        //Integración con el modulo de administraciíon --> Solicitar el vehiculo por la patente
        cotizacionVenta.setImporteIVA(cotizacionVenta.getPrecioBase()*0.21);
    }

    private List<GastoAdministrativo> calcularGastosAdministrativos(CotizacionVenta cotizacionVenta) {
        List<GastoAdministrativo> gastoAdministrativos = new ArrayList<>();
        int linea = 1;
        double totalGastosAdministrativos = 0D;
        for(CostoAdministrativo costoAdministrativo : CostoAdministrativo.values()){
            GastoAdministrativo gastoAdministrativo = new GastoAdministrativo();
            gastoAdministrativo.setLinea(linea);
            gastoAdministrativo.setNombre(costoAdministrativo);
            double importe = cotizacionVenta.getPrecioBase() * costoAdministrativo.getPorcentaje() / 100;
            if(costoAdministrativo.equals(CostoAdministrativo.GARANTIA))
                gastoAdministrativo.setImporte(Boolean.TRUE.equals(cotizacionVenta.getGaratiaExtendida()) ?
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

    public List<CotizacionVentaResponse> getAll(){
        return cotizacionVentaMapper.cotizacionesVentaListAResponse(cotizacionVentaRepository.findAll());
    }
}
