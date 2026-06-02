package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
    private AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listar() {
        List<Aluno> resp = service.listar();
        if (!resp.isEmpty()) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Aluno>> buscarPorId(@PathVariable Long id) {
        Optional<Aluno> resp = service.buscarPorId(id);
        if (resp != null) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Aluno> salvar(@RequestBody Aluno aluno) {
        Aluno novo = service.salvar(aluno);
        URI uri = URI.create("/alunos/" + novo.getId());
        return ResponseEntity.created(uri).body(novo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> alterarAluno(@RequestBody Aluno aluno, @PathVariable Long id) {
        Aluno alterado = service.alterarAluno(aluno, id);
        if (aluno != null) {
            return ResponseEntity.ok(aluno);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Aluno> removerAluno(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
