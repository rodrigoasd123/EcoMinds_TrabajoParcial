package com.upc.products.serviceimpl;

import com.upc.products.entities.Canje;
import com.upc.products.entities.Recompensa;
import com.upc.products.security.entities.User;
import com.upc.products.repositories.CanjeRepository;
import com.upc.products.repositories.RecompensaRepository;
import com.upc.products.security.repositories.UserRepository;
import com.upc.products.services.CanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CanjeServiceImpl implements CanjeService {
    
    @Autowired
    private CanjeRepository canjeRepository;
    
    @Autowired
    private RecompensaRepository recompensaRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Canje> listarTodos() {
        return canjeRepository.findAllByOrderByFechaDesc();
    }
    
    @Override
    public List<Canje> listarPorUsuario(User usuario) {
        return canjeRepository.findByUsuarioOrderByFechaDesc(usuario);
    }
    
    @Override
    @Transactional
    public Canje canjearRecompensa(User usuario, Long recompensaId) {
        Recompensa recompensa = recompensaRepository.findById(recompensaId).orElse(null);
        
        if (recompensa == null) {
            return null;
        }
        
        // Verificar si el usuario tiene suficientes puntos
        Integer puntosUsuario = usuario.getPuntos() != null ? usuario.getPuntos() : 0;
        if (puntosUsuario < recompensa.getPuntosRequeridos()) {
            return null; // No tiene suficientes puntos
        }
        
        // Crear el canje
        Canje canje = new Canje();
        canje.setUsuario(usuario);
        canje.setRecompensa(recompensa);
        canje.setFecha(LocalDate.now());
        canje.setCosto(recompensa.getPuntosRequeridos());
        
        // Descontar puntos del usuario
        usuario.setPuntos(puntosUsuario - recompensa.getPuntosRequeridos());
        userRepository.save(usuario);
        
        return canjeRepository.save(canje);
    }
    
    @Override
    public Canje buscarPorId(Long id) {
        return canjeRepository.findById(id).orElse(null);
    }
    
    @Override
    public long contarCanjesPorUsuario(User usuario) {
        return canjeRepository.countByUsuario(usuario);
    }
}