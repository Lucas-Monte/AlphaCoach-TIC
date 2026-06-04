package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Exercicios;
import br.com.alphacoach.app.repository.ExerciciosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciciosService {
    private ExerciciosRepository exerciciosRepository;

    public ExerciciosService(ExerciciosRepository exerciciosRepository) {
        this.exerciciosRepository = exerciciosRepository;
    }

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

    public Exercicios alterar(Exercicios exercicio, Long id) {
        Exercicios procurado = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado!"));
        if (exercicio.getNome() != null) procurado.setNome(exercicio.getNome());
        if (exercicio.getDescricao() != null) procurado.setDescricao(exercicio.getDescricao());
        if (exercicio.getAtivo() != null) procurado.setAtivo(exercicio.getAtivo());
        if (exercicio.getLinkVideo() != null) procurado.setLinkVideo(exercicio.getLinkVideo());

        return exerciciosRepository.save(procurado);
    }

    public boolean remover(Long id) {
        if (exerciciosRepository.existsById(id)) {
            exerciciosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
