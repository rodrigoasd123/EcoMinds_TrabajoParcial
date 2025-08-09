package com.upc.products.repositories;

import com.upc.products.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRespositorio extends JpaRepository<Producto, Long> {
}
