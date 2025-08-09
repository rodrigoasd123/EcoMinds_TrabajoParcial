package com.upc.products.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.upc.products.entities.Orden;
import com.upc.products.entities.Producto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrdenDTO {
    private Producto producto;
    private int cantidad;
}