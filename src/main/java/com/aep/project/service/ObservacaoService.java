package com.aep.project.service;

import com.aep.project.exception.EspecieNotFoundException;
import com.aep.project.exception.ObservacaoNotFoundException;
import com.aep.project.model.Especie;
import com.aep.project.model.Observacao;
import com.aep.project.repository.EspecieRepository;
import com.aep.project.repository.ObservacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ObservacaoService {

    private ObservacaoRepository observacaoRepository;
    private EspecieRepository especieRepository;

    public ObservacaoService(ObservacaoRepository observacaoRepository,
                             EspecieRepository especieRepository) {
        this.observacaoRepository = observacaoRepository;
        this.especieRepository = especieRepository;
    }

    public Observacao criar(Observacao observacao) {

        if(!observacaoRepository.existsByEspecieId(observacao.getEspecieId())) {
            throw new EspecieNotFoundException("Espécie não encontrada com o ID: " + observacao.getEspecieId())
        }

        observacao.setDataObservacao(LocalDateTime.now());
        return observacaoRepository.save(observacao);
    }

    public List<Observacao> buscar() {
        return observacaoRepository.findAll();
    }

    public Observacao buscarPorId(String id) {
        return observacaoRepository.findById(id)
                .orElseThrow(() -> new ObservacaoNotFoundException("Observação não encontrada com o ID: " + id));
    }

    public List<Observacao> buscarPorEspecie(String especieId) {
        if(!especieRepository.existsById(especieId)) {
            throw new EspecieNotFoundException("Espécie não encontrada com o ID: " + especieId);
        }
        return observacaoRepository.findByEspecieId(especieId);
    }

    public void deletar(String id) {
        buscarPorId(id);
        observacaoRepository.deleteById(id);
    }

}
