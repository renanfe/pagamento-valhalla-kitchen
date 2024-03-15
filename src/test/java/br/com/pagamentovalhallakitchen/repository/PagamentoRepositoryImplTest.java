package br.com.pagamentovalhallakitchen.repository;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.PagamentoRepositoryImpl;
import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.driven.infra.jpa.PagamentoRepositoryJpa;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoService;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoRepositoryImplTest {

    private PagamentoRepositoryImpl pagamentoRepository;
    @Mock
    private PagamentoRepositoryJpa pagamentoRepositoryJpa;

    @BeforeEach
    void setUp() {
        pagamentoRepository = new PagamentoRepositoryImpl(pagamentoRepositoryJpa);
    }

    @Test
    void quandoSalvoOPagamento_entaoDeveSalvarComSucesso() {
        Pagamento pagamento2Insert = PagamentoHelper.buildPagamento();
        when(pagamentoRepositoryJpa.save(any(PagamentoEntity.class))).thenReturn(PagamentoHelper.buildPagamentoEntity());
        Pagamento pagamento = pagamentoRepository.salvarPagamento(pagamento2Insert);
        assertNotNull(pagamento);
        verify(pagamentoRepositoryJpa, times(1)).save(any(PagamentoEntity.class));
    }

    @Test
    void quandoBuscoPorUmPagamento_entaoRetornaUmPagamento() {
        Optional<Pagamento> pagamento2Insert = Optional.of(PagamentoHelper.buildPagamento());
        when(pagamentoRepositoryJpa.findById(any(Long.class))).thenReturn(Optional.of(PagamentoHelper.buildPagamentoEntity()));
        Optional<Pagamento> pagamento = pagamentoRepository.buscarPagamento(PagamentoHelper.gerarLong());
        assertTrue(pagamento.isPresent());
        verify(pagamentoRepositoryJpa, times(1)).findById(any(Long.class));
    }

    @Test
    void quandoSolicitarRemoverPagamentoDoCliente_entaoRemoveOPagamento() {
        UUID clienteId = PagamentoHelper.buildPagamento().getClienteId();
        when(pagamentoRepositoryJpa.deleteByClienteId(any(UUID.class))).thenReturn(0);
        int clienteIdRetorno = pagamentoRepository.removerPagamentoDoCliente(clienteId);
        assertNotNull(clienteIdRetorno);
        verify(pagamentoRepositoryJpa, times(1)).deleteByClienteId(any(UUID.class));
    }

}