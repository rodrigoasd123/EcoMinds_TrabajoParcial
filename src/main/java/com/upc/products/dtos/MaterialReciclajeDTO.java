package com.upc.products.dtos;

public class MaterialReciclajeDTO {
    private Long idMateriales;
    private String nombre;
    private String descripcion;

    // Constructores
    public MaterialReciclajeDTO() {}

    public MaterialReciclajeDTO(Long idMateriales, String nombre, String descripcion) {
        this.idMateriales = idMateriales;
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