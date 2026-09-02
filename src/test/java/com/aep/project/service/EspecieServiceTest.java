package com.aep.project.service;

import com.aep.project.model.Bioma;
import com.aep.project.model.Especie;
import com.aep.project.model.Grupo;
import com.aep.project.model.NivelRisco;
import com.aep.project.repository.EspecieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EspecieServiceTest {

    @Mock
    private EspecieRepository especieRepository;

    @InjectMocks
    private EspecieService especieService;

    private Especie especie;

    @BeforeEach
    public void configurarEspecie() {
        especie = new Especie();

        especie.setNomePopular("Ararinha-Azul");
        especie.setNomeCientifico("Cyanopsitta spixii");
        especie.setGrupo(Grupo.AVE);
        especie.setBioma(Bioma.AMAZONIA);
        especie.setNivelRisco(NivelRisco.CRITICO);
        especie.setPopulacaoEstimada(200);
    }

    @Test
    @DisplayName("Deve criar uma espécie")
    public void criarEspecie() {
        when(especieRepository.save(especie)).thenReturn(especie);

        Especie especieCriada = especieService.criar(especie);

        assertEquals(especie, especieCriada);
        verify(especieRepository).save(especie);
    }

    @Test
    @DisplayName("Deve buscar todas as espécies quando filtro não for aplicado")
    public void buscarTodasEspecies() {
        List<Especie> especies = List.of(especie);

        when(especieRepository.findAll()).thenReturn(especies);

        List<Especie> resultado = especieService.buscar(null);

        assertEquals(especies, resultado);
        verify(especieRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar espécies por nome popular")
    public void buscarPorNomePopular() {
        List<Especie> especies = List.of(especie);

        when(especieRepository.findByNomePopularContainingIgnoreCase("Ararinha"))
                .thenReturn(especies);

        List<Especie> resultado = especieService.buscar("Ararinha");

        assertEquals(especies, resultado);
        verify(especieRepository).findByNomePopularContainingIgnoreCase("Ararinha");
    }

    @Test
    @DisplayName("Deve buscar todas espécies quando nome estiver vazio")
    public void buscarComNomeVazio() {
        List<Especie> especies = List.of();

        when(especieRepository.findAll()).thenReturn(especies);

        List<Especie> resultado = especieService.buscar("");

        assertEquals(especies, resultado);
        verify(especieRepository).findAll();
    }

}
