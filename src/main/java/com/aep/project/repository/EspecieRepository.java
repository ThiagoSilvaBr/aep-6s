package com.aep.project.repository;

import com.aep.project.model.Especie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EspecieRepository extends MongoRepository<Especie, String> {
    List<Especie> findByNomePopularContainingIgnoreCase(String nomePopular);
}
