package com.upc.products.dtos;

public class CrearCanjeRequestDTO {
    
    private Long idRecompensa;
    
    // Constructores
    public CrearCanjeRequestDTO() {}
    
    public CrearCanjeRequestDTO(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }
    
    // Getters y Setters
    public Long getIdRecompensa() {
        return idRecompensa;
    }
    
    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }
}