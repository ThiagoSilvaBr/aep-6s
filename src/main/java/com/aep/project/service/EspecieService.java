package com.aep.project.service;

import com.aep.project.exception.EspecieNotFoundException;
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

    public List<Especie> buscar(String nomePopular) {

        if(nomePopular != null && !nomePopular.isBlank()) {
            return especieRepository.findByNomePopularContainingIgnoreCase(nomePopular);
        }

        return especieRepository.findAll();
    }

    public Especie buscarPorId(String id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new EspecieNotFoundException("Espécie não econtrado com o ID: " + id));
    }

    public Especie atualizar(String id, Especie especieAtualizada) {
        Especie especie = buscarPorId(id);

        especie.setNomePopular(especieAtualizada.getNomePopular());
        especie.setNomeCientifico(especieAtualizada.getNomeCientifico());
        especie.setBioma(especieAtualizada.getBioma());
        especie.setGrupo(especieAtualizada.getGrupo());
        especie.setNivelRisco(especieAtualizada.getNivelRisco());
        especie.setPopulacaoEstimada(especieAtualizada.getPopulacaoEstimada());

        return especieRepository.save(especie);
    }

    public void deletar(String id){
        buscarPorId(id);
        especieRepository.deleteById(id);
    }
}
