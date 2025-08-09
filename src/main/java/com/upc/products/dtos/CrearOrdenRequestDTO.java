package com.upc.products.dtos;

import com.upc.products.entities.DetalleOrden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrearOrdenRequestDTO {

    private Long userID;
    private List<DetalleOrdenDTO> detalles;
}