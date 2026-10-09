package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.MatriculaRequest;
import br.com.alphacoach.app.dto.response.MatriculaResponse;
import br.com.alphacoach.app.model.Matricula;
import br.com.alphacoach.app.model.enums.StatusMatricula;
import br.com.alphacoach.app.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {
    private MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<MatriculaResponse> criar(@Valid @RequestBody MatriculaRequest request) {
        MatriculaResponse matricula = matriculaService.criar(request);
        URI uri = URI.create("matricula/" + matricula.id());
        return ResponseEntity.created(uri).body(matricula);
    }

    @GetMapping
    public ResponseEntity<List<Matricula>> listar() {
        List<Matricula> lista = matriculaService.listarAtivos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponse> procurarPorId(@PathVariable Long id) {
        MatriculaResponse matricula = matriculaService.procurarPorId(id);
        if (matricula != null) {
            return ResponseEntity.ok(matricula);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Matricula>> listarTodos() {
        List<Matricula> lista = matriculaService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MatriculaResponse> alterar(@Valid @RequestBody MatriculaRequest request,@PathVariable Long id) {
        MatriculaResponse alterado = matriculaService.alterar(request, id);
        if (alterado != null) {
            return ResponseEntity.ok(alterado);
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<MatriculaResponse> desativar(@PathVariable Long id) {
        MatriculaResponse cancelado = matriculaService.desativar(id);
        return ResponseEntity.ok(cancelado);
    }

}
