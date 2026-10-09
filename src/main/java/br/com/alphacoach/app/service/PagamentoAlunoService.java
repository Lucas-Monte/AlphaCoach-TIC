package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.PagamentoAlunoRequest;
import br.com.alphacoach.app.dto.request.ParcelaRequest;
import br.com.alphacoach.app.dto.response.PagamentoAlunoResponse;
import br.com.alphacoach.app.model.*;
import br.com.alphacoach.app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoAlunoService {
    private PagamentoAlunoRepository pagamentoRepository;
    private AlunoRepository alunoRepository;
    private ParcelaRepository parcelaRepository;
    private PagamentoParcelaRepository pagamentoParcelaRepository;

    public PagamentoAlunoService(PagamentoAlunoRepository pagamentoRepository, AlunoRepository alunoRepository, ParcelaRepository parcelaRepository, PagamentoParcelaRepository pagamentoParcelaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.alunoRepository = alunoRepository;
        this.parcelaRepository = parcelaRepository;
        this.pagamentoParcelaRepository = pagamentoParcelaRepository;
    }

    @Transactional
    public PagamentoAlunoResponse criar(PagamentoAlunoRequest request) {
        PagamentoAluno pagamentoAluno = new PagamentoAluno();
        pagamentoAluno.setDataPagamento(request.dataPagamento());
        pagamentoAluno.setValorPagamento(request.valorPago());
        pagamentoAluno.setFormaPagamento(request.formaPagamento());
        Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        pagamentoAluno.setAluno(aluno);
        Parcela parcela = parcelaRepository.findById(request.parcelaId()).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        pagamentoAluno.adicionarPagamentoParcela(parcela);
        pagamentoRepository.save(pagamentoAluno);
        return new PagamentoAlunoResponse(pagamentoAluno.getId(), pagamentoAluno.getAluno().getId(), pagamentoAluno.getDataPagamento(), pagamentoAluno.getValorPagamento());
    }

    @Transactional
    public PagamentoAlunoResponse alterar(PagamentoAlunoRequest request, Long id) {
        PagamentoAluno procurado = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        Aluno aluno = null;
        if (request.alunoId() != null) {
            aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            procurado.setAluno(aluno);
        }
        procurado.alterarDados(aluno, request.dataPagamento(), request.formaPagamento(), request.valorPago());

        pagamentoRepository.save(procurado);
        return new PagamentoAlunoResponse(procurado.getId(), procurado.getAluno().getId(), procurado.getDataPagamento(), procurado.getValorPagamento());
    }

    public List<PagamentoAluno> listar() {
        return pagamentoRepository.findAll();
    }

    public PagamentoAlunoResponse procurarPorId(Long id) {
        PagamentoAluno procurado = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        return new PagamentoAlunoResponse(procurado.getId(), procurado.getAluno().getId(), procurado.getDataPagamento(), procurado.getValorPagamento());
    }

    @Transactional
    public void remover(Long id) {
        PagamentoAluno pagamento = buscar(id);
        pagamento.estornarTodos();
        pagamentoRepository.delete(pagamento);
    }

    @Transactional
    public PagamentoAlunoResponse removerParcela(Long idPagamento, Long idPagamentoParcela) {
        PagamentoAluno pagamentoAluno = pagamentoRepository.findById(idPagamento).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        PagamentoParcela procurado = pagamentoAluno.getPagamentoParcelas().stream().filter(pagamentoParcela -> pagamentoParcela.getId().equals(idPagamentoParcela)).findFirst().orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        pagamentoAluno.removerPagamentoParcela(procurado);
        if (!pagamentoAluno.possuiParcelas()) {
            pagamentoRepository.delete(pagamentoAluno);
        }
        return new PagamentoAlunoResponse(pagamentoAluno.getId(), pagamentoAluno.getAluno().getId(), pagamentoAluno.getDataPagamento(), pagamentoAluno.getValorPagamento());
    }

    private PagamentoAluno buscar(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }
}
