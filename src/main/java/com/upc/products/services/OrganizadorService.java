package com.upc.products.services;

import com.upc.products.entities.Organizador;

import java.util.List;

public interface OrganizadorService {
    
    List<Organizador> listarTodos();
    
    Organizador buscarPorId(Long id);
    
    Organizador buscarPorNombre(String nombre);
    
    Organizador insertar(Organizador organizador);
    
    Organizador editar(Organizador organizador);
    
    void eliminar(Long id);
}