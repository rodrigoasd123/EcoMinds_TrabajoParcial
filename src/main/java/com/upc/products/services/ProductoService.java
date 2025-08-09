package com.upc.products.services;

import com.upc.products.entities.Producto;

import java.util.List;

public interface ProductoService {
    public Producto insertarProducto(Producto producto);
    public void eliminarProducto(Long id);
    public Producto modificarProducto(Producto producto);
    public List<Producto> listarProductos();
    public Producto buscarProductoPorId(long id);
}
