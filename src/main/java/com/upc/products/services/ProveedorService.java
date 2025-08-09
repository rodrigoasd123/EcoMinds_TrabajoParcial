package com.upc.products.services;
import com.upc.products.dtos.ProveedorDTO;
import com.upc.products.entities.Proveedor;

import java.util.List;

public interface ProveedorService {
    public ProveedorDTO insertar(ProveedorDTO proveedorDTO);
    public ProveedorDTO editar(ProveedorDTO proveedorDTO);
    public void eliminar(long id);
    public List<ProveedorDTO> listar();
    public ProveedorDTO buscarPorId(long id);
}
