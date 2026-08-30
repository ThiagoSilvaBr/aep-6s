package com.aep.project.controller;

import com.aep.project.dto.EspecieRequest;
import com.aep.project.dto.EspecieResponse;
import com.aep.project.mapper.EspecieMapper;
import com.aep.project.model.Especie;
import com.aep.project.service.EspecieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    private EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @PostMapping
    public ResponseEntity<EspecieResponse> criar(@Valid @RequestBody EspecieRequest especieRequest) {

        Especie especie = EspecieMapper.paraEntidade(especieRequest);
        especie = especieService.criar(especie);

        EspecieResponse especieResponse = EspecieMapper.paraResposta(especie);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(especie.getId())
                .toUri();

        return ResponseEntity.created(uri).body(especieResponse);
    }

    @GetMapping
    public ResponseEntity<List<EspecieResponse>> buscar(
            @RequestParam(required = false) String nomePopular) {

        List<EspecieResponse> especies = especieService.buscar(nomePopular)
                .stream()
                .map(EspecieMapper::paraResposta)
                .toList();

        return ResponseEntity.ok(especies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieResponse> buscarPorId(@PathVariable String id) {

        Especie especie = especieService.buscarPorId(id);

        return ResponseEntity.ok(EspecieMapper.paraResposta(especie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieResponse> atualizar(@PathVariable String id,
                                                     @Valid @RequestBody EspecieRequest especieRequest) {

        Especie especie = EspecieMapper.paraEntidade(especieRequest);
        especie = especieService.atualizar(id, especie);

        return ResponseEntity.ok(EspecieMapper.paraResposta(especie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        especieService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
