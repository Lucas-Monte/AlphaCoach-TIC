package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Exercicios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciciosRepository extends JpaRepository<Exercicios, Long> {
    public boolean existsByNome(String nome);
    public List<Exercicios> findByAtivoTrue();
}
