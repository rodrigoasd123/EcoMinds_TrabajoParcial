package com.upc.products.services;

import com.upc.products.dtos.CrearOrdenRequestDTO;
import com.upc.products.dtos.OrdenDTO;
import com.upc.products.entities.Orden;

import java.util.List;

public interface OrderService {
    public boolean crearOrden(CrearOrdenRequestDTO requestDTO);
    public List<Orden> obtenerTodasLasOrdenes();
    public Orden obtenerOrdenPorId(Long ordenId);
}
