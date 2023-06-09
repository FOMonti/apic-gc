package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.ClienteResponse;
import com.gestion.comercial.dto.ReservaResponse;
import com.gestion.comercial.entity.Reserva;
import com.gestion.comercial.exception.ValidationException;
import com.gestion.comercial.repository.ClienteRepository;
import com.gestion.comercial.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ReservaMapper {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteService clienteService;
    @Autowired
    public ReservaMapper(ClienteRepository clienteRepository, ClienteMapper clienteMapper,
                         ClienteService clienteService){
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.clienteService = clienteService;
    }

    public ReservaResponse entityAResponse(Reserva reserva) {
        ReservaResponse reservaResponse = new ReservaResponse();
        reservaResponse.setId(reserva.getId());
        reservaResponse.setPatente(reserva.getPatente());
        reservaResponse.setImporte(reserva.getImporte());
        reservaResponse.setFechaCreacion(reserva.getFecha());
        reservaResponse.setFechaVencimiento(reserva.getFechaVencimiento());
        reservaResponse.setEstadoReserva(reserva.getEstadoReserva());
        reservaResponse.setFechaVencimientoPago(reserva.getFechaVencimientoPago());
        reservaResponse.setClienteResponse(clienteMapper.clienteEntityAResponse(reserva.getCliente()));
        return reservaResponse;
    }

    public List<ReservaResponse> entityAResponseList(List<Reserva> reservas) {
        List<ReservaResponse> reservaResponses = new ArrayList<>();
        reservas.forEach(reserva -> {
            reservaResponses.add(entityAResponse(reserva));
        });
        return reservaResponses;
    }
}
