package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.AlunosTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
