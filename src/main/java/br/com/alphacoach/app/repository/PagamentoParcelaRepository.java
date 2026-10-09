package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.PagamentoParcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoParcelaRepository extends JpaRepository<PagamentoParcela, Long> {
    Optional<PagamentoParcela> findByParcelaId(Long id);
}
