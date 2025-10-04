package com.upc.products.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "organizador")
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizador")
    private Long idOrganizador;

    @Column(nullable = false)
    private String nombre;

    // Constructores
    public Organizador() {}

    public Organizador(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getIdOrganizador() {
        return idOrganizador;
    }

    public void setIdOrganizador(Long idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}