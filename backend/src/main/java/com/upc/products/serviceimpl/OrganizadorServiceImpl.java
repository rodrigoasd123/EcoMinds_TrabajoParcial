package com.upc.products.serviceimpl;

import com.upc.products.entities.Organizador;
import com.upc.products.repositories.OrganizadorRepositorio;
import com.upc.products.services.OrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizadorServiceImpl implements OrganizadorService {
    
    @Autowired
    private OrganizadorRepositorio organizadorRepository;
    
    @Override
    public List<Organizador> listarTodos() {
        return organizadorRepository.findAll();
    }
    
    @Override
    public Organizador buscarPorId(Long id) {
        return organizadorRepository.findById(id).orElse(null);
    }
    
    @Override
    public Organizador buscarPorNombre(String nombre) {
        return organizadorRepository.findByNombre(nombre).orElse(null);
    }
    
    @Override
    public Organizador insertar(Organizador organizador) {
        return organizadorRepository.save(organizador);
    }
    
    @Override
    public Organizador editar(Organizador organizador) {
        return organizadorRepository.save(organizador);
    }
    
    @Override
    public void eliminar(Long id) {
        organizadorRepository.deleteById(id);
    }
}