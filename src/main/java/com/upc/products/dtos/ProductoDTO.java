package com.upc.products.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO   {
    private  Long id;
    private  String descripcion;
    private  double precio;
    private  int stock;
    private  TipoProductoDTO tipoProducto;
}