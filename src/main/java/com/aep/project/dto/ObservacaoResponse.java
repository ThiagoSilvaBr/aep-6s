package com.aep.project.dto;

import com.aep.project.model.Detalhes;
import com.aep.project.model.Localizacao;
import com.aep.project.model.Registro;

import java.time.LocalDateTime;
import java.util.List;

public record ObservacaoResponse(
    String id,
    String especieId,
    LocalDateTime dataObservacao,
    Localizacao localizacao,
    Detalhes detalhes,
    List<Registro> registros
) {
}