package com.upc.products.controllers;

import com.upc.products.security.entities.User;
import com.upc.products.security.services.UserService;
import com.upc.products.entities.*;
import com.upc.products.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api/admin")
public class AdminTestController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private MaterialReciclajeRepository materialRepository;
    
    @Autowired
    private PuntoAcopioRepository puntoAcopioRepository;
    
    @Autowired
    private RecompensaRepository recompensaRepository;
    
    @Autowired
    private EventoRepository eventoRepository;
    
    @Autowired
    private RecoleccionRepository recoleccionRepository;
    
    @Autowired
    private OrganizadorRepositorio organizadorRepository;

    // Endpoint para probar que el admin puede acceder
    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> testAdmin(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "¡Admin acceso exitoso!");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    // Admin puede ver estadísticas del sistema de reciclaje
    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getEstadisticas() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalUsuarios", userService.findAll().size());
        response.put("totalMateriales", materialRepository.findAll().size());
        response.put("totalPuntosAcopio", puntoAcopioRepository.findAll().size());
        response.put("totalRecompensas", recompensaRepository.findAll().size());
        response.put("totalEventos", eventoRepository.findAll().size());
        response.put("totalRecolecciones", recoleccionRepository.findAll().size());
        response.put("totalOrganizadores", organizadorRepository.findAll().size());
        return ResponseEntity.ok(response);
    }

    // Admin puede ver todos los usuarios
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsuarios() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Admin puede ver todos los materiales de reciclaje
    @GetMapping("/materiales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MaterialReciclaje>> getAllMateriales() {
        return ResponseEntity.ok(materialRepository.findAll());
    }

    // Admin puede ver todos los puntos de acopio
    @GetMapping("/puntos-acopio")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PuntoAcopio>> getAllPuntosAcopio() {
        return ResponseEntity.ok(puntoAcopioRepository.findAll());
    }

    // Admin puede ver todas las recompensas
    @GetMapping("/recompensas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Recompensa>> getAllRecompensas() {
        return ResponseEntity.ok(recompensaRepository.findAll());
    }

    // Admin puede ver todos los eventos
    @GetMapping("/eventos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Evento>> getAllEventos() {
        return ResponseEntity.ok(eventoRepository.findAll());
    }

    // Admin puede ver todas las recolecciones
    @GetMapping("/recolecciones")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Recoleccion>> getAllRecolecciones() {
        return ResponseEntity.ok(recoleccionRepository.findAll());
    }

    // Admin puede ver todos los organizadores
    @GetMapping("/organizadores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Organizador>> getAllOrganizadores() {
        return ResponseEntity.ok(organizadorRepository.findAll());
    }

    // Endpoint para información del usuario actual
    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Map<String, Object>> whoAmI(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());
        return ResponseEntity.ok(response);
    }
}
