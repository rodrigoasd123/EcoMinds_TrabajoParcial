package com.upc.products.controllers;

import com.upc.products.entities.Canje;
import com.upc.products.entities.Recompensa;
import com.upc.products.repositories.CanjeRepository;
import com.upc.products.repositories.RecompensaRepository;
import com.upc.products.security.entities.User;
import com.upc.products.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class CanjeController {

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CanjeRepository canjeRepository;

    @PostMapping("/canjear/{recompensaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> canjearRecompensa(
            @PathVariable Long recompensaId,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {

            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificar que la recompensa existe
            Recompensa recompensa = recompensaRepository.findById(recompensaId)
                .orElseThrow(() -> new RuntimeException("Recompensa no encontrada"));

            // Verificar que el usuario tiene puntos suficientes
            Integer puntosUsuario = user.getPuntos() != null ? user.getPuntos() : 0;

            if (puntosUsuario < recompensa.getPuntosRequeridos()) {
                response.put("success", false);
                response.put("message", "No tienes suficientes puntos para esta recompensa");
                response.put("puntosNecesarios", recompensa.getPuntosRequeridos());
                response.put("puntosActuales", puntosUsuario);
                return ResponseEntity.badRequest().body(response);
            }

            // Descontar puntos al usuario
            int puntosRestantes = puntosUsuario - recompensa.getPuntosRequeridos();
            user.setPuntos(puntosRestantes);
            userRepository.save(user);

            // Crear y guardar el registro de canje en la base de datos
            Canje canje = new Canje();
            canje.setUsuario(user);
            canje.setRecompensa(recompensa);
            canje.setFecha(LocalDate.now());
            canje.setHora(LocalTime.now());
            canje.setCosto(recompensa.getPuntosRequeridos());
            canjeRepository.save(canje);

            response.put("success", true);
            response.put("message", "¡Recompensa canjeada exitosamente!");
            response.put("recompensa", recompensa.getNombre());
            response.put("puntosDescontados", recompensa.getPuntosRequeridos());
            response.put("puntosRestantes", puntosRestantes);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al canjear recompensa: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
//parte jorge hecha 
    // Verificar puntos disponibles del usuario
    @GetMapping("/mis-puntos")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Map<String, Object>> misPuntos(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Integer puntos = user.getPuntos() != null ? user.getPuntos() : 0;
            Double pesoReciclado = user.getPeso_reciclado() != null ? user.getPeso_reciclado() : 0.0;
            
            response.put("success", true);
            response.put("puntos", puntos);
            response.put("pesoReciclado", pesoReciclado);
            response.put("nombre", user.getNombre());
            response.put("apellido", user.getApellido());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener puntos: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
