package com.upc.products.controllers;

import com.upc.products.dtos.ProveedorDTO;
import com.upc.products.entities.Proveedor;
import com.upc.products.services.ProveedorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/*
 Exposición controlada: Solo expón las cabeceras estrictamente necesarias,
 como Authorization, para evitar problemas de seguridad.
 Soporte para withCredentials: Si estás trabajando con cookies o tokens,
 asegúrate de incluir allowCredentials(true) tanto en el backend como en
 el cliente (withCredentials: true).
  Pruebas locales y en producción: Verifica que las cabeceras estén
  disponibles en todos los navegadores que vayas a soportar, ya que
  algunos pueden manejar CORS de manera diferente.
 */
@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores") //EndPoint
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProveedorDTO>> listaProveedores() {
       return ResponseEntity.ok(proveedorService.listar());
    }

    @PostMapping("/proveedor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProveedorDTO> adicionaProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO proveedor = proveedorService.insertar(proveedorDTO);
        return ResponseEntity.ok(proveedor);
    }

    @PutMapping("/proveedor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProveedorDTO>  editarCliente(@RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO proveedor = proveedorService.editar(proveedorDTO);
        return ResponseEntity.ok(proveedorDTO);
    }

    @DeleteMapping("/proveedor/{id}")
    @PreAuthorize("hasRole('USER')")
    public void eliminarProveedor(@PathVariable int id) {
        proveedorService.eliminar(id);
    }

    @GetMapping("/proveedor/{id}")
    public ResponseEntity<ProveedorDTO> buscaProveedor(@PathVariable int id) {
        ProveedorDTO proveedorDTO = proveedorService.buscarPorId(id);
        return ResponseEntity.ok(proveedorDTO);
    }
}
