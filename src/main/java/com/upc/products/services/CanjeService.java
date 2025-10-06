package com.upc.products.services;

import com.upc.products.entities.Canje;
import com.upc.products.security.entities.User;

import java.util.List;

public interface CanjeService {
    
    List<Canje> listarTodos();
    
    List<Canje> listarPorUsuario(User usuario);
    
    Canje canjearRecompensa(User usuario, Long recompensaId);
    
    Canje buscarPorId(Long id);
    
    long contarCanjesPorUsuario(User usuario);
}