package br.com.pagamentovalhallakitchen.service;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUT;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoServiceImpl;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceImplTest {
    private PagamentoServiceImpl pagamentoService;
    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoSQSOUT pagamentoSQSOUTAdapter;

    @BeforeEach
    void setUp() {
        pagamentoService = new PagamentoServiceImpl(pagamentoRepository, pagamentoSQSOUTAdapter);
    }

    @Test
    void quandoEuCrioUmPagamento_entaoDeveRetornarPagamento(){
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.criarPagamento(new Gson().fromJson(PagamentoHelper.buildPagamentoForm(), PedidoGeradoForm.class));
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
    }

    @Test
    void quandoEuProcessoUmPagamento_entaoDeveRetornarPagamentoComSucesso(){
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.processarPagamento(PagamentoHelper.buildRetornoWebHookSucesso());
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));
    }

    @Test
    void quandoEuProcessoUmPagamento_entaoDeveRetornarPagamentoSemSucesso(){
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoService.processarPagamento(PagamentoHelper.buildRetornoWebHookCancelado());
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
