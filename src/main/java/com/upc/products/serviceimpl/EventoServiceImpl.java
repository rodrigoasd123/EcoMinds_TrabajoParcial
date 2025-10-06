package com.upc.products.serviceimpl;

import com.upc.products.entities.Evento;
import com.upc.products.entities.UsuarioEvento;
import com.upc.products.entities.Organizador;
import com.upc.products.security.entities.User;
import com.upc.products.repositories.EventoRepository;
import com.upc.products.repositories.UsuarioEventoRepository;
import com.upc.products.repositories.OrganizadorRepositorio;
import com.upc.products.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {
    
    @Autowired
    private EventoRepository eventoRepository;
    
    @Autowired
    private UsuarioEventoRepository usuarioEventoRepository;
    
    @Autowired
    private OrganizadorRepositorio organizadorRepository;
    
    @Override
    public List<Evento> listarTodos() {
        return eventoRepository.findAllByOrderByFechaAscHoraAsc();
    }
    
    @Override
    public List<Evento> listarPorOrganizador(Long organizadorId) {
        Organizador organizador = organizadorRepository.findById(organizadorId).orElse(null);
        if (organizador != null) {
            return eventoRepository.findByOrganizador(organizador);
        }
        return List.of();
    }
    
    @Override
    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Evento crearEvento(Evento evento) {
        return eventoRepository.save(evento);
    }
    
    @Override
    public Evento editarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }
    
    @Override
    public void eliminarEvento(Long id) {
        // Solo eliminar el evento - las inscripciones se eliminarán por CASCADE
        eventoRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public boolean inscribirUsuario(User usuario, Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId).orElse(null);
        if (evento == null) {
            return false;
        }
        
        // Verificar si ya está inscrito
        if (usuarioEventoRepository.existsByUsuarioAndEvento(usuario, evento)) {
            return false;
        }
        
        UsuarioEvento usuarioEvento = new UsuarioEvento();
        usuarioEvento.setUsuario(usuario);
        usuarioEvento.setEvento(evento);
        
        usuarioEventoRepository.save(usuarioEvento);
        return true;
    }
    
    @Override
    public List<Evento> listarEventosInscritos(User usuario) {
        List<UsuarioEvento> inscripciones = usuarioEventoRepository.findByUsuario(usuario);
        return inscripciones.stream().map(UsuarioEvento::getEvento).toList();
    }
}