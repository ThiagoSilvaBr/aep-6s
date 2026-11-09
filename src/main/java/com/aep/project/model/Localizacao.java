package com.aep.project.model;

public class Localizacao {

    private String municipio;
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