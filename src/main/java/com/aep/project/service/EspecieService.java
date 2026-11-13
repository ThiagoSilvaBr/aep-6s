package com.aep.project.service;

import com.aep.project.exception.EspecieNotFoundException;
import com.aep.project.model.Bioma;
import com.aep.project.model.Especie;
import com.aep.project.model.Grupo;
import com.aep.project.model.NivelRisco;
import com.aep.project.repository.EspecieRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class EspecieService {

    private EspecieRepository especieRepository;
    private MongoTemplate mongoTemplate;

    public EspecieService(EspecieRepository especieRepository,
                          MongoTemplate mongoTemplate) {
        this.especieRepository = especieRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Especie criar(Especie especie) {
        return especieRepository.save(especie);
    }

    public List<Especie> buscar(String nomePopular, Grupo grupo,
                                Bioma bioma, NivelRisco nivelRisco) {

        Query query = new Query();

        if(nomePopular != null && !nomePopular.isBlank()) {
            query.addCriteria(Criteria.where("nomePopular")
                    .regex(Pattern.quote(nomePopular.trim()), "i"));
        }

        if(grupo != null) {
            query.addCriteria(Criteria.where("grupo").is(grupo));
        }

        if(bioma != null) {
            query.addCriteria(Criteria.where("bioma").is(bioma));
        }

        if(nivelRisco != null) {
            query.addCriteria(Criteria.where("nivelRisco").is(nivelRisco));
        }

        return mongoTemplate.find(query, Especie.class);
    }

    public Especie buscarPorId(String id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new EspecieNotFoundException("Espécie não encontrada com o ID: " + id));
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
