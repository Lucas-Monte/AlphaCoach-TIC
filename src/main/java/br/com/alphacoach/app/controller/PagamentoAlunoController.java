package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.model.PagamentoAluno;
import br.com.alphacoach.app.service.PagamentoAlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoAlunoController {
    private PagamentoAlunoService service;

    public PagamentoAlunoController(PagamentoAlunoService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<PagamentoAluno>> listar() {
        List<PagamentoAluno> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    @PostMapping()
    public ResponseEntity<PagamentoAluno> criar(@RequestBody PagamentoAluno pagamentoAluno) {
        PagamentoAluno novo = service.criar(pagamentoAluno);
        URI uri = URI.create("/pagamento/" + novo.getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoAluno> procurarPorId(@PathVariable Long id) {
        Optional<PagamentoAluno> procurado = service.procurarPorId(id);
        if (procurado.isPresent()) {
            return ResponseEntity.ok(procurado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remover(@PathVariable Long id) {
        if(service.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PagamentoAluno> alterar(@RequestBody PagamentoAluno pagamentoAluno,@PathVariable Long id) {
        PagamentoAluno procurado = service.alterar(pagamentoAluno, id);
        return ResponseEntity.ok(procurado);

    }
}
