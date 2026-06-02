package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Planos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanosRepository extends JpaRepository<Planos, Long> {
}
