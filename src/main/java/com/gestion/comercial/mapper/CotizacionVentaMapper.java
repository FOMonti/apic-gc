package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.CotizacionVentaRequest;
import com.gestion.comercial.dto.CotizacionVentaResponse;
import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.repository.GastoAdministrativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CotizacionVentaMapper {
    private final GastoAdministrativoRepository gastoAdministrativoRepository;
    private final ClienteMapper clienteMapper;
    @Autowired
    public CotizacionVentaMapper(GastoAdministrativoRepository gastoAdministrativoRepository, ClienteMapper clienteMapper){
        this.gastoAdministrativoRepository = gastoAdministrativoRepository;
        this.clienteMapper = clienteMapper;
    }
    public CotizacionVenta cotizacionRequestAEntity(CotizacionVentaRequest cotizacionVentaRequest) {
        CotizacionVenta cotizacionVenta = new CotizacionVenta();
        cotizacionVenta.setSucursal(cotizacionVentaRequest.getSucursal());
        cotizacionVenta.setPatente(cotizacionVentaRequest.getPatente());
        cotizacionVenta.setIdVendedor(cotizacionVentaRequest.getIdVendedor().intValue());
        cotizacionVenta.setGarantiaExtendida(cotizacionVentaRequest.getGarantiaExtendida());
        return cotizacionVenta;
    }

    public CotizacionVentaResponse cotizacionEntityAResponse(CotizacionVenta cotizacionVenta) {
        CotizacionVentaResponse cotizacionVentaResponse = new CotizacionVentaResponse();
        cotizacionVentaResponse.setId(cotizacionVenta.getId());
        cotizacionVentaResponse.setSucursal(cotizacionVenta.getSucursal());
        cotizacionVentaResponse.setNumeroCotizacion(cotizacionVenta.getNumeroCotizacion());
        cotizacionVentaResponse.setIdVendedor(cotizacionVenta.getIdVendedor());
        cotizacionVentaResponse.setPatente(cotizacionVenta.getPatente());
        cotizacionVentaResponse.setCliente(clienteMapper.clienteEntityAResponse(cotizacionVenta.getCliente()));
        cotizacionVentaResponse.setFecha(cotizacionVenta.getFecha());
        cotizacionVentaResponse.setPrecioVenta(cotizacionVenta.getPrecioVenta());
        cotizacionVentaResponse.setImporteIVA(cotizacionVenta.getImporteIVA());
        cotizacionVentaResponse.setImporteTotalGastosAdministrativos(cotizacionVenta.getGastosAdministrativos());
        cotizacionVentaResponse.setGarantiaExtendida(cotizacionVenta.getGarantiaExtendida());
        cotizacionVentaResponse.setTotal(cotizacionVenta.getTotal());
        cotizacionVentaResponse.setEstadoCotizacion(cotizacionVenta.getEstadoCotizacion().name());
        return cotizacionVentaResponse;
    }

    public List<CotizacionVentaResponse> cotizacionesVentaListAResponse(List<CotizacionVenta> cotizaciones){
        List<CotizacionVentaResponse> cotizacionesRet = new ArrayList<>();
        cotizaciones.forEach(cotizacion -> {
            CotizacionVentaResponse cotizacionVentaResponse = cotizacionEntityAResponse(cotizacion);
            cotizacionVentaResponse.setGastosAdministrativos(gastoAdministrativoRepository.findAllByCotizacionVenta(cotizacion));
            cotizacionesRet.add(cotizacionVentaResponse);
        });
        return cotizacionesRet;
    }
}
