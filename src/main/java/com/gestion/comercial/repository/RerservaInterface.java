package com.gestion.comercial.repository;

import com.gestion.comercial.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RerservaInterface extends JpaRepository<Reserva,Long> {
}
