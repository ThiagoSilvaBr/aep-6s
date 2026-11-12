package com.aep.project.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Detalhes {

    @NotNull(message = "A quantidade observada é obrigatória")
    @Min(value = 1, message = "A quantidade observada deve ser no mínimo 1")
    private Integer quantidadeObservada;
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    public Detalhes() {}

    public Integer getQuantidadeObservada() {
        return quantidadeObservada;
    }

    public void setQuantidadeObservada(Integer quantidadeObservada) {
        this.quantidadeObservada = quantidadeObservada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}