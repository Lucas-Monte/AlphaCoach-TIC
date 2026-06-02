package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.PlanosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private AlunoRepository repository;
    private PlanosRepository planosRepository;

    public AlunoService(PlanosRepository planosRepository, AlunoRepository repository) {
        this.planosRepository = planosRepository;
        this.repository = repository;
    }

    public Aluno salvar(Aluno aluno) {
        if (repository.existsByCpf(aluno.getCpf())) {
            throw new IllegalArgumentException("Aluno já cadastrado!");
        }

        if (aluno.getPlano() != null && aluno.getPlano().getId() != null) {
            Planos plano = planosRepository.findById(aluno.getPlano().getId()).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
            aluno.setPlano(plano);
        } else {
            throw new IllegalArgumentException("Informe o plano!");
        }

        return repository.save(aluno);
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Aluno alterarAluno(Aluno novo, Long id) {
        if(repository.existsById(id)) {
            novo.setId(id);
            return repository.save(novo);
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
