package com.gestion.comercial.repository;

import com.gestion.comercial.entity.CotizacionVenta;
import com.gestion.comercial.entity.GastoAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoAdministrativoRepository extends JpaRepository<GastoAdministrativo, Long> {


    List<GastoAdministrativo> findAllByCotizacionVenta(CotizacionVenta cotizacionVenta);
}
