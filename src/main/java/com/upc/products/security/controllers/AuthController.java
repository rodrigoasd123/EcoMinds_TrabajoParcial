package com.upc.products.security.controllers;

import com.upc.products.security.dtos.AuthRequestDTO;
import com.upc.products.security.dtos.AuthResponseDTO;
import com.upc.products.security.dtos.RegistroRequestDTO;
import com.upc.products.security.entities.User;
import com.upc.products.security.entities.Role;
import com.upc.products.security.services.CustomUserDetailsService;
import com.upc.products.security.repositories.UserRepository;
import com.upc.products.security.repositories.RoleRepository;
import com.upc.products.security.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "${ip.frontend}")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, 
                         CustomUserDetailsService userDetailsService, UserRepository userRepository, 
                         RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // US-007: Iniciar sesión en la aplicación
    @PostMapping({"/authenticate", "/login"})
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setJwt(token);
        authResponseDTO.setUsername(authRequest.getUsername());
        authResponseDTO.setRoles(roles);
        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }

    // US-001: Crear una cuenta en la aplicación
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registrarUsuario(@RequestBody RegistroRequestDTO registroRequest) {
        Map<String, String> response = new HashMap<>();
        
        try {
            // Verificar si el usuario ya existe
            if (userRepository.existsByUsername(registroRequest.getUsername())) {
                response.put("error", "El nombre de usuario ya existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            if (userRepository.existsByCorreo(registroRequest.getCorreo())) {
                response.put("error", "El correo ya está registrado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Crear nuevo usuario
            User nuevoUsuario = new User();
            nuevoUsuario.setUsername(registroRequest.getUsername());
            nuevoUsuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));
            nuevoUsuario.setNombre(registroRequest.getNombre());
            nuevoUsuario.setApellido(registroRequest.getApellido());
            nuevoUsuario.setCorreo(registroRequest.getCorreo());
            nuevoUsuario.setPeso_reciclado(0.0);
            nuevoUsuario.setPuntos(0);
            
            // Asignar rol USER por defecto
            Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            nuevoUsuario.getRoles().add(userRole);
            
            // Guardar usuario
            userRepository.save(nuevoUsuario);
            
            response.put("message", "Usuario registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}


