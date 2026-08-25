package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.PagamentoAluno;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoAlunoRepository extends JpaRepository<PagamentoAluno, Long> {

}
