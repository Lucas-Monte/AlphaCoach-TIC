package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.ExercicioTreinoRequest;
import br.com.alphacoach.app.dto.request.TreinosRequest;
import br.com.alphacoach.app.dto.response.TreinosResponse;
import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.model.ExercicioTreino;
import br.com.alphacoach.app.model.Exercicios;
import br.com.alphacoach.app.model.Treinos;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.ExercicioTreinoRepository;
import br.com.alphacoach.app.repository.ExerciciosRepository;
import br.com.alphacoach.app.repository.TreinosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Transactional
    public TreinosResponse criar(TreinosRequest request) {
        Treinos novo = new Treinos();
        novo.setNome(request.nome());

        Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não cadastrado!"));
        novo.setAluno(aluno);
        novo.setDataCriacao(LocalDate.now());
        novo.setStatus(true);
        //Passar um ExercicioTreinoRequest no TreinoRequest
        for (ExercicioTreinoRequest ex : request.exercicios()) {
            Exercicios exercicio = exerciciosRepository.findById(ex.exercicioId()).orElseThrow(() -> new RuntimeException("Exercicio não cadastrado"));
            ExercicioTreino exercicioTreino = new ExercicioTreino();
            exercicioTreino.setExercicio(exercicio);
            exercicioTreino.setTreino(novo);
            exercicioTreino.setPotencia(ex.potencia());
            exercicioTreino.setIntensidade(ex.intensidade());
            exercicioTreino.setSeries(ex.series());
            exercicioTreino.setRepeticoes(ex.repeticoes());
            exercicioTreino.setCarga(ex.carga());
            exercicioTreino.setTempoDescanso(ex.tempoDescanso());
            novo.adicionarExercicio(exercicioTreino);
        }
        treinosRepository.save(novo);
        return new TreinosResponse(novo);
    }

    public List<Treinos> listar() {
        return treinosRepository.findAll();
    }

    public TreinosResponse econtrarPorId(Long id) {
        Optional<Treinos> procurado = treinosRepository.findById(id);
        if (procurado.isPresent()){
            Treinos encontrado = procurado.get();
            return new TreinosResponse(encontrado);
        }
        return null;
    }

    @Transactional
    public TreinosResponse alterar(TreinosRequest request, Long id) {
        Treinos alterado = treinosRepository.findById(id).orElseThrow(() -> new RuntimeException("Treino não encontrado!"));
        if (request.nome() != null) alterado.setNome(request.nome());
        if (request.alunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));
            alterado.setAluno(aluno);
        }
        if (request.exercicios() != null) {
            for (ExercicioTreinoRequest ex : request.exercicios()) {
                Exercicios exercicio = exerciciosRepository.findById(ex.exercicioId()).orElseThrow(() -> new RuntimeException("Exercicio não cadastrado"));
                ExercicioTreino exercicioTreino = new ExercicioTreino();
                exercicioTreino.setExercicio(exercicio);
                exercicioTreino.setTreino(alterado);
                exercicioTreino.setPotencia(ex.potencia());
                exercicioTreino.setIntensidade(ex.intensidade());
                exercicioTreino.setSeries(ex.series());
                exercicioTreino.setRepeticoes(ex.repeticoes());
                exercicioTreino.setCarga(ex.carga());
                exercicioTreino.setTempoDescanso(ex.tempoDescanso());
            }
        }
        treinosRepository.save(alterado);
        return new TreinosResponse(alterado);
    }

    @Transactional
    public boolean remover(Long id) {
        if (treinosRepository.existsById(id)) {
            treinosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
