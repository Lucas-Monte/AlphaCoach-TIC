package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    public boolean existsByCpf(String cpf);
    public List<Aluno> findByAtivoTrue();
}
