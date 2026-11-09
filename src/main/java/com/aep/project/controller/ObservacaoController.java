package com.aep.project.controller;

import com.aep.project.dto.ObservacaoRequest;
import com.aep.project.dto.ObservacaoResponse;
import com.aep.project.mapper.ObservacaoMapper;
import com.aep.project.model.Observacao;
import com.aep.project.service.EspecieService;
import com.aep.project.service.ObservacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/observacoes")
public class ObservacaoController {

    private ObservacaoService observacaoService;
    private EspecieService especieService;

    public ObservacaoController(ObservacaoService observacaoService) {
        this.observacaoService = observacaoService;
    }

    @PostMapping
    public ResponseEntity<ObservacaoResponse> criar(@Valid @RequestBody ObservacaoRequest observacaoRequest) {

        Observacao observacao = ObservacaoMapper.paraEntidade(observacaoRequest);
        observacao = observacaoService.criar(observacao);

        ObservacaoResponse observacaoResponse = ObservacaoMapper.paraResposta(observacao);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(observacao.getId())
                .toUri();

        return ResponseEntity.created(uri).body(observacaoResponse);
    }

    @GetMapping
    public ResponseEntity<List<ObservacaoResponse>> buscar() {
        List<ObservacaoResponse> observacoes = observacaoService.buscar()
                .stream()
                .map(ObservacaoMapper::paraResposta)
                .toList();

        return ResponseEntity.ok(observacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservacaoResponse> buscarPorId(@PathVariable String id) {
        Observacao observacao = observacaoService.buscarPorId(id);
        return ResponseEntity.ok(ObservacaoMapper.paraResposta(observacao));
    }

    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<ObservacaoResponse>> buscarPorEspecie(@PathVariable String especieId) {
        List<ObservacaoResponse> observacoes = observacaoService.buscarPorEspecie(especieId)
                .stream()
                .map(ObservacaoMapper::paraResposta)
                .toList();

        return ResponseEntity.ok(observacoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        observacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
