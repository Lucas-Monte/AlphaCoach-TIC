package br.com.alphacoach.app.service;

import br.com.alphacoach.app.dto.request.ExercicioRequest;
import br.com.alphacoach.app.dto.response.ExercicioResponse;
import br.com.alphacoach.app.model.Exercicios;
import br.com.alphacoach.app.repository.ExercicioTreinoRepository;
import br.com.alphacoach.app.repository.ExerciciosRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciciosService {
    private ExerciciosRepository exerciciosRepository;
    private ExercicioTreinoRepository exercicioTreinoRepository;

    public ExerciciosService(ExerciciosRepository exerciciosRepository) {
        this.exerciciosRepository = exerciciosRepository;
    }

    @Transactional
    public ExercicioResponse criar(ExercicioRequest request) {
        if (exerciciosRepository.existsByNome(request.nome())) {
            throw new IllegalArgumentException("Exercício já cadastrado!");
        }

        Exercicios novo = new Exercicios();
        novo.setNome(request.nome());
        novo.setDescricao(request.descricao());
        novo.setAtivo(true);
        if (novo.validarLink(request.link())) {
            novo.setLinkVideo(request.link());
        }
        exerciciosRepository.save(novo);
        return new ExercicioResponse(novo.getId(), novo.getNome(), novo.getDescricao(), novo.getLinkVideo(), novo.getAtivo());
    }

    public List<Exercicios> listar() {
        return exerciciosRepository.findAll();
    }

    public ExercicioResponse encontrarPorId(Long id) {
        Optional<Exercicios> procurado = exerciciosRepository.findById(id);
        if (procurado.isPresent()) {
            Exercicios encontrado = procurado.get();
            return new ExercicioResponse(encontrado.getId(), encontrado.getNome(), encontrado.getDescricao(), encontrado.getLinkVideo(), encontrado.getAtivo());
        }
        return null;
    }

    @Transactional
    public ExercicioResponse alterar(ExercicioRequest request, Long id) {
        Exercicios procurado = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado!"));
        if (request.nome() != null) procurado.setNome(request.nome());
        if (request.descricao() != null) procurado.setDescricao(request.descricao());
        if (request.link() != null) {
            if (procurado.validarLink(request.link())) {
                procurado.setLinkVideo(request.link());
            }
        }
        exerciciosRepository.save(procurado);
        return new ExercicioResponse(procurado.getId(), procurado.getNome(), procurado.getDescricao(), procurado.getLinkVideo(), procurado.getAtivo());
    }

    @Transactional
    public ExercicioResponse remover(Long id) {
        Exercicios procurado = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));
        if (exercicioTreinoRepository.existsByExercicio_Id(procurado.getId())) {
            throw new IllegalArgumentException("Exercício vinculado a um treino");
        }
        procurado.desativar();
        return new ExercicioResponse(procurado.getId(), procurado.getNome(), procurado.getDescricao(), procurado.getLinkVideo(), procurado.getAtivo());
    }

    @Transactional
    public ExercicioResponse recuperarExercicio(Long id) {
        Exercicios procurado = exerciciosRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));
        procurado.ativar();
        return new ExercicioResponse(procurado.getId(), procurado.getNome(), procurado.getDescricao(), procurado.getLinkVideo(), procurado.getAtivo());
    }

    public List<Exercicios> listarAtivos() {
        return exerciciosRepository.findByAtivoTrue();
    }
}
