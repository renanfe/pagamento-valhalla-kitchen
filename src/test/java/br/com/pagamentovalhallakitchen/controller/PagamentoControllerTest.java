package br.com.pagamentovalhallakitchen.controller;

import br.com.pagamentovalhallakitchen.adapter.driver.PagamentoController;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest{

    @MockBean
    private PagamentoService pagamentoService;
    @Autowired
    private MockMvc mvc;
    @Test
    void quandoEuEfetuoUmPagamento_entaoOPagamentoDeveSerRetornado() throws Exception {
        PagamentoForm pagamentoForm = PagamentoHelper.buildPagamentoForm();
        Pagamento pagamento = PagamentoHelper.buildPagamento();
        when(pagamentoService.efetuarPagamento(any(PagamentoForm.class))).thenReturn(pagamento);
        mvc.perform(
                post("/v1/pagamentos")
                    .content(new ObjectMapper().writeValueAsString(pagamentoForm))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        verify(pagamentoService, times(1)).efetuarPagamento(any(PagamentoForm.class));
    }

    @Test
    void quandoBuscoUmPagamentoComId_EntaoRetornaAsInformacoesPagamento() throws Exception {
        Long id = PagamentoHelper.gerarLong();
        Optional<Pagamento> pagamento = Optional.of(PagamentoHelper.buildPagamento());
        when(pagamentoService.buscarPagamentoPorId(any(Long.class))).thenReturn(pagamento);
        mvc.perform(
                get("/v1/pagamentos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(pagamentoService, times(1)).buscarPagamentoPorId(any(Long.class));

    }

    @Test
    void quandoCanceloUmPagamento_entaoRetornaPagamentoComStatusCancelado() throws Exception {
        Long id = PagamentoHelper.gerarLong();
        Optional<Pagamento> pagamento = Optional.of(PagamentoHelper.buildPagamento());
        when(pagamentoService.cancelarPagamento(any(Long.class))).thenReturn(pagamento);
        mvc.perform(
                delete("/v1/pagamentos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(pagamentoService, times(1)).cancelarPagamento(any(Long.class));

    }

}
