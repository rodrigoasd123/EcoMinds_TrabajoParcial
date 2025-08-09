package com.upc.products.serviceimpl;

import com.upc.products.entities.Cliente;
import com.upc.products.repositories.ClienteRepositorio;
import com.upc.products.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public Cliente insertar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente editar(Cliente cliente) {
        if (clienteRepositorio.findById(cliente.getClienteId()).isPresent()) {
            return clienteRepositorio.save(cliente);
        }
        return null;
    }

    @Override
    public void eliminar(long id) {
        if(clienteRepositorio.existsById(id)){
            clienteRepositorio.deleteById(id);
        }
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente buscarPorId(long id) {
        if(clienteRepositorio.findById(id).isPresent()){
            return clienteRepositorio.findById(id).get();
        }
        return null;
    }
}
