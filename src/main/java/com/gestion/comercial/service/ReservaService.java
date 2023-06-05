package com.gestion.comercial.service;

import com.gestion.comercial.dto.ClienteRequest;
import com.gestion.comercial.dto.ReservaResponse;
import com.gestion.comercial.entity.Cliente;
import com.gestion.comercial.entity.Reserva;
import com.gestion.comercial.mapper.ClienteMapper;
import com.gestion.comercial.types.EstadoReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservaService {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ReservaService(ClienteService clienteService, ClienteMapper clienteMapper){
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    public ReservaResponse save(ClienteRequest clienteRequest, String patente) {
        LocalDate fechaActual = LocalDate.now();
        Cliente cliente = clienteMapper.clienteRequestAEntity(clienteRequest);
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);
        reserva.setFechaVencimiento(fechaActual.plusDays(15));
        reserva.setFechaVencimientoPago(fechaActual.plusDays(2));
        reserva.setPatente(patente);
        return null;
    }
}
