package com.upc.products.repositories;

import com.upc.products.entities.UsuarioEvento;
import com.upc.products.entities.Evento;
import com.upc.products.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioEventoRepository extends JpaRepository<UsuarioEvento, Long> {
    
    // Verificar si usuario ya está inscrito en evento
    boolean existsByUsuarioAndEvento(User usuario, Evento evento);
    
    // Encontrar inscripciones por usuario
    List<UsuarioEvento> findByUsuario(User usuario);
    
    // Encontrar inscripciones por evento
    List<UsuarioEvento> findByEvento(Evento evento);
    
    // Eliminar inscripciones por evento (para cuando se elimine un evento)
    void deleteByEvento(Evento evento);
    
    // Eliminar inscripción específica
    void deleteByUsuarioAndEvento(User usuario, Evento evento);
}