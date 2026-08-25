package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.model.PagamentoAluno;
import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.PagamentoAlunoRepository;
import br.com.alphacoach.app.repository.PlanosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoAlunoService {
    private PagamentoAlunoRepository pagamentoRepository;
    private AlunoRepository alunoRepository;
    private PlanosRepository planoRepository;

    public PagamentoAlunoService(PagamentoAlunoRepository pagamentoRepository, AlunoRepository alunoRepository, PlanosRepository planoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;
    }

    @Transactional
    public PagamentoAluno criar(PagamentoAluno pagamentoAluno) {
        Aluno aluno = alunoRepository.findById(pagamentoAluno.getAluno().getId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Planos plano = planoRepository.findById(pagamentoAluno.getPlano().getId()).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
        pagamentoAluno.setPlano(plano);
        pagamentoAluno.setAluno(aluno);
        return pagamentoRepository.save(pagamentoAluno);
    }

    @Transactional
    public PagamentoAluno alterar(PagamentoAluno pagamentoAluno, Long id) {
        PagamentoAluno procurado = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        if (pagamentoAluno.getAluno() != null) procurado.setAluno(pagamentoAluno.getAluno());
        if (pagamentoAluno.getCompetencia() != null) procurado.setCompetencia(pagamentoAluno.getCompetencia());
        if (pagamentoAluno.getDataPagamento() != null) procurado.setDataPagamento(pagamentoAluno.getDataPagamento());
        if (pagamentoAluno.getPlano() != null) procurado.setPlano(pagamentoAluno.getPlano());
        return pagamentoRepository.save(procurado);
    }

    public List<PagamentoAluno> listar() {
        List<PagamentoAluno> lista = pagamentoRepository.findAll();

        return lista;
    }

    public Optional<PagamentoAluno> procurarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    @Transactional
    public Boolean remover(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
