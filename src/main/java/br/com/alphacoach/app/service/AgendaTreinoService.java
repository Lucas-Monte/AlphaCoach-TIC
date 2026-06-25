package br.com.alphacoach.app.service;

import br.com.alphacoach.app.model.AgendaTreino;
import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.repository.AgendaTreinoRepository;
import br.com.alphacoach.app.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaTreinoService {
    private AgendaTreinoRepository agendaRepository;
    private AlunoRepository alunoRepository;

    public AgendaTreinoService(AgendaTreinoRepository agendaRepository, AlunoRepository alunoRepository) {
        this.agendaRepository = agendaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public AgendaTreino criar(AgendaTreino agenda) {
        if (agendaRepository.existsByAlunoAndData(agenda.getAluno(), agenda.getData())) {
            throw new IllegalArgumentException("Agenda já criada para esse aluno");
        }
        if (agenda.getAluno() != null && agenda.getAluno().getId() != null) {
            Aluno aluno = alunoRepository.findById(agenda.getAluno().getId()).orElseThrow(() -> new RuntimeException("Aluno não cadastrado"));
            agenda.setAluno(aluno);
        } else {
            throw new IllegalArgumentException("Aluno é obrigatório ser informado!");
        }

        return agendaRepository.save(agenda);
    }

    public List<AgendaTreino> listar() {
        return agendaRepository.findAll();
    }

    public Optional<AgendaTreino> encontrarPorId(Long id) {
        return agendaRepository.findById(id);
    }

    @Transactional
    public AgendaTreino alterar(AgendaTreino novo, Long id) {
        AgendaTreino agenda = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula não encontrada!"));
        if (novo.getAluno() != null) agenda.setAluno(novo.getAluno());
        if (novo.getData() != null) agenda.setData(novo.getData());
        if (novo.isCheckIn() != null) agenda.setCheckIn(novo.isCheckIn());

        return agendaRepository.save(agenda);
    }

    @Transactional
    public boolean remover(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
