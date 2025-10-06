package com.upc.products.serviceimpl;

import com.upc.products.entities.MaterialReciclaje;
import com.upc.products.repositories.MaterialReciclajeRepository;
import com.upc.products.services.MaterialReciclajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialReciclajeServiceImpl implements MaterialReciclajeService {
    
    @Autowired
    private MaterialReciclajeRepository materialReciclajeRepository;
    
    @Override
    public List<MaterialReciclaje> listarTodos() {
        return materialReciclajeRepository.findAll();
    }
    
    @Override
    public MaterialReciclaje buscarPorId(Long id) {
        return materialReciclajeRepository.findById(id).orElse(null);
    }
    
    @Override
    public MaterialReciclaje insertar(MaterialReciclaje material) {
        return materialReciclajeRepository.save(material);
    }
    
    @Override
    public MaterialReciclaje editar(MaterialReciclaje material) {
        return materialReciclajeRepository.save(material);
    }
    
    @Override
    public void eliminar(Long id) {
        materialReciclajeRepository.deleteById(id);
    }
}