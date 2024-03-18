package br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging;

import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUTAdapter;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PagamentoSQSOUT implements PagamentoSQSOUTAdapter {

    @Autowired
    private SqsTemplate SqsTemplate;

    @Value("${queue.retorno-pagamento}")
    private String queueConfirmarPagamento;

    @Override
    public void publicarRetornoPagamento(Pagamento pagamento) {
        SqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue(queueConfirmarPagamento)
                                .payload(pagamento)
                );
    }

}
