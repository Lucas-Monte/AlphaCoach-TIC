package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.dto.response.MatriculaResponse;
import br.com.alphacoach.app.model.Matricula;
import br.com.alphacoach.app.model.enums.StatusMatricula;
import br.com.alphacoach.app.model.enums.StatusParcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByStatusMatricula(StatusMatricula statusMatricula);
    boolean existsByAlunoIdAndPlanoIdAndStatusMatricula(Long alunoId, Long matriculaId, StatusMatricula statusMatricula);
}
