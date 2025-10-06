package com.upc.products.controllers;

import com.upc.products.entities.PuntoAcopio;
import com.upc.products.repositories.PuntoAcopioRepository;
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
public class PuntoAcopioController {

    @Autowired
    private PuntoAcopioRepository puntoAcopioRepository;

    // Listar todos los puntos de acopio (ADMIN y USER pueden ver)
    @GetMapping("/puntos-acopio")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<PuntoAcopio>> listarPuntosAcopio() {
        List<PuntoAcopio> puntos = puntoAcopioRepository.findAll();
        return ResponseEntity.ok(puntos);
    }

    // Crear nuevo punto de acopio (solo ADMIN)
    @PostMapping("/punto-acopio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> crearPuntoAcopio(@RequestBody PuntoAcopio puntoAcopio) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            PuntoAcopio nuevoPunto = puntoAcopioRepository.save(puntoAcopio);
            response.put("success", true);
            response.put("message", "Punto de acopio creado exitosamente");
            response.put("puntoAcopio", nuevoPunto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear punto de acopio: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Actualizar punto de acopio (solo ADMIN)
    @PutMapping("/punto-acopio/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> actualizarPuntoAcopio(
            @PathVariable Long id, 
            @RequestBody PuntoAcopio puntoAcopio) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (puntoAcopioRepository.existsById(id)) {
                puntoAcopio.setIdAcopio(id);
                PuntoAcopio puntoActualizado = puntoAcopioRepository.save(puntoAcopio);
                response.put("success", true);
                response.put("message", "Punto de acopio actualizado exitosamente");
                response.put("puntoAcopio", puntoActualizado);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Punto de acopio no encontrado");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar punto de acopio: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Eliminar punto de acopio (solo ADMIN)
    @DeleteMapping("/punto-acopio/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarPuntoAcopio(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        
        try {
            if (puntoAcopioRepository.existsById(id)) {
                puntoAcopioRepository.deleteById(id);
                response.put("success", "true");
                response.put("message", "Punto de acopio eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", "false");
                response.put("message", "Punto de acopio no encontrado");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error al eliminar punto de acopio: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Buscar punto de acopio por ID
    @GetMapping("/punto-acopio/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PuntoAcopio> buscarPuntoAcopio(@PathVariable Long id) {
        return puntoAcopioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}