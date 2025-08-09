package com.upc.products.repositories;

import com.upc.products.entities.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepositorio extends JpaRepository<TipoProducto, Long> {
}
