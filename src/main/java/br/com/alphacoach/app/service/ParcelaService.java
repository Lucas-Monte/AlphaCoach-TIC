package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.ParcelaRequest;
import br.com.alphacoach.app.dto.response.ParcelaResponse;
import br.com.alphacoach.app.model.Parcela;
import br.com.alphacoach.app.repository.MatriculaRepository;
import br.com.alphacoach.app.repository.ParcelaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParcelaService {
    ParcelaRepository parcelaRepository;
    MatriculaRepository matriculaRepository;

    @Transactional
    public ParcelaResponse alterar(ParcelaRequest request) {
        Parcela procurada = parcelaRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        if (request.statusParcela() != null) procurada.setStatusParcela(request.statusParcela());
        if (request.vencimentoParcela() != null) procurada.setVencimentoParcela(request.vencimentoParcela());
        parcelaRepository.save(procurada);
        return new ParcelaResponse(procurada);
    }

    @Transactional
    public ParcelaResponse estornar(Long id) {
        Parcela procurada = parcelaRepository.findById(id).orElseThrow(() -> new RuntimeException("Parcela não encontrada"));
        procurada.estornarPagamento();
        parcelaRepository.save(procurada);
        return new ParcelaResponse(procurada);
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
