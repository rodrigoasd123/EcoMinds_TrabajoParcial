package com.upc.products.dtos;

public class RecompensaDTO {
    private Long idRecompensa;
    private String descripcion;
    private String requisito;

    // Constructores
    public RecompensaDTO() {}

    public RecompensaDTO(Long idRecompensa, String descripcion, String requisito) {
        this.idRecompensa = idRecompensa;
        this.descripcion = descripcion;
        this.requisito = requisito;
    }

    // Getters y Setters
    public Long getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(Long idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }
}