package com.upc.products.dtos;

import java.time.LocalDate;

public class CanjeDTO {
    
    private Long idCanje;
    private LocalDate fecha;
    private Integer costo;
    private Long idRecompensa;
    private String nombreRecompensa;
    private Long idUsuario;
    private String nombreUsuario;
    
    // Constructores
    public CanjeDTO() {}
    
    public CanjeDTO(Long idCanje, LocalDate fecha, Integer costo, 
                   Long idRecompensa, String nombreRecompensa, 
                   Long idUsuario, String nombreUsuario) {
        this.idCanje = idCanje;
        this.fecha = fecha;
        this.costo = costo;
        this.idRecompensa = idRecompensa;
        this.nombreRecompensa = nombreRecompensa;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
    
    // Getters y Setters
    public Long getIdCanje() {
        return idCanje;
    }
    
    public void setIdCanje(Long idCanje) {
        this.idCanje = idCanje;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public Integer getCosto() {
        return costo;
    }
    
    public void setCosto(Integer costo) {
        this.costo = costo;
    }
    
    public Long getIdRecompensa() {
        return idRecompensa;
    }
    
    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }
    
    public String getNombreRecompensa() {
        return nombreRecompensa;
    }
    
    public void setNombreRecompensa(String nombreRecompensa) {
        this.nombreRecompensa = nombreRecompensa;
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