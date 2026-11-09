package com.aep.project.model;

public class Detalhes {

    private Integer quantidadeObservada;
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