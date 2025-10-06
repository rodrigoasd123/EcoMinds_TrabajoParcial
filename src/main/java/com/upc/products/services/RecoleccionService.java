package com.upc.products.services;

import com.upc.products.entities.Recoleccion;
import com.upc.products.security.entities.User;

import java.util.List;

public interface RecoleccionService {
    
    List<Recoleccion> listarTodos();
    
    List<Recoleccion> listarPorUsuario(User usuario);
    
    Recoleccion registrarRecoleccion(User usuario, Long materialId, Long puntoAcopioId, Double peso);
    
    Recoleccion buscarPorId(Long id);
}