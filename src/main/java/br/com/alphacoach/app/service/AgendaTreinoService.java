package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.AgendaTreinoRequest;
import br.com.alphacoach.app.dto.response.AgendaTreinoResponse;
import br.com.alphacoach.app.model.AgendaTreino;
import br.com.alphacoach.app.model.Aluno;
import br.com.alphacoach.app.repository.AgendaTreinoRepository;
import br.com.alphacoach.app.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public AgendaTreinoResponse criar(AgendaTreinoRequest request) {
        if (agendaRepository.existsByAlunoIdAndData(request.alunoId(), request.dataEHorario())) {
            throw new IllegalArgumentException("Agenda já criada para esse aluno");
        }
        AgendaTreino agenda = new AgendaTreino();
        agenda.setData(request.dataEHorario());
        Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não cadastrado"));
        agenda.setAluno(aluno);
        agenda.setCheckIn(false);
        agendaRepository.save(agenda);

        return new AgendaTreinoResponse(agenda.getId(), agenda.getAluno().getId(), agenda.getData(), agenda.getCheckIn());
    }

    public List<AgendaTreino> listar() {
        return agendaRepository.findAll();
    }

    public AgendaTreinoResponse encontrarPorId(Long id) {
        Optional<AgendaTreino> procurado = agendaRepository.findById(id);
        if (procurado.isPresent()){
            AgendaTreino encontrado = procurado.get();
            return new AgendaTreinoResponse(encontrado.getId(), encontrado.getAluno().getId(), encontrado.getData(), encontrado.getCheckIn());
        }

        return null;
    }

    @Transactional
    public AgendaTreinoResponse alterar(AgendaTreinoRequest request, Long id) {
        AgendaTreino agenda = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula não encontrada!"));
        if (request.alunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            agenda.setAluno(aluno);
        }
        if (request.dataEHorario() != null) agenda.reagendar(request.dataEHorario());
        agendaRepository.save(agenda);
        return new AgendaTreinoResponse(agenda.getId(), agenda.getAluno().getId(), agenda.getData(), agenda.getCheckIn());
    }

    @Transactional
    public AgendaTreinoResponse fazerCheckIn(Long id) {
        AgendaTreino agenda = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula não encontrada!"));
        agenda.realizarCheckIn();
        agendaRepository.save(agenda);
        return new AgendaTreinoResponse(agenda.getId(), agenda.getAluno().getId(), agenda.getData(), agenda.getCheckIn());
    }

    @Transactional
    public AgendaTreinoResponse cancelarCheckIn(Long id) {
        AgendaTreino agenda = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula não encontrada!"));
        agenda.cancelarCheckIn();
        agendaRepository.save(agenda);
        return new AgendaTreinoResponse(agenda.getId(), agenda.getAluno().getId(), agenda.getData(), agenda.getCheckIn());
    }

    @Transactional
    public AgendaTreinoResponse reagendarAula(LocalDateTime novaData, Long id) {
        AgendaTreino agenda = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Agenda não encontrada!"));

        agenda.reagendar(novaData);
        return new AgendaTreinoResponse(agenda.getId(), agenda.getAluno().getId(), agenda.getData(), agenda.getCheckIn());
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
