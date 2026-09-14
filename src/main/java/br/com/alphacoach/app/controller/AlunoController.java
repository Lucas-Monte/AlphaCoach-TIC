package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.AlterAlunoRequest;
import br.com.alphacoach.app.dto.request.AlunoRequest;
import br.com.alphacoach.app.dto.response.AlunoResponse;
import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    private AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Aluno>> listar() {
        List<Aluno> resp = service.listar();
        if (!resp.isEmpty()) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarAtivos() {
        List<Aluno> resp = service.listarAtivos();
        if (!resp.isEmpty()) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> buscarPorId(@PathVariable Long id) {
        AlunoResponse resp = service.buscarPorId(id);
        if (resp != null) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> adicionar(@Valid @RequestBody AlunoRequest request) {
        AlunoResponse alunoResponse = service.salvar(request);
        URI uri = URI.create("/alunos/" + alunoResponse.id());
        return ResponseEntity.created(uri).body(alunoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlunoResponse> alterarAluno(@Valid @RequestBody AlterAlunoRequest request, @PathVariable Long id) {
        AlunoResponse alterado = service.alterarAluno(request, id);
        if (alterado != null) {
            return ResponseEntity.ok(alterado);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/remover")
    public ResponseEntity<AlunoResponse> removerAluno(@PathVariable Long id) {
        AlunoResponse removido = service.remover(id);
        if (removido != null) {
            return ResponseEntity.ok(removido);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/recuperarAluno")
    public ResponseEntity<AlunoResponse> recuperarAluno(@PathVariable Long id) {
        AlunoResponse recuperado = service.recuperarAluno(id);
        if (recuperado != null) {
            return ResponseEntity.ok(recuperado);
        }
        return ResponseEntity.notFound().build();
    }
}
