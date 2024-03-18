package br.com.pagamentovalhallakitchen.core.applications.ports;

import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

public interface PagamentoSQSOUTAdapter {

    void publicarRetornoPagamento(Pagamento message);

}
