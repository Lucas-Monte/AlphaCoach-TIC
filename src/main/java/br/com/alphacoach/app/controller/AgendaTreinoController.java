package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.AgendaTreinoRequest;
import br.com.alphacoach.app.dto.response.AgendaTreinoResponse;
import br.com.alphacoach.app.model.AgendaTreino;
import br.com.alphacoach.app.service.AgendaTreinoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendatreino")
public class AgendaTreinoController {
    private AgendaTreinoService service;

    public AgendaTreinoController(AgendaTreinoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendaTreinoResponse> criar(@Valid @RequestBody AgendaTreinoRequest request) {
        AgendaTreinoResponse novo = service.criar(request);
        URI uri = URI.create("/agendatreino" + novo.id());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<AgendaTreino>> listar() {
        List<AgendaTreino> resp = service.listar();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaTreinoResponse> encontrarPorId(@PathVariable Long id) {
        AgendaTreinoResponse resp = service.encontrarPorId(id);
        if (resp != null) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AgendaTreinoResponse> alterar(@Valid @RequestBody AgendaTreinoRequest request, @PathVariable Long id) {
        AgendaTreinoResponse alterado = service.alterar(request, id);
        if (alterado != null) {
            return ResponseEntity.ok(alterado);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/checkin")
    public ResponseEntity<AgendaTreinoResponse> fazerCheckin(@PathVariable Long id) {
        AgendaTreinoResponse agenda = service.fazerCheckIn(id);
        if (agenda != null) {
            return ResponseEntity.ok(agenda);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AgendaTreinoResponse> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
