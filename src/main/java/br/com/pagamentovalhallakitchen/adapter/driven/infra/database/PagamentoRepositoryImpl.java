package br.com.pagamentovalhallakitchen.adapter.driven.infra.database;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.jpa.PagamentoRepositoryJpa;
import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final PagamentoRepositoryJpa pagamentoRepositoryJpa;

    public PagamentoRepositoryImpl (PagamentoRepositoryJpa pagamentoRepositoryJpa) {
        this.pagamentoRepositoryJpa = pagamentoRepositoryJpa;
    }

    @Override
    public Optional<Pagamento> buscarPagamento(Long id) {
        return pagamentoRepositoryJpa.findById(id).map(PagamentoMapper::pagamentoEntityToPagamento);
    }

    @Override
    @Transactional
    public Pagamento salvarPagamento(Pagamento pagamento) {
        PagamentoEntity pagamentoEntity = pagamentoRepositoryJpa.save(PagamentoMapper.pagamentoToEntity(pagamento));
        return PagamentoMapper.pagamentoEntityToPagamento(pagamentoEntity);
    }

    @Override
    @Transactional
    public Integer removerPagamentoDoCliente (UUID id) {
        return pagamentoRepositoryJpa.deleteByClienteId(id);
    }
}
