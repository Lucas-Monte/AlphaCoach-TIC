package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}
