package com.upc.products.serviceimpl;

import com.upc.products.dtos.TipoProductoDTO;
import com.upc.products.entities.TipoProducto;
import com.upc.products.repositories.TipoProductoRepositorio;
import com.upc.products.services.TipoProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

    @Autowired
    private TipoProductoRepositorio tipoProductoRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoProductoDTO insertarTipoProducto(TipoProductoDTO tipoProductoDTO) {
        TipoProducto tipoProducto = modelMapper.map(tipoProductoDTO, TipoProducto.class);
        return modelMapper.map(tipoProductoRepositorio.save(tipoProducto), TipoProductoDTO.class);
    }

    @Override
    public void eliminarTipoProducto(Long id) {
       tipoProductoRepositorio.deleteById(id);
    }

    @Override
    public TipoProductoDTO modificarTipoProducto(TipoProductoDTO tipoProductoDTO) {
        return tipoProductoRepositorio.findById(tipoProductoDTO.getId())
                .map(existing -> {
                    TipoProducto tipoProducto = modelMapper.map(tipoProductoDTO, TipoProducto.class);
                        return modelMapper.map(tipoProductoRepositorio.save(tipoProducto), TipoProductoDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("TipoProducto con ID %d no encontrado"+ tipoProductoDTO.getId()));
    }

    @Override
    public List<TipoProductoDTO> listarTipoProductos() {
        return tipoProductoRepositorio.findAll()
                .stream()
                .map(tipoProducto -> modelMapper.map(tipoProducto, TipoProductoDTO.class))
                .toList();
    }

    @Override
    public TipoProductoDTO buscarTipoProductoPorId(long id) {
        return tipoProductoRepositorio.findById(id)
                .map(tipoProducto -> modelMapper.map(tipoProducto, TipoProductoDTO.class))
                .orElseThrow(() -> new RuntimeException("TipoProducto con ID " + id + " no encontrado"));
    }

}
