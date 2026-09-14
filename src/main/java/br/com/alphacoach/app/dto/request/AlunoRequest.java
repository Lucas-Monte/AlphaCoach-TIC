package br.com.alphacoach.app.dto.request;

import br.com.alphacoach.app.model.AlunosTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AlunoRequest(@NotEmpty(message = "Nome é obrigatório") String  nome,
                           @NotEmpty(message = "E-mail é obrigatório") String email,
                           @NotEmpty(message = "CPF é obrigatório") String cpf,
                           @NotNull(message = "Data de nascimento é obrigatório") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                           @NotEmpty(message = "Telefone é obrigatório") String telefone,
                           @NotNull(message = "Plano é obrigatório") Long planoId,
                           @NotEmpty(message = "Objetivo é obrigatório") String objetivo,
                           @NotEmpty(message = "Anamnese é obrigatório") String anamnese,
                           @NotNull(message = "Tipo de aluno é obrigatório")AlunosTypes tipoAluno,
                           String endereco) {
}
