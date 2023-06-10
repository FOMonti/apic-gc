package com.gestion.comercial.repository;

import com.gestion.comercial.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Long countFacturaBySucursal(String sucursal);

    List<Factura> findFacturaByCotizacionID(Long id);

    List<Factura> findFacturaByPatente(String patente);
}
