package br.com.pagamentovalhallakitchen.controller;

import br.com.pagamentovalhallakitchen.adapter.driver.PagamentoController;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoWebhookForm;
import br.com.pagamentovalhallakitchen.config.SecurityConfig;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PagamentoController.class)
@ContextConfiguration(classes = {SecurityConfig.class, PagamentoController.class})
class PagamentoControllerTest{

    @MockBean
    private PagamentoServiceImpl pagamentoService;

    @Autowired
    private MockMvc mvc;

    @Test
    void quandoEuCadastroUmPagamento_entaoOPagamentoDeveSerRetornado() throws Exception {
        PedidoGeradoForm pagamentoForm = PagamentoHelper.buildPagamentoForm();
        Pagamento pagamento = PagamentoHelper.buildPagamento();
        when(pagamentoService.criarPagamento(any(PedidoGeradoForm.class))).thenReturn(pagamento);
        mvc.perform(
                        post("/v1/pagamentos")
                                .content(new ObjectMapper().writeValueAsString(pagamentoForm))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(pagamentoService, times(1)).criarPagamento(any(PedidoGeradoForm.class));
    }

    @Test
    void quandoEuProcessoUmPagamento_entaoOPagamentoDeveSerConcluidoComSucesso() throws Exception {
        RetornoWebhookForm retornoWebhookForm = PagamentoHelper.buildRetornoWebHookSucesso();
        Pagamento pagamento = PagamentoHelper.buildPagamento();
        when(pagamentoService.processarPagamento(any(RetornoWebhookForm.class))).thenReturn(pagamento);
        mvc.perform(
                post("/v1/pagamentos/webhook")
                    .content(new ObjectMapper().writeValueAsString(retornoWebhookForm))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(pagamentoService, times(1)).processarPagamento(any(RetornoWebhookForm.class));
    }

    @Test
    void quandoEuProcessoUmPagamento_entaoOPagamentoDeveSerCancelado() throws Exception {
        RetornoWebhookForm retornoWebhookForm = PagamentoHelper.buildRetornoWebHookCancelado();
        Pagamento pagamento = PagamentoHelper.buildPagamento();
        when(pagamentoService.processarPagamento(any(RetornoWebhookForm.class))).thenReturn(pagamento);
        mvc.perform(
                        post("/v1/pagamentos/webhook")
                                .content(new ObjectMapper().writeValueAsString(retornoWebhookForm))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(pagamentoService, times(1)).processarPagamento(any(RetornoWebhookForm.class));
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
