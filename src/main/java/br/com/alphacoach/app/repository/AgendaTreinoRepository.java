package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.AgendaTreino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendaTreinoRepository extends JpaRepository<AgendaTreino, Long> {
    boolean existsByAlunoIdAndData(Long alunoId, LocalDateTime data);
}
