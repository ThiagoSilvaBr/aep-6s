package com.aep.project.dto;

import com.aep.project.model.Bioma;
import com.aep.project.model.Grupo;
import com.aep.project.model.NivelRisco;

public record EspecieResponse(
        String id,
        String nomePopular,
        String nomeCientifico,
        Grupo grupo,
        Bioma bioma,
        NivelRisco nivelRisco,
        Integer populacaoEstimada
) {
}
