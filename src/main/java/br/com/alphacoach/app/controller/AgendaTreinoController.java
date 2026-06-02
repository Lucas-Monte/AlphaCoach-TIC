package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.model.AgendaTreino;
import br.com.alphacoach.app.service.AgendaTreinoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/agendaTreino")
public class AgendaTreinoController {
    private AgendaTreinoService service;

    public AgendaTreinoController(AgendaTreinoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendaTreino> criar(@RequestBody AgendaTreino agendaTreino) {
        AgendaTreino novo = service.criar(agendaTreino);
        URI uri = URI.create("/agendaTreino" + novo.getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<AgendaTreino>> listar() {
        List<AgendaTreino> resp = service.listar();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AgendaTreino>> encontrarPorId(@PathVariable Long id) {
        Optional<AgendaTreino> resp = service.encontrarPorId(id);
        if (!resp.isEmpty()) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AgendaTreino> alterar(@RequestBody AgendaTreino agendaTreino, @PathVariable Long id) {
        AgendaTreino novo = service.alterar(agendaTreino, id);
        if (novo != null) {
            return ResponseEntity.ok(novo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AgendaTreino> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
