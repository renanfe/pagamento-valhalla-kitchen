package br.com.pagamentovalhallakitchen.utils;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoWebhookForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;
import io.awspring.cloud.sqs.operations.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class PagamentoHelper {
    public static PedidoGeradoForm buildPagamentoForm(){
        return PedidoGeradoForm.builder()
                .clienteId(UUID.randomUUID())
                .pedidoId(gerarLong())
                .tipoPagamento(TipoPagamento.CREDITO)
                .valor(gerarBigDecimal())
                .build();
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

    public static Message<PedidoGeradoForm> buildMessage() {
        return new GenericMessage<PedidoGeradoForm>(buildPagamentoForm());
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

    public static RetornoPagamentoForm buildRetornoPagamentoForm () {
        return RetornoPagamentoForm.builder()
                .pedidoId(gerarLong())
                .statusRetorno(Status.CONCLUIDO)
                .motivo("teste")
                .build();
    }
}
