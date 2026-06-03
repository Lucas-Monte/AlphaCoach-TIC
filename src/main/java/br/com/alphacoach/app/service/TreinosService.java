package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.model.ExercicioTreino;
import br.com.alphacoach.app.model.Exercicios;
import br.com.alphacoach.app.model.Treinos;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.ExercicioTreinoRepository;
import br.com.alphacoach.app.repository.ExerciciosRepository;
import br.com.alphacoach.app.repository.TreinosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreinosService {
    private TreinosRepository treinosRepository;
    private AlunoRepository alunoRepository;
    private ExerciciosRepository exerciciosRepository;
    private ExercicioTreinoRepository exercicioTreinoRepository;

    public TreinosService(TreinosRepository treinosRepository, AlunoRepository alunoRepository, ExerciciosRepository exerciciosRepository, ExercicioTreinoRepository exercicioTreinoRepository) {
        this.treinosRepository = treinosRepository;
        this.alunoRepository = alunoRepository;
        this.exerciciosRepository = exerciciosRepository;
        this.exercicioTreinoRepository = exercicioTreinoRepository;
    }

    public Treinos criar(Treinos treino) {
        if (treino.getAluno() != null && treino.getAluno().getId() != null) {
            Aluno aluno = alunoRepository.findById(treino.getAluno().getId()).orElseThrow(() -> new RuntimeException("Aluno não cadastrado!"));
            treino.setAluno(aluno);
        } else {
            throw new IllegalArgumentException("Informe o aluno...");
        }

        if (treino.getExercicios() != null) {
            for (ExercicioTreino ex : treino.getExercicios()) {
                if (ex.getExercicio() != null && ex.getExercicio().getId() != null) {
                    Exercicios exercicio = exerciciosRepository.findById(ex.getExercicio().getId()).orElseThrow(() -> new RuntimeException("Exercicio não cadastrado"));

                    ex.setExercicio(exercicio);
                    ex.setTreino(treino);
                } else {
                    throw new IllegalArgumentException("Exercicio não informado");
                }

            }
        } else {
            throw new IllegalArgumentException("Informe ao menos um exercicio!");
        }

        return treinosRepository.save(treino);
    }

    public List<Treinos> listar() {
        return treinosRepository.findAll();
    }

    public Optional<Treinos> econtrarPorId(Long id) {
        return treinosRepository.findById(id);
    }

    public Treinos alterar(Treinos treino, Long id) {
        if (treinosRepository.existsById(id)) {
            treino.setId(id);
            return treinosRepository.save(treino);
        }
        return null;
    }

    public boolean remover(Long id) {
        if (treinosRepository.existsById(id)) {
            treinosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
