package br.com.pagamentovalhallakitchen.core.applications.ports;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

public interface PagamentoSQSINAdapter {

    void receive(@Payload Message<PagamentoForm> message);

}
