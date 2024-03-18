package br.com.pagamentovalhallakitchen.messaging;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.messaging.PagamentoSQSINImpl;
import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoService;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSIN;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoSQSINImplTest {

    private PagamentoSQSIN pagamentoSQSIN;
    @Mock
    PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        this.pagamentoSQSIN = new PagamentoSQSINImpl(pagamentoService);
    }

    @Test
    void quandoEuReceboUmaConfirmaçãoDePagamento_entaoDevoProcessarOPagamentoComSucesso(){
        doReturn(PagamentoHelper.buildPagamento()).when(pagamentoService).criarPagamento(any());
        this.pagamentoSQSIN.receberMensagem(PagamentoHelper.buildMessage());
        verify(this.pagamentoService, times(1)).criarPagamento(any());
    }
}
