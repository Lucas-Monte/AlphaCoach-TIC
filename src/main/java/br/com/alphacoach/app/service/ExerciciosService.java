package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Exercicios;
import br.com.alphacoach.app.repository.ExerciciosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciciosService {
    private ExerciciosRepository exerciciosRepository;

    public ExerciciosService(ExerciciosRepository exerciciosRepository) {
        this.exerciciosRepository = exerciciosRepository;
    }

    @Transactional
    public Exercicios criar(Exercicios exercicio) {
        if (exerciciosRepository.existsByNome(exercicio.getNome())) {
            throw new IllegalArgumentException("Exercício já cadastrado!");
        }

        return exerciciosRepository.save(exercicio);
    }

    public List<Exercicios> listar() {
        return exerciciosRepository.findAll();
    }

    public Optional<Exercicios> encontrarPorId(Long id) {
        return exerciciosRepository.findById(id);
    }

    @Transactional
    public Exercicios alterar(Exercicios exercicio, Long id) {
        Exercicios procurado = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado!"));
        if (exercicio.getNome() != null) procurado.setNome(exercicio.getNome());
        if (exercicio.getDescricao() != null) procurado.setDescricao(exercicio.getDescricao());
        if (exercicio.getAtivo() != null) procurado.setAtivo(exercicio.getAtivo());
        if (exercicio.getLinkVideo() != null) procurado.setLinkVideo(exercicio.getLinkVideo());

        return exerciciosRepository.save(procurado);
    }

    @Transactional
    public Exercicios remover(Long id) {
        Exercicios exercicio = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));
        if (exercicio.getAtivo()) {
            exercicio.setAtivo(false);
        }

        return exercicio;
    }

    @Transactional
    public Exercicios recuperarExercicio(Long id) {
        Exercicios exercicio = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));
        if (!exercicio.getAtivo()) {
            exercicio.setAtivo(true);
        }

        return exercicio;
    }

    public List<Exercicios> listarAtivos() {
        return exerciciosRepository.findByAtivoTrue();
    }
}
