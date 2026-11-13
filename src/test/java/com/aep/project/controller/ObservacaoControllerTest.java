package com.aep.project.controller;

import com.aep.project.model.Detalhes;
import com.aep.project.model.Localizacao;
import com.aep.project.model.Observacao;
import com.aep.project.model.Registro;
import com.aep.project.repository.EspecieRepository;
import com.aep.project.repository.ObservacaoRepository;
import com.aep.project.service.ObservacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ObservacaoController.class)
public class ObservacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
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
    @DisplayName("Deve criar uma observação em /POST")
    public void criarUmaObservacao() throws Exception{
        when(observacaoService.criar(any(Observacao.class)))
                .thenReturn(observacao);

        String json = """
        {
            "especieId": "1",
            "localizacao": {
                "municipio": "Maringá",
                "estado": "PR"
            },
            "detalhes": {
                "quantidadeObservada": 3,
                "descricao": "testando"
            },
            "registros": [
                {
                    "tipo": "foto",
                    "descricao": "testando"
                }
            ]
        }
        """;

        mockMvc.perform(post("/observacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(observacaoService).criar(any(Observacao.class));
    }

    @Test
    @DisplayName("Deve listar todas observações em /GET")
    public void listarTodasObservacoes() throws Exception{
        when(observacaoService.buscar()).thenReturn(List.of(observacao));

        mockMvc.perform(get("/observacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

}
