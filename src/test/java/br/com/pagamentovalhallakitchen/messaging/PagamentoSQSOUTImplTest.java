package br.com.pagamentovalhallakitchen.messaging;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging.PagamentoSQSOUTImpl;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUT;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoSQSOUTImplTest {

    private PagamentoSQSOUT pagamentoSQSOUT;

    @Mock
    private SqsTemplate sqsTemplate;

    @BeforeEach
    void setUp() {
        this.pagamentoSQSOUT = new PagamentoSQSOUTImpl(sqsTemplate);
    }
    @Test
    void quandoEuReceboUmPagamento_entaoDevoEncaminharMensagemComSucesso(){
        doReturn(PagamentoHelper.buildSendResult()).when(sqsTemplate).send(any());
        this.pagamentoSQSOUT.publicarRetornoPagamento(PagamentoHelper.buildPagamento());
        verify(this.sqsTemplate, times(1)).send(any());
    }
}
