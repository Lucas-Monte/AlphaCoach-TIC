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
        if (exercicioTreinoRepository.existsById(id)) {
            exercicioTreino.setId(id);
            return exercicioTreinoRepository.save(exercicioTreino);
        }
        return null;
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
