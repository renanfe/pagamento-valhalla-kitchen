package br.com.pagamentovalhallakitchen.utils;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoWebhookForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.operations.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class PagamentoHelper {
    public static String buildPagamentoForm(){
        PedidoGeradoForm pedidoGeradoForm = PedidoGeradoForm.builder()
                .clienteId(UUID.randomUUID())
                .pedidoId(gerarLong())
                .tipoPagamento(TipoPagamento.CREDITO)
                .valor(gerarBigDecimal())
                .build();
        return new Gson().toJson(pedidoGeradoForm, PedidoGeradoForm.class);
    }

    public static Pagamento buildPagamento(){
        return Pagamento.builder()
                .id(gerarLong())
                .clienteId(UUID.randomUUID())
                .pedidoId(gerarLong())
                .tipoPagamento(TipoPagamento.CREDITO)
                .valor(gerarBigDecimal())
                .build();
    }

    public static long gerarLong() {
        return new Random().nextLong();
    }

    public static BigDecimal gerarBigDecimal() {
        return BigDecimal.ONE;
    }

    public static PagamentoEntity buildPagamentoEntity () {
        return PagamentoEntity.builder()
                .id(gerarLong())
                .clienteId(UUID.randomUUID())
                .pedidoId(gerarLong())
                .tipoPagamento(TipoPagamento.CREDITO)
                .valor(gerarBigDecimal())
                .build();
    }

    public static RetornoPagamentoForm buildRespostaPagamentoFormConcluido () {
        return RetornoPagamentoForm.builder()
                .pedidoId(gerarLong())
                .statusRetorno(Status.CONCLUIDO)
                .build();
    }

    public static RetornoPagamentoForm buildRespostaPagamentoFormCancelado () {
        return RetornoPagamentoForm.builder()
                .pedidoId(gerarLong())
                .statusRetorno(Status.CANCELADO)
                .build();
    }

    public static SendResult buildSendResult() {
        return new SendResult(UUID.randomUUID(), "teste.com.br", buildMessage(), null);
    }

    public static Message<String> buildMessage() {
        return new GenericMessage<String>(buildPagamentoForm());
    }

    public static RetornoWebhookForm buildRetornoWebHookSucesso () {
        return RetornoWebhookForm.builder()
                .id(gerarLong())
                .status(Status.CONCLUIDO)
                .motivo("CONCLUIDO")
                .build();
    }

    public static RetornoWebhookForm buildRetornoWebHookCancelado () {
        return RetornoWebhookForm.builder()
                .id(gerarLong())
                .status(Status.CONCLUIDO)
                .motivo("CANCELADO")
                .build();

    }

    public static String buildRetornoPagamentoForm () {
        RetornoPagamentoForm retornoPagamentoForm = RetornoPagamentoForm.builder()
                .pedidoId(gerarLong())
                .statusRetorno(Status.CONCLUIDO)
                .motivo("teste")
                .build();
        return new Gson().toJson(retornoPagamentoForm, RetornoPagamentoForm.class);
    }
}
