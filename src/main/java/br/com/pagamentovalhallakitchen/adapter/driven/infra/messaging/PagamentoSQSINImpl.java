package br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoService;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSIN;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.annotation.SqsListener;
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
    public void receberMensagem(Message<String> message) {
        PedidoGeradoForm pedidoGeradoForm = new Gson().fromJson(message.getPayload(), PedidoGeradoForm.class);
        this.pagamentoService.criarPagamento(pedidoGeradoForm);
    }
}
