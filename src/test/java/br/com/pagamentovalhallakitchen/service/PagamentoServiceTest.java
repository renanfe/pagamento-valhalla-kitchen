package br.com.pagamentovalhallakitchen.service;

import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoService;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {
    private PagamentoService pagamentoService;
    @Mock
    private PagamentoRepository pagamentoRepository;

    @BeforeEach
    void setUp() {
        pagamentoService = new PagamentoService(pagamentoRepository);
    }
    @Test
    public void quandoEuRealizoOPagamento_entaoDeveRetornarPagamento(){
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.efetuarPagamento(PagamentoHelper.buildPagamentoForm());
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
    }

    @Test
    public void quandoEuBuscoOPagamento_entaoDeveRetornarPagamento(){
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        Optional<Pagamento> pagamento = pagamentoService.buscarPagamentoPorId(PagamentoHelper.gerarLong());
        assertTrue(pagamento.isPresent());
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
    }

    @Test
    public void quandoEuCanceloOPagamento_entaoDeveRetornarPagamentoCancelado(){
        Pagamento pagamento = PagamentoHelper.buildPagamento();
        doReturn(Optional.of(pagamento)).when(pagamentoRepository).buscarPagamento(any(Long.class));
        doReturn(pagamento).when(pagamentoRepository).salvarPagamento(any(Pagamento.class));
        Optional<Pagamento> optPagamento = pagamentoService.cancelarPagamento(PagamentoHelper.gerarLong());
        assertTrue(optPagamento.isPresent());
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
    }
}
