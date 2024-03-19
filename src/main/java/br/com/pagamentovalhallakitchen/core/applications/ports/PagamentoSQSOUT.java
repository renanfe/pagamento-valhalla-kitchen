package br.com.pagamentovalhallakitchen.core.applications.ports;

import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

public interface PagamentoSQSOUT {

    void publicarRetornoPagamento(RetornoPagamentoForm retornoPagamentoForm);

}
