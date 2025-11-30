package com.upc.products.controllers;

import com.upc.products.security.entities.User;
import com.upc.products.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class UsuarioController {
    
    @Autowired
    private UserService userService;

    // Obtener perfil del usuario autenticado
    @GetMapping("/perfil")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obtenerPerfil() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User usuario = userService.findByUsername(username);
        
        Map<String, Object> response = new HashMap<>();
        response.put("username", usuario.getUsername());
        response.put("nombre", usuario.getNombre());
        response.put("apellido", usuario.getApellido());
        response.put("correo", usuario.getCorreo());
        response.put("pesoReciclado", usuario.getPeso_reciclado());
        response.put("puntos", usuario.getPuntos());
        
        return ResponseEntity.ok(response);
    }

    // Actualizar perfil del usuario
    @PutMapping("/perfil")
    @PreAuthorize("hasRole('USER') or hasRole('ORGANIZADOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> actualizarPerfil(@RequestBody Map<String, String> datos) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User usuario = userService.findByUsername(username);
        
        if (datos.containsKey("nombre")) {
            usuario.setNombre(datos.get("nombre"));
        }
        if (datos.containsKey("apellido")) {
            usuario.setApellido(datos.get("apellido"));
        }
        if (datos.containsKey("correo")) {
            usuario.setCorreo(datos.get("correo"));
        }
        
        userService.save(usuario);
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Perfil actualizado correctamente");
        response.put("usuario", usuario);
        
        return ResponseEntity.ok(response);
    }

    // Listar todos los usuarios (solo para ADMIN)
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> usuarios = userService.findAll();
        return ResponseEntity.ok(usuarios);
    }
}
