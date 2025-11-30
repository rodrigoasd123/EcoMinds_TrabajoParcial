package com.upc.products.services;

import com.upc.products.entities.Evento;
import com.upc.products.security.entities.User;

import java.util.List;

public interface EventoService {
    
    List<Evento> listarTodos();
    
    List<Evento> listarPorOrganizador(Long organizadorId);
    
    Evento buscarPorId(Long id);
    
    Evento crearEvento(Evento evento);
    
    Evento editarEvento(Evento evento);
    
    void eliminarEvento(Long id);
    
    boolean inscribirUsuario(User usuario, Long eventoId);
    
    List<Evento> listarEventosInscritos(User usuario);
}