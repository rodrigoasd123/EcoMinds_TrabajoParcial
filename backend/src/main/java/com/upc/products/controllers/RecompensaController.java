package com.upc.products.controllers;

import com.upc.products.entities.Recompensa;
import com.upc.products.repositories.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class RecompensaController {

    @Autowired
    private RecompensaRepository recompensaRepository;

    // Listar todas las recompensas (ADMIN y USER pueden ver)
    @GetMapping("/recompensas")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Recompensa>> listarRecompensas() {
        List<Recompensa> recompensas = recompensaRepository.findAll();
        return ResponseEntity.ok(recompensas);
    }

    // Crear nueva recompensa (solo ADMIN)
    @PostMapping("/recompensa")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> crearRecompensa(@RequestBody Recompensa recompensa) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Recompensa nuevaRecompensa = recompensaRepository.save(recompensa);
            response.put("success", true);
            response.put("message", "Recompensa creada exitosamente");
            response.put("recompensa", nuevaRecompensa);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear recompensa: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Actualizar recompensa (solo ADMIN)
    @PutMapping("/recompensa/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> actualizarRecompensa(
            @PathVariable Long id, 
            @RequestBody Recompensa recompensa) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (recompensaRepository.existsById(id)) {
                recompensa.setIdRecompensa(id);
                Recompensa recompensaActualizada = recompensaRepository.save(recompensa);
                response.put("success", true);
                response.put("message", "Recompensa actualizada exitosamente");
                response.put("recompensa", recompensaActualizada);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Recompensa no encontrada");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar recompensa: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Eliminar recompensa (solo ADMIN)
    @DeleteMapping("/recompensa/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarRecompensa(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        
        try {
            if (recompensaRepository.existsById(id)) {
                recompensaRepository.deleteById(id);
                response.put("success", "true");
                response.put("message", "Recompensa eliminada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", "false");
                response.put("message", "Recompensa no encontrada");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error al eliminar recompensa: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Buscar recompensa por ID
    @GetMapping("/recompensa/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Recompensa> buscarRecompensa(@PathVariable Long id) {
        return recompensaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar recompensas disponibles para un usuario (por puntos)
    @GetMapping("/recompensas/disponibles/{puntos}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Recompensa>> listarRecompensasDisponibles(@PathVariable Integer puntos) {
        List<Recompensa> recompensasDisponibles = recompensaRepository.findByPuntosRequeridosLessThanEqual(puntos);
        return ResponseEntity.ok(recompensasDisponibles);
    }

    // US-018: Ordenar recompensas por puntos requeridos (menor a mayor)
    @GetMapping("/recompensas/ordenar/puntos-asc")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Recompensa>> ordenarRecompensasPuntosMenorMayor() {
        List<Recompensa> recompensasOrdenadas = recompensaRepository.findAllByOrderByPuntosRequeridosAsc();
        return ResponseEntity.ok(recompensasOrdenadas);
    }

    // US-019: Ordenar recompensas por puntos requeridos (mayor a menor)
    @GetMapping("/recompensas/ordenar/puntos-desc")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Recompensa>> ordenarRecompensasPuntosMayorMenor() {
        List<Recompensa> recompensasOrdenadas = recompensaRepository.findAllByOrderByPuntosRequeridosDesc();
        return ResponseEntity.ok(recompensasOrdenadas);
    }

    // Ordenar recompensas por nombre alfabético
    @GetMapping("/recompensas/ordenar/nombre")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Recompensa>> ordenarRecompensasPorNombre() {
        List<Recompensa> recompensasOrdenadas = recompensaRepository.findAllByOrderByNombreAsc();
        return ResponseEntity.ok(recompensasOrdenadas);
    }
}