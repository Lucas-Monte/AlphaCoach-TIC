package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.PagamentoAlunoRequest;
import br.com.alphacoach.app.dto.response.PagamentoAlunoResponse;
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
    public ResponseEntity<PagamentoAlunoResponse> criar(@RequestBody PagamentoAlunoRequest request) {
        PagamentoAlunoResponse novo = service.criar(request);
        URI uri = URI.create("/pagamento/" + novo.id());
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoAlunoResponse> procurarPorId(@PathVariable Long id) {
        PagamentoAlunoResponse procurado = service.procurarPorId(id);
        if (procurado != null) {
            return ResponseEntity.ok(procurado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PagamentoAlunoResponse> alterar(@RequestBody PagamentoAlunoRequest request,@PathVariable Long id) {
        PagamentoAlunoResponse procurado = service.alterar(request, id);
        return ResponseEntity.ok(procurado);

    }

    @DeleteMapping("/{id}/remover/{idItem}")
    public ResponseEntity<PagamentoAlunoResponse> removerParcela(@PathVariable Long id, @PathVariable Long idItem){
        PagamentoAlunoResponse pagamento = service.removerParcela(id, idItem);
        return ResponseEntity.ok(pagamento);
    }
}
