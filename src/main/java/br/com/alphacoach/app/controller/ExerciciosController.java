package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.ExercicioRequest;
import br.com.alphacoach.app.dto.response.ExercicioResponse;
import br.com.alphacoach.app.model.Exercicios;
import br.com.alphacoach.app.service.ExerciciosService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Spliterator;

@RestController
@RequestMapping("/exercicios")
public class ExerciciosController {
    private ExerciciosService service;

    public ExerciciosController(ExerciciosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExercicioResponse> criar(@RequestBody ExercicioRequest request) {
        ExercicioResponse criado = service.criar(request);
        URI uri = URI.create("/exercicios/" + criado.id());
        return ResponseEntity.created(uri).body(criado);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Exercicios>> listar() {
        List<Exercicios> resp = service.listar();
        if (resp != null) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Exercicios>> listarAtivos() {
        List<Exercicios> resp = service.listarAtivos();
        if (!resp.isEmpty()) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExercicioResponse> encontrarPorId(@PathVariable Long id) {
        ExercicioResponse procurado = service.encontrarPorId(id);

        if (procurado != null) {
            return ResponseEntity.ok(procurado);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExercicioResponse> alterar(@RequestBody ExercicioRequest request, @PathVariable Long id) {
        ExercicioResponse alterado = service.alterar(request, id);
        if (alterado != null) {
            return ResponseEntity.ok(alterado);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/remover")
    public ResponseEntity<ExercicioResponse> remover(@PathVariable Long id) {
        ExercicioResponse exercicio = service.remover(id);
        if (exercicio!=null) {
            return ResponseEntity.ok(exercicio);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/recuperarExercicio")
    public ResponseEntity<ExercicioResponse> recuperarExercicio(@PathVariable Long id) {
        ExercicioResponse exercicio = service.recuperarExercicio(id);
        if (exercicio!=null) {
            return ResponseEntity.ok(exercicio);
        }
        return ResponseEntity.notFound().build();
    }
}
