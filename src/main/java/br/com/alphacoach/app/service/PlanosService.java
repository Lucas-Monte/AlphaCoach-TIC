package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.repository.PlanosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PlanosService {
    private PlanosRepository repository;

    public PlanosService(PlanosRepository repository) {
        this.repository = repository;
    }

    public Planos criar(Planos plano) {
        return repository.save(plano);
    }

    public List<Planos> listar() {
        return repository.findAll();
    }

    public Optional<Planos> encontrarPorId(Long id) {
        return repository.findById(id);
    }

    public Planos alterar(Planos plano, Long id) {
        Planos procurado = repository.findById(id).orElseThrow(() -> new RuntimeException("Plano não encontrado!"));
        if (plano.isAtivo() != null) procurado.setAtivo(plano.isAtivo());
        if (plano.getDescricao() != null) procurado.setDescricao(plano.getDescricao());
        if (plano.getDuracaoMeses() != null) procurado.setDuracaoMeses(plano.getDuracaoMeses());
        if (plano.getTipoPlano() != null) procurado.setTipoPlano(plano.getTipoPlano());
        if (plano.getValor() != null) procurado.setValor(plano.getValor());

        return repository.save(procurado);
    }

    public boolean remover(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
