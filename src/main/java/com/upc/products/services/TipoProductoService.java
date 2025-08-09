package com.upc.products.services;


import com.upc.products.dtos.TipoProductoDTO;
import com.upc.products.entities.TipoProducto;

import java.util.List;

public interface TipoProductoService {
    public TipoProductoDTO insertarTipoProducto(TipoProductoDTO tipoProductoDTO);
    public void eliminarTipoProducto(Long id);
    public TipoProductoDTO modificarTipoProducto(TipoProductoDTO tipoProductoDTO);
    public List<TipoProductoDTO> listarTipoProductos();
    public TipoProductoDTO buscarTipoProductoPorId(long id);
}
