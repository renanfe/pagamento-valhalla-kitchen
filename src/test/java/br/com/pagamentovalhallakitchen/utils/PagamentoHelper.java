package br.com.pagamentovalhallakitchen.utils;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RespostaPagamentoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class PagamentoHelper {
    public static PagamentoForm buildPagamentoForm(){
        return PagamentoForm.builder()
                .clienteId(UUID.randomUUID())
                .pedidoId(gerarLong())
                .tipoPagamento(TipoPagamento.CREDITO)
                .preco(gerarBigDecimal())
                .build();
    }

    public static Pagamento buildPagamento(){
        return Pagamento.builder()
                .id(gerarLong())
                .clienteId(UUID.randomUUID())
                .pedidoId(gerarLong())
                .tipoPagamento(TipoPagamento.CREDITO)
                .preco(gerarBigDecimal())
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
                .preco(gerarBigDecimal())
                .build();
    }

    public static RespostaPagamentoForm buildRespostaPagamentoFormConcluido () {
        return RespostaPagamentoForm.builder()
                .id(gerarLong())
                .status("CONCLUIDO")
                .build();
    }

    public static RespostaPagamentoForm buildRespostaPagamentoFormCancelado () {
        return RespostaPagamentoForm.builder()
                .id(gerarLong())
                .status("CANCELADO")
                .build();
    }
}
