package com.upc.products.repositories;

import com.upc.products.entities.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {
    
    // Encontrar recompensas con requisito menor o igual a los puntos dados (método antiguo)
    List<Recompensa> findByRequisitoLessThanEqual(String requisito);
    
    // Encontrar recompensas por descripción (búsqueda parcial)
    List<Recompensa> findByDescripcionContainingIgnoreCase(String descripcion);
    
    // Nuevos métodos para puntos requeridos
    List<Recompensa> findByPuntosRequeridosLessThanEqual(Integer puntosRequeridos);
    
    // US-018, US-019: Ordenar recompensas por puntos
    List<Recompensa> findAllByOrderByPuntosRequeridosAsc();
    List<Recompensa> findAllByOrderByPuntosRequeridosDesc();
    
    // Ordenar por nombre
    List<Recompensa> findAllByOrderByNombreAsc();
    
    // Buscar por nombre
    List<Recompensa> findByNombreContainingIgnoreCase(String nombre);
}