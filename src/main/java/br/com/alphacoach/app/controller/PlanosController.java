package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.PlanosRequest;
import br.com.alphacoach.app.dto.response.PlanosResponse;
import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.service.PlanosService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanosController {
    private PlanosService service;

    public PlanosController(PlanosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PlanosResponse> criar(@Valid @RequestBody PlanosRequest resquest) {
        PlanosResponse novo = service.criar(resquest);
        URI uri = URI.create("/planos" + novo.plano().getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Planos>> listar() {
        List<Planos> resp = service.listar();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanosResponse> encontarPorId(@PathVariable Long id) {
        PlanosResponse plano = service.encontrarPorId(id);
        if (plano != null) {
            return ResponseEntity.ok(plano);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanosResponse> alterar(@Valid @RequestBody PlanosRequest request, @PathVariable Long id) {
        PlanosResponse novo = service.alterar(request, id);
        return ResponseEntity.ok(novo);
    }

    @PatchMapping("/{id}/desativar/plano")
    public ResponseEntity<PlanosResponse> desativar(@PathVariable Long id) {
        PlanosResponse plano = service.desativar(id);
        return ResponseEntity.ok(plano);
    }

    @PatchMapping("/{id}/ativar/plano")
    public ResponseEntity<PlanosResponse> ativar(@PathVariable Long id) {
        PlanosResponse plano = service.ativar(id);
        return ResponseEntity.ok(plano);
    }
}
