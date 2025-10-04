package com.upc.products.security.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = true)
    private Double peso_reciclado;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer puntos = 0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Constructor para facilitar el registro
    public User(String username, String password, String nombre, String apellido, String correo) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.peso_reciclado = 0.0;
        this.puntos = 0;
        this.roles = new HashSet<>();
    }

    // Métodos adicionales para los nuevos campos
    public Double getPeso_reciclado() {
        return peso_reciclado;
    }

    public void setPeso_reciclado(Double peso_reciclado) {
        this.peso_reciclado = peso_reciclado;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
}
