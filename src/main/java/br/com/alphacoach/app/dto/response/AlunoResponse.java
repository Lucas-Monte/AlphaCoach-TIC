package br.com.alphacoach.app.dto.response;
import br.com.alphacoach.app.model.enums.AlunosTypes;


public record AlunoResponse(Long id, String nome, String email, AlunosTypes tipoAluno, String objetivo, String anamnese, Boolean ativo) {
}
