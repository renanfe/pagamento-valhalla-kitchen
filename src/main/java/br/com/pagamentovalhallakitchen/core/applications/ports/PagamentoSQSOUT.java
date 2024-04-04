package br.com.pagamentovalhallakitchen.core.applications.ports;

import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;

public interface PagamentoSQSOUT {

    void publicarRetornoPagamento(String retornoPagamentoForm);

}
