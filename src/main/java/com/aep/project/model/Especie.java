package com.aep.project.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "especies")
public class Especie {

    @Id
    private String id;

    @NotBlank
    private String nomePopular;

    @NotBlank
    private String nomeCientifico;

    @NotNull
    private Grupo grupo;

    @NotNull
    private Bioma bioma;

    @NotNull
    private NivelRisco nivelRisco;

    @NotNull
    @Min(0)
    private Integer populacaoEstimada;

    public Especie() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomePopular() {
        return nomePopular;
    }

    public void setNomePopular(String nomePopular) {
        this.nomePopular = nomePopular;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Bioma getBioma() {
        return bioma;
    }

    public void setBioma(Bioma bioma) {
        this.bioma = bioma;
    }

    public NivelRisco getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public Integer getPopulacaoEstimada() {
        return populacaoEstimada;
    }

    public void setPopulacaoEstimada(Integer populacaoEstimada) {
        this.populacaoEstimada = populacaoEstimada;
    }
}
