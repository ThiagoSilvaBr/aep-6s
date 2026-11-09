package com.aep.project.mapper;

import com.aep.project.dto.ObservacaoRequest;
import com.aep.project.dto.ObservacaoResponse;
import com.aep.project.model.Observacao;

public class ObservacaoMapper {

    public static Observacao paraEntidade(ObservacaoRequest observacaoRequest) {
        Observacao observacao = new Observacao();

        observacao.setEspecieId(observacaoRequest.especieId());
        observacao.setLocalizacao(observacaoRequest.localizacao());
        observacao.setDetalhes(observacaoRequest.detalhes());
        observacao.setRegistros(observacaoRequest.registros());

        return observacao;
    }

    public static ObservacaoResponse paraResposta(Observacao observacao) {
        return new ObservacaoResponse(
                observacao.getId(),
                observacao.getEspecieId(),
                observacao.getDataObservacao(),
                observacao.getLocalizacao(),
                observacao.getDetalhes(),
                observacao.getRegistros()
        );
    }

}
