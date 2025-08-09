package com.upc.products.controllers;

import com.upc.products.dtos.ProveedorDTO;
import com.upc.products.dtos.TipoProductoDTO;
import com.upc.products.entities.Proveedor;
import com.upc.products.entities.TipoProducto;
import com.upc.products.services.TipoProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class TipoProductoController {
    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping("/tipoProductos")
    public ResponseEntity<List<TipoProductoDTO>> listaTiposProductos() {
       return ResponseEntity.ok(tipoProductoService.listarTipoProductos());
    }

    @PostMapping("/tipoProducto")//add
    public ResponseEntity<TipoProductoDTO> adicionaTipo(@RequestBody TipoProductoDTO tipoProductoDTO) {
        TipoProductoDTO tipoProducto = tipoProductoService.insertarTipoProducto(tipoProductoDTO);
        return ResponseEntity.ok(tipoProductoDTO);
    }
}
