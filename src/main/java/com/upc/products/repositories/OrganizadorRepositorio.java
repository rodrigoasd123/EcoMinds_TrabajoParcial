package com.upc.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.upc.products.entities.Organizador;
import java.util.Optional;

@Repository
public interface OrganizadorRepositorio extends JpaRepository<Organizador, Long> {
    
    Optional<Organizador> findByNombre(String nombre);
}