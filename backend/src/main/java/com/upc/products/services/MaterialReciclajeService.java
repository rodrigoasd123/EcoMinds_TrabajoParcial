package com.upc.products.services;

import com.upc.products.entities.MaterialReciclaje;
import java.util.List;

public interface MaterialReciclajeService {
    List<MaterialReciclaje> listarTodos();
    MaterialReciclaje buscarPorId(Long id);
    MaterialReciclaje insertar(MaterialReciclaje material);
    MaterialReciclaje editar(MaterialReciclaje material);
    void eliminar(Long id);
}