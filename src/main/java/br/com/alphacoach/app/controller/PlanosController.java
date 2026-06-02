package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.service.PlanosService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/planos")
public class PlanosController {
    private PlanosService service;

    public PlanosController(PlanosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Planos> criar(@RequestBody Planos plano) {
        Planos novo = service.criar(plano);
        URI uri = URI.create("/planos" + novo.getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Planos>> listar() {
        List<Planos> resp = service.listar();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Planos>> encontarPorId(@PathVariable Long id) {
        Optional<Planos> plano = service.encontrarPorId(id);
        if (!plano.isEmpty()) {
            return ResponseEntity.ok(plano);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Planos> alterar(@RequestBody Planos plano, @PathVariable Long id) {
        Planos novo = service.alterar(plano, id);
        return ResponseEntity.ok(novo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Planos> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
