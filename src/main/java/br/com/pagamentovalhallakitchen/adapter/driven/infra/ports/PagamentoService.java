package br.com.pagamentovalhallakitchen.adapter.driven.infra.ports;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

public interface PagamentoService {

    public Pagamento criarPagamento(PagamentoForm pagamentoForm);

}