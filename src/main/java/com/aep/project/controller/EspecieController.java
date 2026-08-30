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
        Especie request = EspecieMapper.mapeandoParaEntidade(especieRequest);
        Especie especie = especieService.criar(request);
        EspecieResponse especieResponse = EspecieMapper.mapeandoParaResposta(especie);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(especie.getId())
                .toUri();

        return ResponseEntity.created(uri).body(especieResponse);
    }

    @GetMapping
    public ResponseEntity<List<Especie>> buscar(@RequestParam(required = false) String nomePopular) {
        return ResponseEntity.ok(especieService.buscar(nomePopular));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especie> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(especieService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especie> atualizar(@PathVariable String id, @Valid @RequestBody Especie novaEspecie) {
        return ResponseEntity.ok(especieService.atualizar(id, novaEspecie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        especieService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
