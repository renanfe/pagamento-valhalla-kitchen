package br.com.apivalhallakitchen.core.applications.ports;

import br.com.apivalhallakitchen.core.domain.Pagamento;

import java.util.Optional;

public interface PagamentoRepository {

    Optional<Pagamento> buscarPagamento(Long id);
    Pagamento salvarPagamento(Pagamento pagamento);

}
