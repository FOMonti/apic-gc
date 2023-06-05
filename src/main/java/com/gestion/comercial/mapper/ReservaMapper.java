package com.gestion.comercial.mapper;

import com.gestion.comercial.dto.ReservaResponse;
import com.gestion.comercial.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public ReservaResponse entityAResponse(Reserva reserva) {
        ReservaResponse reservaResponse = new ReservaResponse();
        reservaResponse.setId(reserva.getId());
        reservaResponse.setPatente(reserva.getPatente());
        reservaResponse.setImporte(reserva.getImporte());
        reservaResponse.setFechaCreacion(reserva.getFecha());
        reservaResponse.setFechaVencimiento(reserva.getFechaVencimiento());
        reservaResponse.setEstadoReserva(reserva.getEstadoReserva());
        reservaResponse.setFechaVencimientoPago(reserva.getFechaVencimientoPago());
        return reservaResponse;
    }
}
