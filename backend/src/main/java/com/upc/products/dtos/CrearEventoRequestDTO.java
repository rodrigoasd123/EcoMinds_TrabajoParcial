package com.upc.products.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearEventoRequestDTO {
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String lugar;
    private String descripcion;
    private Long organizadorId;
    
    // Constructores
    public CrearEventoRequestDTO() {}
    
    public CrearEventoRequestDTO(String nombre, LocalDate fecha, LocalTime hora, String lugar, String descripcion, Long organizadorId) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.organizadorId = organizadorId;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public LocalTime getHora() {
        return hora;
    }
    
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    
    public String getLugar() {
        return lugar;
    }
    
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Long getOrganizadorId() {
        return organizadorId;
    }
    
    public void setOrganizadorId(Long organizadorId) {
        this.organizadorId = organizadorId;
    }
}