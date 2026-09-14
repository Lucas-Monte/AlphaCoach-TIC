package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.AlterAlunoRequest;
import br.com.alphacoach.app.dto.request.AlunoRequest;
import br.com.alphacoach.app.dto.response.AlunoResponse;
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
    public AlunoResponse salvar(AlunoRequest request) {
        if (repository.existsByCpf(request.cpf())) {
            throw new IllegalArgumentException("Aluno já cadastrado!");
        }
        Aluno novo = new Aluno();
        novo.setNome(request.nome());
        novo.setEmail(request.email());
        novo.setCpf(request.cpf());
        novo.setTelefone(request.telefone());
        novo.setDataNascimento(request.dataNascimento());
        novo.setObjetivo(request.objetivo());
        novo.setAnamnese(request.anamnese());
        Planos plano = planosRepository.findById(request.planoId()).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
        novo.setPlano(plano);
        novo.setAtivo(true);
        novo.setTipoAluno(request.tipoAluno());
        repository.save(novo);
        return new AlunoResponse(novo.getId(),novo.getNome(), novo.getEmail(), novo.getPlano().getId(), novo.getTipoAluno(), novo.getObjetivo(), novo.getAnamnese(), novo.getAtivo());
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public AlunoResponse buscarPorId(Long id) {
        Optional<Aluno> procurado = repository.findById(id);
        if (procurado.isPresent()) {
            Aluno encontrado = procurado.get();
            return new AlunoResponse(encontrado.getId(), encontrado.getNome(), encontrado.getEmail(), encontrado.getPlano().getId(), encontrado.getTipoAluno(), encontrado.getObjetivo(), encontrado.getAnamnese(), encontrado.getAtivo());
        }
        return null;
        
    }

    @Transactional
    public AlunoResponse alterarAluno(AlterAlunoRequest request, Long id) {
        Aluno procurado = repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));
         if (request.nome() != null) procurado.setNome(request.nome());
         if (request.email() != null) procurado.setEmail(request.email());
         if (request.cpf() != null) procurado.setCpf(request.cpf());
         if (request.dataNascimento() != null) procurado.setDataNascimento(request.dataNascimento());
         if (request.endereco() != null) procurado.setEndereco(request.endereco());
         if (request.tipoAluno() != null) procurado.setTipoAluno(request.tipoAluno());
         if (request.telefone() != null) procurado.setTelefone(request.telefone());
         if (request.planoId() != null) {
             Planos plano = planosRepository.findById(request.planoId()).orElseThrow(() -> new RuntimeException("Planio não encontrado"));
             procurado.setPlano(plano);
         }
         if (request.objetivo() != null) procurado.setObjetivo(request.objetivo());
         if (request.anamnese() != null) procurado.setAnamnese(request.anamnese());
         repository.save(procurado);
         return new AlunoResponse(procurado.getId(), procurado.getNome(), procurado.getEmail(), procurado.getPlano().getId(), procurado.getTipoAluno(), procurado.getObjetivo(), procurado.getAnamnese(), procurado.getAtivo());
    }

    @Transactional
    public AlunoResponse remover(Long id) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        if (aluno.getAtivo()) {
            aluno.setAtivo(false);
        }
        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getPlano().getId(), aluno.getTipoAluno(), aluno.getObjetivo(), aluno.getAnamnese(), aluno.getAtivo());
    }

    @Transactional
    public AlunoResponse recuperarAluno(Long id) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        if (!aluno.getAtivo()) {
            aluno.setAtivo(true);
        }
        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getPlano().getId(), aluno.getTipoAluno(), aluno.getObjetivo(), aluno.getAnamnese(), aluno.getAtivo());
    }

    public List<Aluno> listarAtivos() {
        return repository.findByAtivoTrue();
    }
}
