package com.upc.products.controllers;

import com.upc.products.dtos.CrearOrdenRequestDTO;
import com.upc.products.dtos.OrdenDTO;
import com.upc.products.entities.Orden;
import com.upc.products.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/orden")
    public boolean crearOrden(@RequestBody CrearOrdenRequestDTO crearOrdenRequestDTO) {
        return orderService.crearOrden(crearOrdenRequestDTO);
    }
    @GetMapping("/ordenes")
    public List<OrdenDTO> obtenerTodasLasOrdenes() {
        List<Orden> ordenes = orderService.obtenerTodasLasOrdenes();
        return ordenes.stream()
                .map(orden -> modelMapper.map(orden, OrdenDTO.class))
                .collect(Collectors.toList());
    }
}
