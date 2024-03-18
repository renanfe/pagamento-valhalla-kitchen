package br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoServiceAdapter;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSINAdapter;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class PagamentoSQSIN implements PagamentoSQSINAdapter {

    private PagamentoServiceAdapter pagamentoService;
    @Autowired
    private SqsTemplate SqsTemplate;

    public PagamentoSQSIN(PagamentoServiceAdapter pagamentoServiceAdapter){
        this.pagamentoService = pagamentoServiceAdapter;
    }

    @Override
    @SqsListener(value = "${queue.pedido-gerado}")
    public void receive(Message<PagamentoForm> message) {
        this.pagamentoService.criarPagamento(message.getPayload());
    }
}
