package com.upc.products.services;

import com.upc.products.entities.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClienteService {
    public Cliente insertar(Cliente cliente);
    public Cliente editar(Cliente cliente);
    public void eliminar(long id);
    public List<Cliente> listar();
    public Cliente buscarPorId(long id);
}
