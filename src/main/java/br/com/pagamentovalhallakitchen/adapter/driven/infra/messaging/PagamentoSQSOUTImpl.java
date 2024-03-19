package br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging;

import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUT;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PagamentoSQSOUTImpl implements PagamentoSQSOUT {

    private SqsTemplate sqsTemplate;

    @Value("${queue.retorno-pagamento}")
    private String queueConfirmarPagamento;

    public PagamentoSQSOUTImpl (SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void publicarRetornoPagamento(RetornoPagamentoForm retornoPagamentoForm) {
        this.sqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue(queueConfirmarPagamento)
                                .payload(retornoPagamentoForm)
                );
    }

}
