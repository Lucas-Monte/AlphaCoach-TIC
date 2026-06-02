package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Exercicios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciciosRepository extends JpaRepository<Exercicios, Long> {
    boolean existsByNome(String nome);
}
