package com.upc.products.dtos;

public class CrearRecoleccionRequestDTO {
    
    private Long idMaterial;
    private Long idPunto;
    private Double peso;
    
    // Constructores
    public CrearRecoleccionRequestDTO() {}
    
    public CrearRecoleccionRequestDTO(Long idMaterial, Long idPunto, Double peso) {
        this.idMaterial = idMaterial;
        this.idPunto = idPunto;
        this.peso = peso;
    }
    
    // Getters y Setters
    public Long getIdMaterial() {
        return idMaterial;
    }
    
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    public Long getIdPunto() {
        return idPunto;
    }
    
    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }
    
    public Double getPeso() {
        return peso;
    }
    
    public void setPeso(Double peso) {
        this.peso = peso;
    }
}