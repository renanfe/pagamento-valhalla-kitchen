package br.com.pagamentovalhallakitchen.core.applications.ports;

import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {

    Optional<Pagamento> buscarPagamento(Long id);
    Pagamento salvarPagamento(Pagamento pagamento);
    Integer removerPagamentoDoCliente(UUID id);
}
