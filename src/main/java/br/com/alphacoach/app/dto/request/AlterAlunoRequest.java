package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.enums.AlunosTypes;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record AlterAlunoRequest (String  nome,
                                 String email,
                                 String cpf,
                                 @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                                 String telefone,
                                 Long planoId,
                                 String objetivo,
                                 String anamnese,
                                 AlunosTypes tipoAluno,
                                 String endereco) {
}
