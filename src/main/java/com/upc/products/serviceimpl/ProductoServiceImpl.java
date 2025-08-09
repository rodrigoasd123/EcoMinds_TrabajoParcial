package com.upc.products.serviceimpl;

import com.upc.products.entities.Producto;
import com.upc.products.repositories.ProductoRespositorio;
import com.upc.products.services.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRespositorio productoRespositorio;

    @Override
    public Producto insertarProducto(Producto producto) {
         return productoRespositorio.save(producto);
    }

    @Transactional
    @Override
    public void eliminarProducto(Long id) {
        if(productoRespositorio.existsById(id)) {
            productoRespositorio.deleteById(id);
        }
    }

    @Transactional
    @Override
    public Producto modificarProducto(Producto producto) {
       if(productoRespositorio.findById(producto.getId()).isPresent()) {
           return productoRespositorio.save(producto);
       }
       return null;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRespositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(long id) {
        if(productoRespositorio.findById(id).isPresent()){
            return productoRespositorio.findById(id).get();
        }
        return null;
    }
}
