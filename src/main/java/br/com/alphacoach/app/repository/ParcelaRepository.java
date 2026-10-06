package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
}
