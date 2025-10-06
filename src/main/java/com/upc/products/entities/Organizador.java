package com.upc.products.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "organizador")
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizador")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // Constructores
    public Organizador() {}

    public Organizador(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}