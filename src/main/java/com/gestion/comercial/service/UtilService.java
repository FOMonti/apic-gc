package com.gestion.comercial.service;

import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.repository.ClienteRepository;
import com.gestion.comercial.repository.CotizacionVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilService {

    private final CotizacionVentaRepository cotizacionVentaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public UtilService(CotizacionVentaRepository cotizacionVentaRepository, ClienteRepository clienteRepository){
        this.cotizacionVentaRepository = cotizacionVentaRepository;
        this.clienteRepository = clienteRepository;
    }

    protected CotizacionVenta orElseThrow(Long id, String ruta){
        Optional<CotizacionVenta> cotizacion= cotizacionVentaRepository.findById(id);
        return cotizacion.orElseThrow(() -> new ValidationException
                ("No se encontró una cotización de venta con el ID:{" + id +"}", ruta));
    }

    public Cliente orElseThrow(String dni, String ruta){
        Optional<Cliente> cliente = clienteRepository.getClienteByDni(dni);
        return cliente.orElseThrow(() -> new ValidationException
                ("No se encontró un cliente con el dni:{" + dni + "}", ruta));
    }
}
