package com.upc.products.security.dtos;

public class RegistroRequestDTO {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String correo;

    // Constructores
    public RegistroRequestDTO() {}

    public RegistroRequestDTO(String username, String password, String nombre, String apellido, String correo) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}