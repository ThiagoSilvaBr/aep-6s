package com.aep.project.model;

import jakarta.validation.constraints.NotBlank;

public class Localizacao {
    @NotBlank(message = "O município é obrigatório")
    private String municipio;
    @NotBlank(message = "O estado é obrigatório")
    private String estado;

    public Localizacao() {}

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}