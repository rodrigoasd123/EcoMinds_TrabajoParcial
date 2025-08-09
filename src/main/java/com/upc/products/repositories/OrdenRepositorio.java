package com.upc.products.repositories;

import com.upc.products.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepositorio extends JpaRepository<Orden, Long> {
}
