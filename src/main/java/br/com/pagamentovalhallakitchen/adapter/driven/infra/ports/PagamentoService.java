package br.com.pagamentovalhallakitchen.adapter.driven.infra.ports;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

public interface PagamentoService {

    public Pagamento criarPagamento(PedidoGeradoForm pagamentoForm);

}
