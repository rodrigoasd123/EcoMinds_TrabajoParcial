package com.upc.products.dtos;

import com.upc.products.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDTO {
    private Long ordenId;
    private LocalDate fecha;
    //private Long clienteId;
    private Cliente cliente;
    private List<DetalleOrdenDTO> detalles;
}