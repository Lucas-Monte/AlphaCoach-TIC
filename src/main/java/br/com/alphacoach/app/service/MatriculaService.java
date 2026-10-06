package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.MatriculaRequest;
import br.com.alphacoach.app.dto.response.MatriculaResponse;
import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.model.Matricula;
import br.com.alphacoach.app.model.Parcela;
import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.model.enums.StatusMatricula;
import br.com.alphacoach.app.model.enums.StatusParcela;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.MatriculaRepository;
import br.com.alphacoach.app.repository.PlanosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {
    private MatriculaRepository matriculaRepository;
    private AlunoRepository alunoRepository;
    private PlanosRepository planosRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository, PlanosRepository planosRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.planosRepository = planosRepository;
    }

    @Transactional
    public MatriculaResponse criar(MatriculaRequest request) {
        Matricula matricula = new Matricula();
        matricula.setStatusMatricula(StatusMatricula.ATIVO);
        matricula.setDataInicio(LocalDate.now());
        Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        if (aluno.getAtivo()){
            matricula.setAluno(aluno);
        } else {
            throw new BusinessException("Aluno não está ativo");
        }

        Planos plano = planosRepository.findById(request.planoId()).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
        if (plano.getAtivo()) {
            matricula.setPlano(plano);
        } else {
            throw new BusinessException("Plano não está ativo");
        }

        List<BigDecimal> valoresParcelas = plano.gerarParcelas(plano.getValor(), plano.getDuracaoMeses());
        for (int i = 0; i < valoresParcelas.size(); i++) {
            Parcela nova = new Parcela();
            nova.setStatusParcela(StatusParcela.EM_DIA);
            nova.setMatricula(matricula);
            nova.setNumeroParcela(i+1);
            LocalDate vencimento = matricula.getDataInicio().plusMonths(i+1);
            nova.setVencimentoParcela(vencimento);
            nova.setCompetencia(vencimento.withDayOfMonth(1));
            nova.setValorParcela(valoresParcelas.get(i));
            matricula.adicionarParcela(nova);
        }

        matriculaRepository.save(matricula);
        return new MatriculaResponse(matricula.getId(), matricula.getPlano().getId(), matricula.getAluno().getId(), matricula.getStatusMatricula(), matricula.getDataInicio());
    }

    public List<Matricula> listarAtivos() {
        return matriculaRepository.findByStatusMatricula(StatusMatricula.ATIVO);
    }

    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }

    @Transactional
    public MatriculaResponse alterar(MatriculaRequest request, Long id) {
        Matricula procurado = matriculaRepository.findById(id).orElseThrow(() -> new RuntimeException("Matricula não encontrada"));
        if (request.alunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            procurado.setAluno(aluno);
        }
        if (request.planoId() != null) {
            Planos plano = planosRepository.findById(request.planoId()).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
            procurado.setPlano(plano);
        }
        if (request.statusMatricula() != null) procurado.setStatusMatricula(request.statusMatricula());
        if (request.dataInicio() != null) procurado.setDataInicio(request.dataInicio());
        matriculaRepository.save(procurado);
        return new MatriculaResponse(procurado.getId(), procurado.getPlano().getId(), procurado.getAluno().getId(), procurado.getStatusMatricula(), procurado.getDataInicio());
    }

    @Transactional
    public MatriculaResponse desativar(Long id) {
        Matricula procurado = matriculaRepository.findById(id).orElseThrow(() -> new RuntimeException("Matricula não encontrada"));
        procurado.cancelarMatricula();
        matriculaRepository.save(procurado);
        return new MatriculaResponse(procurado.getId(), procurado.getPlano().getId(), procurado.getAluno().getId(), procurado.getStatusMatricula(), procurado.getDataInicio());
    }

}
