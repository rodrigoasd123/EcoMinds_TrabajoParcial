package com.upc.products.serviceimpl;

import com.upc.products.entities.PuntoAcopio;
import com.upc.products.repositories.PuntoAcopioRepository;
import com.upc.products.services.PuntoAcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PuntoAcopioServiceImpl implements PuntoAcopioService {
    
    @Autowired
    private PuntoAcopioRepository puntoAcopioRepository;
    
    @Override
    public List<PuntoAcopio> listarTodos() {
        return puntoAcopioRepository.findAll();
    }
    
    @Override
    public PuntoAcopio buscarPorId(Long id) {
        return puntoAcopioRepository.findById(id).orElse(null);
    }
    
    @Override
    public PuntoAcopio insertar(PuntoAcopio puntoAcopio) {
        return puntoAcopioRepository.save(puntoAcopio);
    }
    
    @Override
    public PuntoAcopio editar(PuntoAcopio puntoAcopio) {
        return puntoAcopioRepository.save(puntoAcopio);
    }
    
    @Override
    public void eliminar(Long id) {
        puntoAcopioRepository.deleteById(id);
    }
}