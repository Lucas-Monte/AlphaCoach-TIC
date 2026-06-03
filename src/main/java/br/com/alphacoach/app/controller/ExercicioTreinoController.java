package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.model.ExercicioTreino;
import br.com.alphacoach.app.service.ExercicioTreinoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("exercicio-treino")
public class ExercicioTreinoController {
    private ExercicioTreinoService service;

    public ExercicioTreinoController(ExercicioTreinoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ExercicioTreino>> listar() {
        List<ExercicioTreino> exerciciosTreino = service.listar();
        return ResponseEntity.ok(exerciciosTreino);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ExercicioTreino>> encontrarPorId(@PathVariable Long id) {
        Optional<ExercicioTreino> ex = service.encontrarPorId(id);
        if (ex.isPresent()) {
            return ResponseEntity.ok(ex);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExercicioTreino> alterar(@RequestBody ExercicioTreino exercicioTreino, @PathVariable Long id) {
        ExercicioTreino novo = service.alterar(exercicioTreino, id);
        if (novo != null) {
            return ResponseEntity.ok(novo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExercicioTreino> remover(@PathVariable Long id) {
        if (service.remover(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
