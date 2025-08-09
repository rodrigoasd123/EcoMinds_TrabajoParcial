package com.upc.products.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {
    private Long idProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private LocalDate fechaInscripcion;
}
