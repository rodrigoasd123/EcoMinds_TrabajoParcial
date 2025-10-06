package com.upc.products.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventoDTO {
    
    private Long idEvento;
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String lugar;
    private String descripcion;
    private Long idOrganizador;
    private String nombreOrganizador;
    private List<String> participantes;
    
    // Constructores
    public EventoDTO() {}
    
    public EventoDTO(Long idEvento, String nombre, LocalDate fecha, LocalTime hora, 
                    String lugar, String descripcion, Long idOrganizador, 
                    String nombreOrganizador, List<String> participantes) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.idOrganizador = idOrganizador;
        this.nombreOrganizador = nombreOrganizador;
        this.participantes = participantes;
    }
    
    // Getters y Setters
    public Long getIdEvento() {
        return idEvento;
    }
    
    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }
    
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
    
    public Long getIdOrganizador() {
        return idOrganizador;
    }
    
    public void setIdOrganizador(Long idOrganizador) {
        this.idOrganizador = idOrganizador;
    }
    
    public String getNombreOrganizador() {
        return nombreOrganizador;
    }
    
    public void setNombreOrganizador(String nombreOrganizador) {
        this.nombreOrganizador = nombreOrganizador;
    }
    
    public List<String> getParticipantes() {
        return participantes;
    }
    
    public void setParticipantes(List<String> participantes) {
        this.participantes = participantes;
    }
}