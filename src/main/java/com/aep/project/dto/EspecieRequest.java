package com.aep.project.dto;

import com.aep.project.model.Bioma;
import com.aep.project.model.Grupo;
import com.aep.project.model.NivelRisco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EspecieRequest(
         @NotBlank(message = "O nome popular é obrigatório.")
         String nomePopular,
         @NotBlank(message = "O nome científico é obrigatório.")
         String nomeCientifico,
         @NotNull(message = "O grupo é obrigatório.")
         Grupo grupo,
         @NotNull(message = "O bioma é obrigatório.")
         Bioma bioma,
         @NotNull(message = "O nível de risco é obrigatório.")
         NivelRisco nivelRisco,
         @NotNull(message = "A população estimada é obrigatória.")
         @Positive(message = "A população estimada deve ser maior que zero.")
         Integer populacaoEstimada
) {
}
