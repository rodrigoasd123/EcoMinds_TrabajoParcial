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
    private String descripcion;

    @Column(nullable = false)
    private String requisito;

    // Constructores
    public Recompensa() {}

    public Recompensa(String descripcion, String requisito) {
        this.descripcion = descripcion;
        this.requisito = requisito;
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
}