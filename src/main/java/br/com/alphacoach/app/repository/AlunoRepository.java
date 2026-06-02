package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    public boolean existsByCpf(String cpf);
}
