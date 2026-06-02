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
        if (repository.existsById(id)) {
            plano.setId(id);
            return repository.save(plano);
        }
        return null;
    }

    public boolean remover(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
