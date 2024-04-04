package br.com.pagamentovalhallakitchen.core.applications.ports;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

public interface PagamentoSQSIN {

    void receberMensagem(@Payload Message<String> message);

}
