package br.com.pagamentovalhallakitchen.service;

import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepositoryAdapter;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUTAdapter;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoService;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {
    private PagamentoService pagamentoService;
    @Mock
    private PagamentoRepositoryAdapter pagamentoRepository;

    @Mock
    private PagamentoSQSOUTAdapter pagamentoSQSOUTAdapter;

    @BeforeEach
    void setUp() {
        pagamentoService = new PagamentoService(pagamentoRepository, pagamentoSQSOUTAdapter);
    }

    @Test
    void quandoEuCrioUmPagamento_entaoDeveRetornarPagamento(){
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.criarPagamento(PagamentoHelper.buildPagamentoForm());
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
    }

    @Test
    void quandoEuProcessoUmPagamento_entaoDeveRetornarPagamentoComSucesso(){
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.processarPagamento(PagamentoHelper.buildRespostaPagamentoFormConcluido());
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
    }

    @Test
    void quandoEuProcessoUmPagamento_entaoDeveRetornarPagamentoSemSucesso(){
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.processarPagamento(PagamentoHelper.buildRespostaPagamentoFormCancelado());
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
    }

    @Test
    void quandoEuBuscoOPagamento_entaoDeveRetornarPagamento(){
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        Optional<Pagamento> pagamento = pagamentoService.buscarPagamentoPorId(PagamentoHelper.gerarLong());
        assertTrue(pagamento.isPresent());
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
    }

    @Test
    void quandoEuCanceloOPagamento_entaoDeveRetornarPagamentoCancelado(){
        Pagamento pagamento = PagamentoHelper.buildPagamento();
        doReturn(Optional.of(pagamento)).when(pagamentoRepository).buscarPagamento(any(Long.class));
        doReturn(pagamento).when(pagamentoRepository).salvarPagamento(any(Pagamento.class));
        Optional<Pagamento> optPagamento = pagamentoService.cancelarPagamento(PagamentoHelper.gerarLong());
        assertTrue(optPagamento.isPresent());
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
    }

    @Test
    void quandoEuCanceloOPagamentoDoCliente_entaoDevoCancelarOPagamentoERetornarQuantidade(){
        doReturn(1).when(pagamentoRepository).removerPagamentoDoCliente(any(UUID.class));
        Optional<Integer> optPagamento = pagamentoService.removerPagamentoDoCliente(UUID.randomUUID());
        assertTrue(optPagamento.isPresent());
        verify(pagamentoRepository, times(1)).removerPagamentoDoCliente(any(UUID.class));
    }

}
