package com.upc.products.dtos;

public class UsuarioDTO {
    
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String correo;
    private Double pesoReciclado;
    private Integer puntos;
    private String rol;
    
    // Constructores
    public UsuarioDTO() {}
    
    public UsuarioDTO(Long id, String username, String nombre, String apellido, 
                     String correo, Double pesoReciclado, Integer puntos, String rol) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.pesoReciclado = pesoReciclado;
        this.puntos = puntos;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    
    public Double getPesoReciclado() {
        return pesoReciclado;
    }
    
    public void setPesoReciclado(Double pesoReciclado) {
        this.pesoReciclado = pesoReciclado;
    }
    
    public Integer getPuntos() {
        return puntos;
    }
    
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
}