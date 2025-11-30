package com.upc.products.repositories;

import com.upc.products.entities.Evento;
import com.upc.products.entities.Organizador;
import com.upc.products.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Ordenar eventos por fecha y hora ascendente
    List<Evento> findAllByOrderByFechaAscHoraAsc();
    
    // Buscar eventos por usuario inscrito
    @Query("SELECT e FROM Evento e JOIN UsuarioEvento ue ON e = ue.evento WHERE ue.usuario = :usuario")
    List<Evento> findEventosByUsuario(@Param("usuario") User usuario);
    
    // Buscar eventos por organizador
    List<Evento> findByOrganizador(Organizador organizador);
    
    // Buscar eventos por ID del organizador
    @Query("SELECT e FROM Evento e WHERE e.organizador.id = :idOrganizador")
    List<Evento> findByOrganizadorId(@Param("idOrganizador") Long idOrganizador);
}