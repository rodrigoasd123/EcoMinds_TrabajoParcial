package com.upc.products.controllers;

import com.upc.products.dtos.OrganizadorDTO;
import com.upc.products.entities.Organizador;
import com.upc.products.services.OrganizadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrganizadorController {

    @Autowired
    private OrganizadorService organizadorService;
    
    @Autowired
    private ModelMapper modelMapper;

    // US-002: Listar organizadores (ADMIN)
    @GetMapping("/organizadores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> listarOrganizadores() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Organizador> organizadores = organizadorService.listarTodos();
            List<OrganizadorDTO> organizadoresDTO = organizadores.stream()
                .map(organizador -> modelMapper.map(organizador, OrganizadorDTO.class))
                .collect(Collectors.toList());
            
            response.put("success", true);
            response.put("organizadores", organizadoresDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al listar organizadores: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // US-003: Crear organizador (ADMIN)
    @PostMapping("/organizador")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> crearOrganizador(@RequestBody OrganizadorDTO organizadorDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Organizador organizador = modelMapper.map(organizadorDTO, Organizador.class);
            Organizador nuevoOrganizador = organizadorService.insertar(organizador);
            OrganizadorDTO responseDTO = modelMapper.map(nuevoOrganizador, OrganizadorDTO.class);
            
            response.put("success", true);
            response.put("message", "Organizador creado exitosamente");
            response.put("organizador", responseDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear organizador: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // US-004: Actualizar organizador (ADMIN)
    @PutMapping("/organizador/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> actualizarOrganizador(@PathVariable Long id, @RequestBody OrganizadorDTO organizadorDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Organizador organizador = modelMapper.map(organizadorDTO, Organizador.class);
            organizador.setId(id); // Setear el ID para actualizar
            Organizador organizadorActualizado = organizadorService.editar(organizador);
            OrganizadorDTO responseDTO = modelMapper.map(organizadorActualizado, OrganizadorDTO.class);
            
            response.put("success", true);
            response.put("message", "Organizador actualizado exitosamente");
            response.put("organizador", responseDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar organizador: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // US-005: Eliminar organizador (ADMIN)
    @DeleteMapping("/organizador/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> eliminarOrganizador(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            organizadorService.eliminar(id);
            response.put("success", true);
            response.put("message", "Organizador eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar organizador: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Obtener organizador por ID
    @GetMapping("/organizador/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZADOR')")
    public ResponseEntity<Map<String, Object>> obtenerOrganizador(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Organizador organizador = organizadorService.buscarPorId(id);
            OrganizadorDTO organizadorDTO = modelMapper.map(organizador, OrganizadorDTO.class);
            
            response.put("success", true);
            response.put("organizador", organizadorDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener organizador: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}