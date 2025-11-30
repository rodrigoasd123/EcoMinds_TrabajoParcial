package com.upc.products.repositories;

import com.upc.products.entities.Canje;
import com.upc.products.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanjeRepository extends JpaRepository<Canje, Long> {
    
    // Buscar canjes por usuario
    List<Canje> findByUsuario(User usuario);
    
    // Buscar canjes por usuario ordenados por fecha descendente (más recientes primero)
    @Query("SELECT c FROM Canje c WHERE c.usuario = :usuario ORDER BY c.fecha DESC")
    List<Canje> findByUsuarioOrderByFechaDesc(@Param("usuario") User usuario);
    
    // Contar canjes por usuario
    long countByUsuario(User usuario);
    
    // Buscar todos los canjes ordenados por fecha descendente
    List<Canje> findAllByOrderByFechaDesc();
    
    // Buscar canjes por recompensa
    @Query("SELECT c FROM Canje c WHERE c.recompensa.id = :recompensaId")
    List<Canje> findByRecompensaId(@Param("recompensaId") Long recompensaId);
}