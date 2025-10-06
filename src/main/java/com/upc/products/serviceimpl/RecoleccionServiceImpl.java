package com.upc.products.serviceimpl;

import com.upc.products.entities.Recoleccion;
import com.upc.products.entities.MaterialReciclaje;
import com.upc.products.entities.PuntoAcopio;
import com.upc.products.security.entities.User;
import com.upc.products.repositories.RecoleccionRepository;
import com.upc.products.repositories.MaterialReciclajeRepository;
import com.upc.products.repositories.PuntoAcopioRepository;
import com.upc.products.security.repositories.UserRepository;
import com.upc.products.services.RecoleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecoleccionServiceImpl implements RecoleccionService {
    
    @Autowired
    private RecoleccionRepository recoleccionRepository;
    
    @Autowired
    private MaterialReciclajeRepository materialReciclajeRepository;
    
    @Autowired
    private PuntoAcopioRepository puntoAcopioRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<Recoleccion> listarTodos() {
        return recoleccionRepository.findAll();
    }
    
    @Override
    public List<Recoleccion> listarPorUsuario(User usuario) {
        return recoleccionRepository.findByUsuario(usuario);
    }
    
    @Override
    @Transactional
    public Recoleccion registrarRecoleccion(User usuario, Long materialId, Long puntoAcopioId, Double peso) {
        MaterialReciclaje material = materialReciclajeRepository.findById(materialId).orElse(null);
        PuntoAcopio puntoAcopio = puntoAcopioRepository.findById(puntoAcopioId).orElse(null);
        
        if (material == null || puntoAcopio == null) {
            return null;
        }
        
        Recoleccion recoleccion = new Recoleccion();
        recoleccion.setUsuario(usuario);
        recoleccion.setMaterial(material);
        recoleccion.setPuntoAcopio(puntoAcopio);
        recoleccion.setPeso(peso);
        recoleccion.setFecha(LocalDate.now());
        
        // Calcular puntos (1 punto por cada kg)
        Integer puntosGanados = peso.intValue();
        
        // Actualizar peso reciclado y puntos del usuario
        Double pesoAnterior = usuario.getPeso_reciclado() != null ? usuario.getPeso_reciclado() : 0.0;
        Integer puntosAnteriores = usuario.getPuntos() != null ? usuario.getPuntos() : 0;
        
        usuario.setPeso_reciclado(pesoAnterior + peso);
        usuario.setPuntos(puntosAnteriores + puntosGanados);
        
        userRepository.save(usuario);
        
        return recoleccionRepository.save(recoleccion);
    }
    
    @Override
    public Recoleccion buscarPorId(Long id) {
        return recoleccionRepository.findById(id).orElse(null);
    }
}