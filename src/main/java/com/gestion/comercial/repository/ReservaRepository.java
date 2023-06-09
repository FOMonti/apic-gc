package com.gestion.comercial.repository;

import com.gestion.comercial.entity.Reserva;
import com.gestion.comercial.types.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    List<Reserva> findReservaByClienteDniAndEstadoReservaOrderByImporteDesc(String dni, EstadoReserva estadoReserva);
}
