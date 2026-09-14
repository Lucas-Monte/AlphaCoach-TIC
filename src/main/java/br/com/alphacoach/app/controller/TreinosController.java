package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.TreinosRequest;
import br.com.alphacoach.app.dto.response.TreinosResponse;
import br.com.alphacoach.app.model.Treinos;
import br.com.alphacoach.app.service.TreinosService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/treinos")
public class TreinosController {
    private TreinosService service;

    public TreinosController(TreinosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TreinosResponse> criar(@Valid @RequestBody TreinosRequest request) {
        TreinosResponse novo = service.criar(request);
        URI uri = URI.create("/treinos/" + novo.treino().getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Treinos>> listar() {
        List<Treinos> treinos = service.listar();
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinosResponse> encontrarPorId(@PathVariable Long id) {
        TreinosResponse resp = service.econtrarPorId(id);
        if (resp != null) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TreinosResponse> alterar(@Valid @RequestBody TreinosRequest request, @PathVariable Long id) {
        TreinosResponse novo = service.alterar(request, id);
        if (novo != null) {
            return ResponseEntity.ok(novo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Treinos> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
