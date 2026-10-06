package br.com.alphacoach.app.repository;

import br.com.alphacoach.app.model.PagamentoParcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoParcelaRepository extends JpaRepository<PagamentoParcela, Long> {
}
