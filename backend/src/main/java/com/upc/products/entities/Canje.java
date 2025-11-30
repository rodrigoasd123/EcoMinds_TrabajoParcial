package com.upc.products.entities;

import jakarta.persistence.*;
import com.upc.products.security.entities.User;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "canje")
public class Canje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canje")
    private Long idCanje;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Integer costo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recompensa", nullable = false)
    private Recompensa recompensa;

    // Constructores
    public Canje() {}

    public Canje(LocalDate fecha, LocalTime hora, Integer costo, User usuario, Recompensa recompensa) {
        this.fecha = fecha;
        this.hora = hora;
        this.costo = costo;
        this.usuario = usuario;
        this.recompensa = recompensa;
    }

    // Getters y Setters
    public Long getIdCanje() {
        return idCanje;
    }

    public void setIdCanje(Long idCanje) {
        this.idCanje = idCanje;
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

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }
}