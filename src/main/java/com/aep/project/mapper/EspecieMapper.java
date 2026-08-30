package com.aep.project.mapper;

import com.aep.project.dto.EspecieRequest;
import com.aep.project.dto.EspecieResponse;
import com.aep.project.model.Especie;

public class EspecieMapper {

    public static Especie mapeandoParaEntidade(EspecieRequest especieRequest){
        Especie especie = new Especie();

        especie.setNomePopular(especieRequest.nomePopular());
        especie.setNomeCientifico(especieRequest.nomeCientifico());
        especie.setGrupo(especieRequest.grupo());
        especie.setBioma(especieRequest.bioma());
        especie.setNivelRisco(especieRequest.nivelRisco());
        especie.setPopulacaoEstimada(especieRequest.populacaoEstimada());

        return especie;
    }

    public static EspecieResponse mapeandoParaResposta(Especie especie){

        return new EspecieResponse(
                especie.getId(),
                especie.getNomePopular(),
                especie.getNomeCientifico(),
                especie.getGrupo(),
                especie.getBioma(),
                especie.getNivelRisco(),
                especie.getPopulacaoEstimada()
        );
    }
}
