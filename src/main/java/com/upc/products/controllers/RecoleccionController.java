package com.upc.products.controllers;

import com.upc.products.entities.Recoleccion;
import com.upc.products.entities.MaterialReciclaje;
import com.upc.products.entities.PuntoAcopio;
import com.upc.products.dtos.CrearRecoleccionRequestDTO;
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
            @RequestBody CrearRecoleccionRequestDTO requestDTO,
            Authentication auth) {

        Map<String, Object> respuesta = new HashMap<>();

        try {
            // Validar request
            if (requestDTO == null) {
                respuesta.put("success", false);
                respuesta.put("message", "Datos de recolección requeridos");
                return ResponseEntity.badRequest().body(respuesta);
            }

            // Obtener usuario autenticado
            String username = auth.getName();
            User usuario = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Validar datos del DTO
            Double peso = requestDTO.getPeso();
            Long idMaterial = requestDTO.getIdMaterial();
            Long idPunto = requestDTO.getIdPunto();

            if (peso == null || peso <= 0) {
                respuesta.put("success", false);
                respuesta.put("message", "Peso debe ser mayor a 0");
                return ResponseEntity.badRequest().body(respuesta);
            }

            if (idMaterial == null) {
                respuesta.put("success", false);
                respuesta.put("message", "ID de material es requerido");
                return ResponseEntity.badRequest().body(respuesta);
            }

            if (idPunto == null) {
                respuesta.put("success", false);
                respuesta.put("message", "ID de punto de acopio es requerido");
                return ResponseEntity.badRequest().body(respuesta);
            }

            // Buscar material y punto de acopio
            MaterialReciclaje material = materialRepository.findById(idMaterial)
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));
                    
            PuntoAcopio puntoAcopio = puntoAcopioRepository.findById(idPunto)
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
            
            // Calcular puntos
            int puntosGanados = (int) Math.ceil(peso);
            usuario.setPuntos(usuario.getPuntos() + puntosGanados);
            
            // Actualizar peso total
            Double pesoAnterior = usuario.getPeso_reciclado();
            usuario.setPeso_reciclado((pesoAnterior != null ? pesoAnterior : 0.0) + peso);

            userRepository.save(usuario);

            respuesta.put("success", true);
            respuesta.put("message", "Recolección registrada exitosamente");
            respuesta.put("puntosGanados", puntosGanados);
            respuesta.put("puntosTotal", usuario.getPuntos());

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al registrar recolección: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Endpoint para obtener el formato JSON
    @GetMapping("/formato-registro")
    public ResponseEntity<Map<String, Object>> obtenerFormatoRegistro() {
        Map<String, Object> formato = new HashMap<>();

        Map<String, Object> ejemplo = new HashMap<>();
        ejemplo.put("idMaterial", 1L);
        ejemplo.put("idPunto", 1L);
        ejemplo.put("peso", 2.5);

        formato.put("ejemplo", ejemplo);
        formato.put("descripcion", "Formato JSON para registrar una recolección");
        formato.put("endpoint", "POST /api/recolecciones/registrar");

        Map<String, String> campos = new HashMap<>();
        campos.put("idMaterial", "ID del material a reciclar (Long)");
        campos.put("idPunto", "ID del punto de acopio (Long)");
        campos.put("peso", "Peso en kilogramos (Double)");

        formato.put("campos", campos);

        return ResponseEntity.ok(formato);
    }
}
