package com.upc.products.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long clienteId;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private double salario;
}
