package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.AgendaTreino;
import br.com.alphacoach.app.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public interface AgendaTreinoRepository extends JpaRepository<AgendaTreino, Long> {
    boolean existsByAlunoAndData(Aluno aluno, LocalDate data);
}
