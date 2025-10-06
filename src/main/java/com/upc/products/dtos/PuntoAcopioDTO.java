package com.upc.products.dtos;

import java.time.LocalDate;

public class PuntoAcopioDTO {
    
    private Long idPunto;
    private String nombre;
    private String ubicacion;
    
    // Constructores
    public PuntoAcopioDTO() {}
    
    public PuntoAcopioDTO(Long idPunto, String nombre, String ubicacion) {
        this.idPunto = idPunto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }
    
    // Getters y Setters
    public Long getIdPunto() {
        return idPunto;
    }
    
    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
