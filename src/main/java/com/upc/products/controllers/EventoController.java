package com.upc.products.controllers;

import com.upc.products.entities.Evento;
import com.upc.products.entities.UsuarioEvento;
import com.upc.products.entities.Organizador;
import com.upc.products.dtos.CrearEventoRequestDTO;
import com.upc.products.dtos.EditarEventoRequestDTO;
import com.upc.products.repositories.EventoRepository;
import com.upc.products.repositories.UsuarioEventoRepository;
import com.upc.products.repositories.OrganizadorRepositorio;
import com.upc.products.security.entities.User;
import com.upc.products.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioEventoRepository usuarioEventoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizadorRepositorio organizadorRepository;

    // US-004: Consultar eventos ambientales (CUALQUIER USUARIO - incluso sin autenticar)
    @GetMapping("/eventos")
    public ResponseEntity<Map<String, Object>> listarEventos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Evento> eventos = eventoRepository.findAllByOrderByFechaAscHoraAsc();

            // Convertir eventos a formato limpio sin problemas de serialización
            List<Map<String, Object>> eventosData = eventos.stream().map(evento -> {
                Map<String, Object> eventoData = new HashMap<>();
                eventoData.put("id", evento.getIdEvento());
                eventoData.put("nombre", evento.getNombre());
                eventoData.put("fecha", evento.getFecha().toString());
                eventoData.put("hora", evento.getHora().toString());
                eventoData.put("lugar", evento.getLugar());
                eventoData.put("descripcion", evento.getDescripcion());

                // Manejar organizador de forma segura - enviar como objeto
                if (evento.getOrganizador() != null) {
                    Map<String, Object> organizadorData = new HashMap<>();
                    organizadorData.put("idOrganizador", evento.getOrganizador().getId());
                    organizadorData.put("nombreOrganizador", evento.getOrganizador().getNombre());
                    eventoData.put("organizador", organizadorData);
                } else {
                    eventoData.put("organizador", null);
                }

                return eventoData;
            }).toList();

            response.put("success", true);
            response.put("message", "Eventos obtenidos exitosamente");
            response.put("eventos", eventosData);
            response.put("total", eventosData.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener eventos: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // US-008: Inscribirse en un evento ambiental (USER autenticado)
    @PostMapping("/evento/{eventoId}/inscribirse")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> inscribirseEvento(
            @PathVariable Long eventoId,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Obtener usuario autenticado
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificar que el evento existe
            Evento evento = eventoRepository.findById(eventoId)
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

            // Verificar que no esté ya inscrito usando los objetos completos
            if (usuarioEventoRepository.existsByUsuarioAndEvento(user, evento)) {
                response.put("success", false);
                response.put("message", "Ya estás inscrito en este evento");
                return ResponseEntity.badRequest().body(response);
            }

            // Crear inscripción
            UsuarioEvento inscripcion = new UsuarioEvento(user, evento);
            usuarioEventoRepository.save(inscripcion);

            response.put("success", true);
            response.put("message", "Inscripción exitosa al evento: " + evento.getNombre());
            response.put("evento", evento.getNombre());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al inscribirse: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // US-009: Crear evento (ORGANIZADOR)
    @PostMapping("/evento")
    @PreAuthorize("hasRole('ORGANIZADOR')")
    public ResponseEntity<Map<String, Object>> crearEvento(
            @RequestBody CrearEventoRequestDTO eventoRequest,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Obtener usuario autenticado
            String username = authentication.getName();

            // Buscar el organizador por nombre de usuario
            Optional<Organizador> optionalOrganizador = organizadorRepository.findByNombre(username);
            Organizador organizador;

            if (optionalOrganizador.isPresent()) {
                organizador = optionalOrganizador.get();
            } else {
                // Si no existe, crear automáticamente un organizador con el nombre del usuario
                organizador = new Organizador(username);
                organizador = organizadorRepository.save(organizador);
            }

            // Crear el evento
            Evento evento = new Evento();
            evento.setNombre(eventoRequest.getNombre());
            evento.setFecha(eventoRequest.getFecha());
            evento.setHora(eventoRequest.getHora());
            evento.setLugar(eventoRequest.getLugar());
            evento.setDescripcion(eventoRequest.getDescripcion());
            evento.setOrganizador(organizador);

            Evento nuevoEvento = eventoRepository.save(evento);
            response.put("success", true);
            response.put("message", "Evento creado exitosamente por " + organizador.getNombre());
            response.put("evento", nuevoEvento);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear evento: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // US-010: Editar descripción de evento (ORGANIZADOR o ADMIN)
    @PutMapping("/evento/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZADOR', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> editarEvento(
            @PathVariable Long id,
            @RequestBody EditarEventoRequestDTO eventoRequest,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Verificar que el evento existe
            Optional<Evento> optionalEvento = eventoRepository.findById(id);
            if (optionalEvento.isEmpty()) {
                response.put("success", false);
                response.put("message", "Evento no encontrado");
                return ResponseEntity.status(404).body(response);
            }

            Evento evento = optionalEvento.get();

            // Verificar que el organizador existe y no es null
            if (evento.getOrganizador() == null) {
                response.put("success", false);
                response.put("message", "Error: el evento no tiene un organizador asignado");
                return ResponseEntity.badRequest().body(response);
            }

            // Preservar el organizador existente
            Organizador organizador = evento.getOrganizador();

            // Actualizar solo los campos necesarios
            if (eventoRequest.getNombre() != null) {
                evento.setNombre(eventoRequest.getNombre());
            }
            if (eventoRequest.getFecha() != null) {
                evento.setFecha(eventoRequest.getFecha());
            }
            if (eventoRequest.getHora() != null) {
                evento.setHora(eventoRequest.getHora());
            }
            if (eventoRequest.getLugar() != null) {
                evento.setLugar(eventoRequest.getLugar());
            }
            if (eventoRequest.getDescripcion() != null) {
                evento.setDescripcion(eventoRequest.getDescripcion());
            }

            // Reasignar el organizador para asegurar que no se pierda
            evento.setOrganizador(organizador);

            Evento eventoActualizado = eventoRepository.save(evento);

            // Crear respuesta con datos limpios del evento actualizado
            Map<String, Object> eventoData = new HashMap<>();
            eventoData.put("id", eventoActualizado.getIdEvento());
            eventoData.put("nombre", eventoActualizado.getNombre());
            eventoData.put("fecha", eventoActualizado.getFecha().toString());
            eventoData.put("hora", eventoActualizado.getHora().toString());
            eventoData.put("lugar", eventoActualizado.getLugar());
            eventoData.put("descripcion", eventoActualizado.getDescripcion());

            if (eventoActualizado.getOrganizador() != null) {
                Map<String, Object> organizadorData = new HashMap<>();
                organizadorData.put("idOrganizador", eventoActualizado.getOrganizador().getId());
                organizadorData.put("nombreOrganizador", eventoActualizado.getOrganizador().getNombre());
                eventoData.put("organizador", organizadorData);
            } else {
                eventoData.put("organizador", null);
            }

            response.put("success", true);
            response.put("message", "Evento actualizado exitosamente");
            response.put("evento", eventoData);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar evento: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // US-011: Eliminar evento (ORGANIZADOR propietario)
    @DeleteMapping("/evento/{id}")
    @PreAuthorize("hasRole('ORGANIZADOR')")
    public ResponseEntity<Map<String, String>> eliminarEvento(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        try {
            Optional<Evento> optionalEvento = eventoRepository.findById(id);
            if (optionalEvento.isPresent()) {
                Evento evento = optionalEvento.get();
                // Primero eliminar inscripciones
                usuarioEventoRepository.deleteByEvento(evento);
                // Luego eliminar evento
                eventoRepository.deleteById(id);
                response.put("success", "true");
                response.put("message", "Evento eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", "false");
                response.put("message", "Evento no encontrado");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", "false");
            response.put("message", "Error al eliminar evento: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Buscar evento por ID (acceso público para edición de organizadores)
    @GetMapping("/evento/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Map<String, Object>> buscarEvento(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Evento> optionalEvento = eventoRepository.findById(id);
            if (optionalEvento.isEmpty()) {
                response.put("success", false);
                response.put("message", "Evento no encontrado");
                return ResponseEntity.status(404).body(response);
            }

            Evento evento = optionalEvento.get();

            // Crear respuesta con datos limpios del evento
            Map<String, Object> eventoData = new HashMap<>();
            eventoData.put("idEvento", evento.getIdEvento());
            eventoData.put("nombre", evento.getNombre());
            eventoData.put("fecha", evento.getFecha().toString());
            eventoData.put("hora", evento.getHora().toString());
            eventoData.put("lugar", evento.getLugar());
            eventoData.put("descripcion", evento.getDescripcion());

            if (evento.getOrganizador() != null) {
                Map<String, Object> organizadorData = new HashMap<>();
                organizadorData.put("idOrganizador", evento.getOrganizador().getId());
                organizadorData.put("nombreOrganizador", evento.getOrganizador().getNombre());
                eventoData.put("organizador", organizadorData);
            } else {
                eventoData.put("organizador", null);
            }

            response.put("success", true);
            response.put("evento", eventoData);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener evento: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Ver mis inscripciones (USER)
    @GetMapping("/mis-eventos")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Evento>> misEventosInscritos(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Evento> eventosInscritos = eventoRepository.findEventosByUsuario(user);
        return ResponseEntity.ok(eventosInscritos);
    }
}