package com.upc.products.dtos;

import java.time.LocalDate;

public class RecoleccionDTO {
    
    private Long idRecoleccion;
    private LocalDate fecha;
    private Double peso;
    private Integer puntos;
    private Long idMaterial;
    private String nombreMaterial;
    private Long idPunto;
    private String nombrePunto;
    private Long idUsuario;
    private String nombreUsuario;
    
    // Constructores
    public RecoleccionDTO() {}
    
    public RecoleccionDTO(Long idRecoleccion, LocalDate fecha, Double peso, Integer puntos, 
                         Long idMaterial, String nombreMaterial, Long idPunto, String nombrePunto,
                         Long idUsuario, String nombreUsuario) {
        this.idRecoleccion = idRecoleccion;
        this.fecha = fecha;
        this.peso = peso;
        this.puntos = puntos;
        this.idMaterial = idMaterial;
        this.nombreMaterial = nombreMaterial;
        this.idPunto = idPunto;
        this.nombrePunto = nombrePunto;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
    
    // Getters y Setters
    public Long getIdRecoleccion() {
        return idRecoleccion;
    }
    
    public void setIdRecoleccion(Long idRecoleccion) {
        this.idRecoleccion = idRecoleccion;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public Double getPeso() {
        return peso;
    }
    
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
    public Integer getPuntos() {
        return puntos;
    }
    
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
    public Long getIdMaterial() {
        return idMaterial;
    }
    
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    public String getNombreMaterial() {
        return nombreMaterial;
    }
    
    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }
    
    public Long getIdPunto() {
        return idPunto;
    }
    
    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }
    
    public String getNombrePunto() {
        return nombrePunto;
    }
    
    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
