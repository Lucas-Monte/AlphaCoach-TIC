package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.ExercicioTreino;
import br.com.alphacoach.app.repository.ExercicioTreinoRepository;
import br.com.alphacoach.app.repository.ExerciciosRepository;
import ch.qos.logback.core.joran.conditional.IfAction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExercicioTreinoService {
    private ExercicioTreinoRepository exercicioTreinoRepository;

    public ExercicioTreinoService(ExercicioTreinoRepository exercicioTreinoRepository) {
        this.exercicioTreinoRepository = exercicioTreinoRepository;
    }

    public ExercicioTreino alterar(ExercicioTreino exercicioTreino, Long id) {
        ExercicioTreino procurado = exercicioTreinoRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não adicionado ao treino!"));
        if (exercicioTreino.getCarga() != null) procurado.setCarga(exercicioTreino.getCarga());
        if (exercicioTreino.getIntensidade() != null) procurado.setIntensidade(exercicioTreino.getIntensidade());
        if (exercicioTreino.getPotencia() != null) procurado.setPotencia(exercicioTreino.getPotencia());
        if (exercicioTreino.getRepeticoes() != null) procurado.setRepeticoes(exercicioTreino.getRepeticoes());
        if (exercicioTreino.getSeries() != null) procurado.setSeries(exercicioTreino.getSeries());
        if (exercicioTreino.isStatus() != null) procurado.setStatus(exercicioTreino.isStatus());
        if (exercicioTreino.getTempoDescanso() != null) procurado.setTempoDescanso(exercicioTreino.getTempoDescanso());

        return exercicioTreinoRepository.save(procurado);
    }

    public List<ExercicioTreino> listar() {
        return exercicioTreinoRepository.findAll();
    }

    public Optional<ExercicioTreino> encontrarPorId(Long id) {
        return exercicioTreinoRepository.findById(id);
    }

    public boolean remover(Long id) {
        if (exercicioTreinoRepository.existsById(id)) {
            exercicioTreinoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
