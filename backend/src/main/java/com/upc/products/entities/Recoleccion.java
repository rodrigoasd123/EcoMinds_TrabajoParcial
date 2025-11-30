package com.upc.products.entities;

import jakarta.persistence.*;
import com.upc.products.security.entities.User;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "recoleccion")
public class Recoleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recoleccion")
    private Long idRecoleccion;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materiales", nullable = false)
    private MaterialReciclaje material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acopio", nullable = false)
    private PuntoAcopio puntoAcopio;

    // Constructores
    public Recoleccion() {}

    public Recoleccion(LocalDate fecha, LocalTime hora, Double peso, User usuario, MaterialReciclaje material, PuntoAcopio puntoAcopio) {
        this.fecha = fecha;
        this.hora = hora;
        this.peso = peso;
        this.usuario = usuario;
        this.material = material;
        this.puntoAcopio = puntoAcopio;
    }

    // Getters y Setters
    public Long getIdRecoleccion() {
        return idRecoleccion;
    }

    public void setIdRecoleccion(Long idRecoleccion) {
        this.idRecoleccion = idRecoleccion;
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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public MaterialReciclaje getMaterial() {
        return material;
    }

    public void setMaterial(MaterialReciclaje material) {
        this.material = material;
    }

    public PuntoAcopio getPuntoAcopio() {
        return puntoAcopio;
    }

    public void setPuntoAcopio(PuntoAcopio puntoAcopio) {
        this.puntoAcopio = puntoAcopio;
    }
}