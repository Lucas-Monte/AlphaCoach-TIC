package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.PlanosRequest;
import br.com.alphacoach.app.dto.response.PlanosResponse;
import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.repository.PlanosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PlanosService {
    private PlanosRepository repository;

    public PlanosService(PlanosRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PlanosResponse criar(PlanosRequest resquest) {
        Planos plano = new Planos();
        plano.setDescricao(resquest.descricao());
        plano.setValor(resquest.valor());
        plano.setDuracaoMeses(resquest.duracaoMeses());
        plano.setTipoPlano(resquest.tipoPlano());
        plano.setAtivo(true);
        repository.save(plano);

        return new PlanosResponse(plano);
    }

    public List<Planos> listar() {
        return repository.findAll();
    }

    public PlanosResponse encontrarPorId(Long id) {
        Optional<Planos> procurado = repository.findById(id);
        if (procurado.isPresent()) {
            Planos encontrado = procurado.get();
            return new PlanosResponse(encontrado);
        }
        return null;
    }

    @Transactional
    public PlanosResponse alterar(PlanosRequest request, Long id) {
        Planos procurado = repository.findById(id).orElseThrow(() -> new RuntimeException("Plano não encontrado!"));
        if (request.descricao() != null) procurado.setDescricao(request.descricao());
        if (request.duracaoMeses() != null) procurado.setDuracaoMeses(request.duracaoMeses());
        if (request.tipoPlano() != null) procurado.setTipoPlano(request.tipoPlano());
        if (request.valor() != null) procurado.setValor(request.valor());
        repository.save(procurado);

        return new PlanosResponse(procurado);
    }

    @Transactional
    public PlanosResponse desativar(Long id) {
        Planos procurado = repository.findById(id).orElseThrow(() -> new RuntimeException("Plano não encontrado!"));
        procurado.desativar();
        repository.save(procurado);
        return new PlanosResponse(procurado);
    }

    @Transactional
    public PlanosResponse ativar(Long id) {
        Planos procurado = repository.findById(id).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
        procurado.ativar();
        repository.save(procurado);
        return new PlanosResponse(procurado);
    }
}
