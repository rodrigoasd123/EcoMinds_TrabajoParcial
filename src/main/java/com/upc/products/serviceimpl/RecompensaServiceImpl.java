package com.upc.products.serviceimpl;

import com.upc.products.entities.Recompensa;
import com.upc.products.repositories.RecompensaRepository;
import com.upc.products.services.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecompensaServiceImpl implements RecompensaService {
    
    @Autowired
    private RecompensaRepository recompensaRepository;
    
    @Override
    public List<Recompensa> listarTodos() {
        return recompensaRepository.findAll();
    }
    
    @Override
    public List<Recompensa> listarPorPuntosAsc() {
        return recompensaRepository.findAllByOrderByPuntosRequeridosAsc();
    }
    
    @Override
    public List<Recompensa> listarPorPuntosDesc() {
        return recompensaRepository.findAllByOrderByPuntosRequeridosDesc();
    }
    
    @Override
    public List<Recompensa> listarPorNombre() {
        return recompensaRepository.findAllByOrderByNombreAsc();
    }
    
    @Override
    public Recompensa buscarPorId(Long id) {
        return recompensaRepository.findById(id).orElse(null);
    }
    
    @Override
    public Recompensa insertar(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }
    
    @Override
    public Recompensa editar(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }
    
    @Override
    public void eliminar(Long id) {
        recompensaRepository.deleteById(id);
    }
}