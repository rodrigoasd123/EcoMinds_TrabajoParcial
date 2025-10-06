package com.upc.products.services;

import com.upc.products.entities.PuntoAcopio;
import java.util.List;

public interface PuntoAcopioService {
    List<PuntoAcopio> listarTodos();
    PuntoAcopio buscarPorId(Long id);
    PuntoAcopio insertar(PuntoAcopio puntoAcopio);
    PuntoAcopio editar(PuntoAcopio puntoAcopio);
    void eliminar(Long id);
}