package com.upc.products.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "punto_acopio")
public class PuntoAcopio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acopio")
    private Long idAcopio;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ubicacion;

    // Constructores
    public PuntoAcopio() {}

    public PuntoAcopio(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public Long getIdAcopio() {
        return idAcopio;
    }

    public void setIdAcopio(Long idAcopio) {
        this.idAcopio = idAcopio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}