package com.gestion.comercial.service;

import com.gestion.comercial.dto.MovimientoBancario;
import com.gestion.comercial.dto.MovimientoRequest;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.entity.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MovimientosService {

    private final RestTemplate restTemplate;
    private final String URL_G2_ADMINISTRACION = "https://karu-administracion-back-produccion.azurewebsites.net/v1/movimientos/0000000000000000000001/?format=json";
    private final String URL_G2_MOVIMIENTO = "https://karu-administracion-back-desarrollo.azurewebsites.net/v1/movimiento/";
    private final UtilService utilService;
    private static final String DIGITS = "0123456789";
    @Autowired
    public MovimientosService(RestTemplate restTemplate, UtilService utilService){
        this.restTemplate = restTemplate;
        this.utilService = utilService;
    }

    public List<MovimientoBancario> movimientoBancarios(){
        List<MovimientoBancario> movimientoBancarios = new ArrayList<>();
        ResponseEntity<MovimientoBancario[]> response = restTemplate.getForEntity(URL_G2_ADMINISTRACION,
                MovimientoBancario[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            MovimientoBancario[] movimientos = response.getBody();
            if (movimientos != null) movimientoBancarios = Arrays.asList(movimientos);
        }
        return movimientoBancarios;
    }

    public ResponseEntity<String> enviarMovimientoFactura(Factura factura){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MovimientoRequest movimientoRequest = crearMovimientoFactura(factura,
                utilService.clienteOrElseThrow(factura.getClienteDni(), "/integracion/facturas"));
        HttpEntity<MovimientoRequest> requestEntity = new HttpEntity<>(movimientoRequest, headers);

        return restTemplate.exchange(URL_G2_MOVIMIENTO, HttpMethod.POST, requestEntity, String.class);
    }

    private MovimientoRequest crearMovimientoFactura(Factura factura, Cliente cliente){
        MovimientoRequest movimientoRequest = new MovimientoRequest();
        movimientoRequest.setCodigo_unico(calcularCodigoUnico(cliente.getDni()));
        movimientoRequest.setConcepto("F-" + factura.getId());
        movimientoRequest.setMonto(factura.getImporte());
        movimientoRequest.setDocumento(Integer.parseInt(cliente.getDni()));
        movimientoRequest.setNombre_completo(cliente.getNombre() + " " + cliente.getApellido());
        return movimientoRequest;
    }

    private String calcularCodigoUnico(String dni){
        StringBuilder sb = new StringBuilder();
        sb.append("22");
        sb.append("0".repeat(12));
        sb.append(dni);
        return sb.toString();
    }
}
