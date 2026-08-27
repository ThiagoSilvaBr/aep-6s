package com.aep.project.service;

import com.aep.project.model.Especie;
import com.aep.project.repository.EspecieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecieService {

    private EspecieRepository especieRepository;

    public EspecieService(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    public Especie criar(Especie especie) {
        return especieRepository.save(especie);
    }

    public List<Especie> buscarTodas() {
        return especieRepository.findAll();
    }

    public Especie buscarPorId(String id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
    }

    public Especie atualizar(String id, Especie especieAtualizada) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));

        especie.setNomePopular(especieAtualizada.getNomePopular());
        especie.setNomeCientifico(especieAtualizada.getNomeCientifico());
        especie.setBioma(especieAtualizada.getBioma());
        especie.setGrupo(especieAtualizada.getGrupo());
        especie.setNivelRisco(especieAtualizada.getNivelRisco());
        especie.setPopulacaoEstimada(especieAtualizada.getPopulacaoEstimada());

        return especieRepository.save(especie);
    }

    public void deletar(String id) {
        if(!especieRepository.existsById(id)) {
            throw new RuntimeException("Espécie não encontrada");
        }

        especieRepository.deleteById(id);
    }
}
