package com.aep.project.dto;

import com.aep.project.model.Detalhes;
import com.aep.project.model.Localizacao;
import com.aep.project.model.Registro;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ObservacaoRequest(
        @NotBlank(message = "O Id da espécie é obrigatório")
        String especieId,

        @NotNull(message = "A localização é obrigatória")
        @Valid
        Localizacao localizacao,

        @NotNull(message = "Os detalhes devem ser obrigatórios")
        @Valid
        Detalhes detalhes,

        List<@Valid Registro> registros
) {
}