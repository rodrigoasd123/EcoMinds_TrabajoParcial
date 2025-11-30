package com.upc.products.entities;

import jakarta.persistence.*;
import com.upc.products.security.entities.User;

@Entity
@Table(name = "usuario_evento")
public class UsuarioEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    // Constructores
    public UsuarioEvento() {}

    public UsuarioEvento(User usuario, Evento evento) {
        this.usuario = usuario;
        this.evento = evento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    // Métodos de conveniencia para trabajar con IDs
    public Long getIdUsuario() {
        return this.usuario != null ? this.usuario.getId() : null;
    }

    public void setIdUsuario(Long idUsuario) {
        // Este método será usado por el controller, pero necesitamos manejarlo apropiadamente
        // Nota: En un caso real, se debería cargar el User completo desde la base de datos
    }

    public Long getIdEvento() {
        return this.evento != null ? this.evento.getIdEvento() : null;
    }

    public void setIdEvento(Long idEvento) {
        // Este método será usado por el controller, pero necesitamos manejarlo apropiadamente
        // Nota: En un caso real, se debería cargar el Evento completo desde la base de datos
    }
}