package com.aep.project.repository;

import com.aep.project.model.Observacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ObservacaoRepository extends MongoRepository<Observacao, String> {

    List<Observacao> findByEspecieId(String especieId);

    boolean existsByEspecieId(String especieId);

    void deleteByEspecieId(String especieId);
}
