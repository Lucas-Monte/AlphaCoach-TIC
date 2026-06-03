package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.model.Treinos;
import br.com.alphacoach.app.service.TreinosService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/treinos")
public class TreinosController {
    private TreinosService service;

    public TreinosController(TreinosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Treinos> criar(@RequestBody Treinos treino) {
        Treinos novo = service.criar(treino);
        URI uri = URI.create("/treinos/" + novo.getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Treinos>> listar() {
        List<Treinos> treinos = service.listar();
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Treinos>> encontrarPorId(@PathVariable Long id) {
        Optional<Treinos> resp = service.econtrarPorId(id);
        if (resp.isPresent()) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Treinos> alterar(@RequestBody Treinos treino, @PathVariable Long id) {
        Treinos novo = service.alterar(treino, id);
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
