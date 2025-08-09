package com.upc.products.serviceimpl;

import com.upc.products.dtos.ProveedorDTO;
import com.upc.products.entities.Proveedor;
import com.upc.products.repositories.ProveedorRepositorio;
import com.upc.products.services.ProveedorService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public ProveedorDTO insertar(ProveedorDTO proveedorDTO) {
        if (proveedorDTO.getIdProveedor() != null && proveedorRepositorio.existsById(proveedorDTO.getIdProveedor())) {
            throw new RuntimeException("El Proveedor con ID " + proveedorDTO.getIdProveedor() + " ya existe.");
        }
        // Convertir el DTO a la entidad Proveedor
        Proveedor proveedor = modelMapper.map(proveedorDTO, Proveedor.class);
        // Guardar la entidad Proveedor en la base de datos
        proveedor = proveedorRepositorio.save(proveedor);
        return modelMapper.map(proveedor, ProveedorDTO.class);
    }

    @Transactional
    @Override
    public ProveedorDTO editar(ProveedorDTO proveedorDTO) {
        return proveedorRepositorio.findById(proveedorDTO.getIdProveedor())
                .map(existing -> {
                    Proveedor proveedor = modelMapper.map(proveedorDTO, Proveedor.class);
                        return modelMapper.map(proveedorRepositorio.save(proveedor), ProveedorDTO.class);
                })
                .orElseThrow(() -> new RuntimeException(String.format("Proveedor con ID %d no encontrado", proveedorDTO.getIdProveedor())));
    }

    @Override
    @Transactional
    public void eliminar(long id) {
       if (!proveedorRepositorio.existsById(id)) {
               throw new RuntimeException("Proveedor no encontrado con ID: " + id);
           }
           proveedorRepositorio.deleteById(id);
       }

    @Override
    public List<ProveedorDTO> listar() {
        return proveedorRepositorio.findAll()
                .stream()
                .map(proveedor -> modelMapper.map(proveedor, ProveedorDTO.class))
                .toList();
    }

    @Override
    public ProveedorDTO buscarPorId(long id) {
       return proveedorRepositorio.findById(id)
               .map(proveedor -> modelMapper.map(proveedor, ProveedorDTO.class))
               .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }
}
