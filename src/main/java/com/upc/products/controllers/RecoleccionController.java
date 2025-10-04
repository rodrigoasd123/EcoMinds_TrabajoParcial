package com.upc.products.controllers;

import com.upc.products.entities.Recoleccion;
import com.upc.products.entities.MaterialReciclaje;
import com.upc.products.entities.PuntoAcopio;
import com.upc.products.security.entities.User;
import com.upc.products.security.repositories.UserRepository;
import com.upc.products.repositories.RecoleccionRepository;
import com.upc.products.repositories.MaterialReciclajeRepository;
import com.upc.products.repositories.PuntoAcopioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true")
@RestController
@RequestMapping("/api/recolecciones")
public class RecoleccionController {

    @Autowired
    private RecoleccionRepository recoleccionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MaterialReciclajeRepository materialRepository;
    
    @Autowired
    private PuntoAcopioRepository puntoAcopioRepository;

    // US-002: Registrar recolección de residuos
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarRecoleccion(
            @RequestBody Map<String, Object> request, 
            Authentication authentication) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Obtener usuario autenticado
            String username = authentication.getName();
            User usuario = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Extraer datos de la request
            Double peso = Double.valueOf(request.get("peso").toString());
            Long materialId = Long.valueOf(request.get("materialId").toString());
            Long puntoAcopioId = Long.valueOf(request.get("puntoAcopioId").toString());
            
            // Validar que existan el material y punto de acopio
            MaterialReciclaje material = materialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));
                    
            PuntoAcopio puntoAcopio = puntoAcopioRepository.findById(puntoAcopioId)
                    .orElseThrow(() -> new RuntimeException("Punto de acopio no encontrado"));
            
            // Crear nueva recolección
            Recoleccion recoleccion = new Recoleccion();
            recoleccion.setFecha(LocalDate.now());
            recoleccion.setHora(LocalTime.now());
            recoleccion.setPeso(peso);
            recoleccion.setUsuario(usuario);
            recoleccion.setMaterial(material);
            recoleccion.setPuntoAcopio(puntoAcopio);
            
            // Guardar recolección
            recoleccionRepository.save(recoleccion);
            
            // Calcular y asignar puntos (1 punto por kg aproximadamente)
            int puntosGanados = (int) Math.ceil(peso);
            usuario.setPuntos(usuario.getPuntos() + puntosGanados);
            
            // Actualizar peso total reciclado
            usuario.setPeso_reciclado(
                (usuario.getPeso_reciclado() != null ? usuario.getPeso_reciclado() : 0.0) + peso
            );
            
            userRepository.save(usuario);
            
            response.put("message", "Recolección registrada exitosamente");
            response.put("puntosGanados", puntosGanados);
            response.put("puntosTotal", usuario.getPuntos());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Error al registrar recolección: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}