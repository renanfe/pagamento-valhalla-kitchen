package br.com.pagamentovalhallakitchen.adapter.driven.infra.jpa;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepositoryJpa extends JpaRepository<PagamentoEntity, Long> {
}
