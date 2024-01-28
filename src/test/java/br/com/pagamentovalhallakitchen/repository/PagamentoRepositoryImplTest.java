package br.com.pagamentovalhallakitchen.repository;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.PagamentoRepositoryImpl;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoRepositoryImplTest {

    @Mock
    private PagamentoRepositoryImpl pagamentoRepository;

    @Test
    public void quandoSalvoOPagamento_entaoDeveSalvarComSucesso() {
        Pagamento pagamento2Insert = PagamentoHelper.buildPagamento();
        when(pagamentoRepository.salvarPagamento(any(Pagamento.class))).thenReturn(PagamentoHelper.buildPagamento());
        Pagamento pagamento = pagamentoRepository.salvarPagamento(pagamento2Insert);
        assertNotNull(pagamento);
        verify(pagamentoRepository, times(1)).salvarPagamento(any(Pagamento.class));

    }

    @Test
    public void quandoBuscoPorUmPagamento_entaoRetornaUmPagamento() {
        Optional<Pagamento> pagamento2Insert = Optional.of(PagamentoHelper.buildPagamento());
        when(pagamentoRepository.buscarPagamento(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamento()));
        Optional<Pagamento> pagamento = pagamentoRepository.buscarPagamento(PagamentoHelper.gerarLong());
        assertTrue(pagamento.isPresent());
        verify(pagamentoRepository, times(1)).buscarPagamento(any(Long.class));

    }

}