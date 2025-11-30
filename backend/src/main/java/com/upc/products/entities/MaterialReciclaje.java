package com.upc.products.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "material_reciclaje")
public class MaterialReciclaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materiales")
    private Long idMateriales;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = true)
    private String descripcion;

    // Constructores
    public MaterialReciclaje() {}

    public MaterialReciclaje(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getIdMateriales() {
        return idMateriales;
    }

    public void setIdMateriales(Long idMateriales) {
        this.idMateriales = idMateriales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}