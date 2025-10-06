package com.upc.products.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "recompensa")
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recompensa")
    private Long idRecompensa;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String requisito;
    
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer puntosRequeridos = 0;

    // Constructores
    public Recompensa() {}

    public Recompensa(String nombre, String descripcion, String requisito, Integer puntosRequeridos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisito = requisito;
        this.puntosRequeridos = puntosRequeridos;
    }

    // Getters y Setters
    public Long getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(Integer puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }
}