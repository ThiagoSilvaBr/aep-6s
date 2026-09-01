package com.aep.project.mapper;

import com.aep.project.dto.EspecieRequest;
import com.aep.project.dto.EspecieResponse;
import com.aep.project.model.Bioma;
import com.aep.project.model.Especie;
import com.aep.project.model.Grupo;
import com.aep.project.model.NivelRisco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EspecieMapperTest {

    @Test
    @DisplayName("Deve converter EspecieRequest para Especie")
    public void converterRequestParaEntidade() {

        EspecieRequest request = new EspecieRequest(
                "Ararinha-Azul",
                "Cyanopsitta spixii",
                Grupo.AVE,
                Bioma.AMAZONIA,
                NivelRisco.CRITICO,
                200
        );

        Especie especie = EspecieMapper.paraEntidade(request);

        assertEquals("Ararinha-Azul", especie.getNomePopular());
        assertEquals("Cyanopsitta spixii", especie.getNomeCientifico());
        assertEquals(Grupo.AVE, especie.getGrupo());
        assertEquals(Bioma.AMAZONIA, especie.getBioma());
        assertEquals(NivelRisco.CRITICO, especie.getNivelRisco());
        assertEquals(200, especie.getPopulacaoEstimada());
    }

    @Test
    @DisplayName("Deve converter Especie para EspecieResponse")
    public void converterEntidadeParaResponse() {

        Especie especie = new Especie();

        especie.setId("2");
        especie.setNomePopular("Pirarucu");
        especie.setNomeCientifico("Arapaima gigas");
        especie.setGrupo(Grupo.PEIXE);
        especie.setBioma(Bioma.AMAZONIA);
        especie.setNivelRisco(NivelRisco.MODERADO);
        especie.setPopulacaoEstimada(80000);

        EspecieResponse response = EspecieMapper.paraResposta(especie);

        assertEquals("2", response.id());
        assertEquals("Pirarucu", response.nomePopular());
        assertEquals("Arapaima gigas", response.nomeCientifico());
        assertEquals(Grupo.PEIXE, response.grupo());
        assertEquals(Bioma.AMAZONIA, response.bioma());
        assertEquals(NivelRisco.MODERADO, response.nivelRisco());
        assertEquals(80000, response.populacaoEstimada());
    }

}
