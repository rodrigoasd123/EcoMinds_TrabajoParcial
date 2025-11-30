package com.upc.products.services;

import com.upc.products.entities.Recompensa;

import java.util.List;

public interface RecompensaService {
    
    List<Recompensa> listarTodos();
    
    List<Recompensa> listarPorPuntosAsc();
    
    List<Recompensa> listarPorPuntosDesc();
    
    List<Recompensa> listarPorNombre();
    
    Recompensa buscarPorId(Long id);
    
    Recompensa insertar(Recompensa recompensa);
    
    Recompensa editar(Recompensa recompensa);
    
    void eliminar(Long id);
}