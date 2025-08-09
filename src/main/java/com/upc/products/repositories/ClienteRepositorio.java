package com.upc.products.repositories;

import com.upc.products.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.CharBuffer;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
