package br.com.apivalhallakitchen.adapter.driven.infra;

import br.com.apivalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.apivalhallakitchen.adapter.driven.infra.jpa.PagamentoRepositoryJpa;
import br.com.apivalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.apivalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.apivalhallakitchen.core.domain.Pagamento;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final PagamentoRepositoryJpa pagamentoRepositoryJpa;

    public PagamentoRepositoryImpl(PagamentoRepositoryJpa pagamentoRepositoryJpa) {
        this.pagamentoRepositoryJpa = pagamentoRepositoryJpa;
    }

    @Override
    public Optional<Pagamento> buscarPagamento(Long id) {
        return pagamentoRepositoryJpa.findById(id).map(PagamentoMapper::pagamentoEntityToPagamento);
    }

    @Override
    public Pagamento salvarPagamento(Pagamento pagamento) {
        PagamentoEntity pagamentoEntity = pagamentoRepositoryJpa.save(PagamentoMapper.pagamentoToEntity(pagamento));
        return PagamentoMapper.pagamentoEntityToPagamento(pagamentoEntity);
    }

}
