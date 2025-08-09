package com.upc.products.repositories;

import com.upc.products.entities.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenRepositorio extends JpaRepository<DetalleOrden, Long> {
}
