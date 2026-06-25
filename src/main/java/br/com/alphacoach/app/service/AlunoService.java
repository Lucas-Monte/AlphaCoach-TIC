package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.model.Planos;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.PlanosRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
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

    @Transactional
    public Aluno alterarAluno(Aluno novo, Long id) {
        Aluno procurado = repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));
         if (novo.getNome() != null) procurado.setNome(novo.getNome());
         if (novo.getEmail() != null) procurado.setEmail(novo.getEmail());
         if (novo.getCpf() != null) procurado.setCpf(novo.getCpf());
         if (novo.getDataNascimento() != null) procurado.setDataNascimento(novo.getDataNascimento());
         if (novo.getEndereco() != null) procurado.setEndereco(novo.getEndereco());
         if (novo.getTipoCliente() != null) procurado.setTipoCliente(novo.getTipoCliente());
         if (novo.getAtivo() != null) procurado.setAtivo(novo.getAtivo());
         if (novo.getTelefone() != null) procurado.setTelefone(novo.getTelefone());
         if (novo.getPlano() != null) procurado.setPlano(novo.getPlano());
         if (novo.getObjetivo() != null) procurado.setObjetivo(novo.getObjetivo());
         if (novo.getAnamnese() != null) procurado.setAnamnese(novo.getAnamnese());
         return repository.save(procurado);
    }

    @Transactional
    public boolean remover(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
