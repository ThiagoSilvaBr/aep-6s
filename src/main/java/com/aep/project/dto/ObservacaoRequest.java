package com.aep.project.dto;

import com.aep.project.model.Detalhes;
import com.aep.project.model.Localizacao;
import com.aep.project.model.Registro;

import java.util.List;

public record ObservacaoRequest(
        String especieId,
        Localizacao localizacao,
        Detalhes detalhes,
        List<Registro> registros
) {
}