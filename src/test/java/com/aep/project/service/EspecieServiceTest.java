package com.aep.project.service;

import com.aep.project.model.Bioma;
import com.aep.project.model.Especie;
import com.aep.project.model.Grupo;
import com.aep.project.model.NivelRisco;
import com.aep.project.repository.EspecieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EspecieServiceTest {

    @Mock
    private EspecieRepository especieRepository;

    @InjectMocks
    private EspecieService especieService;

    @Test
    @DisplayName("Deve criar uma espécie")
    public void criarEspecie() {

        Especie especie = new Especie();

        especie.setNomePopular("Ararinha-Azul");
        especie.setNomeCientifico("Cyanopsitta spixii");
        especie.setGrupo(Grupo.AVE);
        especie.setBioma(Bioma.AMAZONIA);
        especie.setNivelRisco(NivelRisco.CRITICO);
        especie.setPopulacaoEstimada(200);

        when(especieRepository.save(especie)).thenReturn(especie);

        Especie especieCriada = especieService.criar(especie);

        assertEquals(especie, especieCriada);
        verify(especieRepository).save(especie);
    }

}
