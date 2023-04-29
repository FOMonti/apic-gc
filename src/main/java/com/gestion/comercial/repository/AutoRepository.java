package com.gestion.comercial.repository;

import com.gestion.comercial.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutoRepository extends JpaRepository<Auto,String> {

    Optional<Auto> findOptionalById(String id);

}
