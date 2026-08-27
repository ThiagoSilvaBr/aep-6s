package com.aep.project.repository;

import com.aep.project.model.Especie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EspecieRepository extends MongoRepository<Especie, String> {
}
