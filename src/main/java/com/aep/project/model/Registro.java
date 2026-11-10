package com.aep.project.model;

import jakarta.validation.constraints.NotBlank;

public class Registro {

    @NotBlank(message = "O tipo de registro deve ser obrigatório")
    private String tipo;
    @NotBlank(message = "A descrição do registro deve ser obrigatória")
    private String descricao;

    public Registro() {}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}