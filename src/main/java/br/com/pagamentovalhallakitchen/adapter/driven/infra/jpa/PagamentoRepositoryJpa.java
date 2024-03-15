package br.com.pagamentovalhallakitchen.adapter.driven.infra.jpa;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PagamentoRepositoryJpa extends JpaRepository<PagamentoEntity, Long> {
    @Modifying
    @Query("delete from PagamentoEntity p where p.clienteId=:clienteId")
    int deleteByClienteId(@Param("clienteId") UUID clienteId);
}
