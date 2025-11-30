package com.upc.products.controllers;

import com.upc.products.entities.MaterialReciclaje;
import com.upc.products.repositories.MaterialReciclajeRepository;
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
public class MaterialReciclajeController {

    @Autowired
    private MaterialReciclajeRepository materialRepository;

    // Listar todos los materiales (ADMIN y USER pueden ver)
    @GetMapping("/materiales")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<MaterialReciclaje>> listarMateriales() {
        List<MaterialReciclaje> materiales = materialRepository.findAll();
        return ResponseEntity.ok(materiales);
    }

    // Crear nuevo material (solo ADMIN)
    @PostMapping("/material")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> crearMaterial(@RequestBody MaterialReciclaje material) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            MaterialReciclaje nuevoMaterial = materialRepository.save(material);
            response.put("success", true);
            response.put("message", "Material de reciclaje creado exitosamente");
            response.put("material", nuevoMaterial);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear material: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Actualizar material (solo ADMIN)
    @PutMapping("/material/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> actualizarMaterial(
            @PathVariable Long id, 
            @RequestBody MaterialReciclaje material) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (materialRepository.existsById(id)) {
                material.setIdMateriales(id);
                MaterialReciclaje materialActualizado = materialRepository.save(material);
                response.put("success", true);
                response.put("message", "Material actualizado exitosamente");
                response.put("material", materialActualizado);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Material no encontrado");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar material: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Eliminar material (solo ADMIN)
    @DeleteMapping("/material/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarMaterial(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        
        try {
            if (materialRepository.existsById(id)) {
                materialRepository.deleteById(id);
                response.put("success", "true");
                response.put("message", "Material eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", "false");
                response.put("message", "Material no encontrado");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error al eliminar material: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Buscar material por ID
    @GetMapping("/material/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MaterialReciclaje> buscarMaterial(@PathVariable Long id) {
        return materialRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}