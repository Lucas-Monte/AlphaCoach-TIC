package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Treinos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinosRepository extends JpaRepository<Treinos, Long> {
}
