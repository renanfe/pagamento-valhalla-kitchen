package br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoService;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSIN;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class PagamentoSQSINImpl implements PagamentoSQSIN {

    private PagamentoService pagamentoService;

    public PagamentoSQSINImpl (PagamentoService pagamentoServiceImpl){
        this.pagamentoService = pagamentoServiceImpl;
    }

    @Override
    @SqsListener(value = "${queue.pedido-gerado}")
    public void receberMensagem(Message<PagamentoForm> message) {
        this.pagamentoService.criarPagamento(message.getPayload());
    }
}
