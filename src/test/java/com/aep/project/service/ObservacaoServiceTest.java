package com.aep.project.service;

import com.aep.project.exception.EspecieNotFoundException;
import com.aep.project.exception.ObservacaoNotFoundException;
import com.aep.project.model.Detalhes;
import com.aep.project.model.Localizacao;
import com.aep.project.model.Observacao;
import com.aep.project.model.Registro;
import com.aep.project.repository.EspecieRepository;
import com.aep.project.repository.ObservacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObservacaoServiceTest {

    @Mock
    private ObservacaoRepository observacaoRepository;

    @Mock
    private EspecieRepository especieRepository;

    @InjectMocks
    private ObservacaoService observacaoService;

    private Observacao observacao;

    @BeforeEach
    public void configurarObservacao(){
        Localizacao localizacao = new Localizacao();
        localizacao.setMunicipio("Maringá");
        localizacao.setEstado("PR");

        Detalhes detalhes = new Detalhes();
        detalhes.setQuantidadeObservada(5);
        detalhes.setDescricao("Uma ninhada de araras foram vistas sozinhas no ninho.");

        Registro registro = new Registro();
        registro.setTipo("Fotografia");
        registro.setDescricao("Registro fotográfico da ninhada.");

        observacao = new Observacao();
        observacao.setId("1");
        observacao.setEspecieId("5");
        observacao.setLocalizacao(localizacao);
        observacao.setDetalhes(detalhes);
        observacao.setRegistros(List.of(registro));
    }

    @Test
    @DisplayName("Deve criar uma observação")
    public void criarObservacao(){
        when(especieRepository.existsById("5")).thenReturn(true);
        when(observacaoRepository.save(observacao)).thenReturn(observacao);

        Observacao observacaoCriada = observacaoService.criar(observacao);

        assertEquals(observacao, observacaoCriada);
        assertNotNull(observacaoCriada.getDataObservacao());

        verify(especieRepository).existsById("5");
        verify(observacaoRepository).save(observacao);
    }

    @Test
    @DisplayName("Deve buscar todas as observações quando não especificado uma espécie")
    public void buscarTodasObservacoes(){
        List<Observacao> observacoes = List.of(observacao);

        when(observacaoRepository.findAll()).thenReturn(observacoes);

        List<Observacao> resultado = observacaoService.buscar();

        assertEquals(observacoes, resultado);
        verify(observacaoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar observação por especieId")
    public void buscarObservacoesPorEspecieId(){
        List<Observacao> observacoes = List.of(observacao);

        when(especieRepository.existsById("5")).thenReturn(true);
        when(observacaoRepository.findByEspecieId("5")).thenReturn(observacoes);

        List<Observacao> resultado = observacaoService.buscarPorEspecie("5");

        assertEquals(observacoes, resultado);
        verify(observacaoRepository).findByEspecieId("5");
    }

    @Test
    @DisplayName("Deve lançar uma exception quando especieId não existir")
    public void buscarObservacoesComEspecieIdVazio(){
        when(especieRepository.existsById("")).thenReturn(false);

        assertThrows(
                EspecieNotFoundException.class,
                () -> observacaoService.buscarPorEspecie("")
        );

        verify(especieRepository).existsById("");
        verify(observacaoRepository, never()).findAll();
        verify(observacaoRepository, never()).findByEspecieId("");
    }

    @Test
    @DisplayName("Deve deletar uma observação por Id")
    public void deletarObservacoesPorId(){
        observacao.setId("1");

        when(observacaoRepository.findById("1"))
                .thenReturn(Optional.of(observacao));

        observacaoService.deletar("1");

        verify(observacaoRepository).findById("1");
        verify(observacaoRepository).deleteById("1");
    }

}
