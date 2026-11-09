package com.aep.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "observacoes")
public class Observacao {

    @Id
    private String id;

    private String especieId;
    private LocalDateTime dataObservacao;
    private Localizacao localizacao;
    private Detalhes detalhes;
    private List<Registro> registros;

    public Observacao() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspecieId() {
        return especieId;
    }

    public void setEspecieId(String especieId) {
        this.especieId = especieId;
    }

    public LocalDateTime getDataObservacao() {
        return dataObservacao;
    }

    public void setDataObservacao(LocalDateTime dataObservacao) {
        this.dataObservacao = dataObservacao;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Detalhes getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(Detalhes detalhes) {
        this.detalhes = detalhes;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }
}
