package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.PagamentoAlunoRequest;
import br.com.alphacoach.app.dto.response.PagamentoAlunoResponse;
import br.com.alphacoach.app.model.*;
import br.com.alphacoach.app.repository.*;
import jakarta.transaction.Transactional;
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
        if (request.alunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            procurado.setAluno(aluno);
        }
        if (request.dataPagamento() != null) procurado.setDataPagamento(request.dataPagamento());
        if (request.formaPagamento() != null) procurado.setFormaPagamento(request.formaPagamento());
        if (request.valorPago() != null) procurado.setValorPagamento(request.valorPago());
        pagamentoRepository.save(procurado);
        return new PagamentoAlunoResponse(procurado.getId(), procurado.getAluno().getId(), procurado.getDataPagamento(), procurado.getValorPagamento());
    }

    public List<PagamentoAluno> listar() {
        return pagamentoRepository.findAll();
    }

    @Transactional
    public Boolean remover(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public PagamentoAlunoResponse removerParcela(PagamentoAlunoRequest request, Long id) {
        //Preciso pegar o pagamentoParcela para poder passar no metodo de removerPagamentoParcela, para ele remover da lista de pagamentosParcelas
    }
}
