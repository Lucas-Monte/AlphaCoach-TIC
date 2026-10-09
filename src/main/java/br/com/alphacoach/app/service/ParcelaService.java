package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.ParcelaRequest;
import br.com.alphacoach.app.dto.response.ParcelaResponse;
import br.com.alphacoach.app.model.PagamentoParcela;
import br.com.alphacoach.app.model.Parcela;
import br.com.alphacoach.app.repository.MatriculaRepository;
import br.com.alphacoach.app.repository.PagamentoParcelaRepository;
import br.com.alphacoach.app.repository.ParcelaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParcelaService {
    ParcelaRepository parcelaRepository;
    MatriculaRepository matriculaRepository;
    PagamentoParcelaRepository pagamentoParcelaRepository;
    PagamentoAlunoService pagamentoAlunoService;

    public ParcelaService(ParcelaRepository parcelaRepository, MatriculaRepository matriculaRepository, PagamentoParcelaRepository pagamentoParcelaRepository, PagamentoAlunoService pagamentoAlunoService) {
        this.parcelaRepository = parcelaRepository;
        this.matriculaRepository = matriculaRepository;
        this.pagamentoParcelaRepository = pagamentoParcelaRepository;
        this.pagamentoAlunoService = pagamentoAlunoService;
    }

    @Transactional
    public ParcelaResponse alterar(ParcelaRequest request, Long id) {
        Parcela procurada = parcelaRepository.findById(id).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        if (request.vencimentoParcela() != null) {
            procurada.alterarVencimento(request.vencimentoParcela());
        }
        parcelaRepository.save(procurada);
        return new ParcelaResponse(procurada);
    }

    @Transactional
    public ParcelaResponse estornar(Long id) {
        Parcela parcela = parcelaRepository.findById(id).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        PagamentoParcela item = pagamentoParcelaRepository.findByParcelaId(id).orElseThrow(() -> new RuntimeException("A parcena não possui pagamento para estornar"));
        pagamentoAlunoService.removerParcela(item.getPagamentoAluno().getId(), item.getId());
        return new ParcelaResponse(parcela);
    }

    @Transactional
    public ParcelaResponse cancelar(Long id) {
        Parcela procurada = parcelaRepository.findById(id).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        procurada.cancelar();
        parcelaRepository.save(procurada);
        return new ParcelaResponse(procurada);
    }

    @Transactional
    public ParcelaResponse atualizarStatus(Long id) {
        Parcela procurada = parcelaRepository.findById(id).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        procurada.atualziarStatusPorVencimento();
        parcelaRepository.save(procurada);
        return new ParcelaResponse(procurada);
    }




}
