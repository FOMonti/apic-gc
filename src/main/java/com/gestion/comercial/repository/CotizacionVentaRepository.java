package com.gestion.comercial.repository;

import com.gestion.comercial.entity.CotizacionVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CotizacionVentaRepository extends JpaRepository<CotizacionVenta,Long>{

    Long countCotizacionVentaBySucursal(String sucursal);

    @Override
    Optional<CotizacionVenta> findById(Long id);
}
