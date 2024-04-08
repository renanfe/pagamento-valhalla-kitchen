package br.com.pagamentovalhallakitchen.adapter.utils.mappers;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoWebhookForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import com.google.gson.Gson;

public class PagamentoMapper {

    PagamentoMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Pagamento pedidoGeradoFormToPagamento (PedidoGeradoForm pedidoGeradoForm){
        return Pagamento.builder()
                .clienteId(pedidoGeradoForm.getClienteId())
                .pedidoId(pedidoGeradoForm.getPedidoId())
                .tipoPagamento(pedidoGeradoForm.getTipoPagamento())
                .valor(pedidoGeradoForm.getValor())
                .build();
    }

    public static PagamentoEntity pagamentoToEntity(Pagamento pagamento){
        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .pedidoId(pagamento.getPedidoId())
                .tipoPagamento(pagamento.getTipoPagamento())
                .valor(pagamento.getValor())
                .dataTransacao(pagamento.getDataTransacao())
                .status(pagamento.getStatus())
                .motivo(pagamento.getMotivo())
                .build();
    }

    public static Pagamento pagamentoEntityToPagamento(PagamentoEntity pagamentoEntity){
        return Pagamento.builder()
                .id(pagamentoEntity.getId())
                .clienteId(pagamentoEntity.getClienteId())
                .pedidoId(pagamentoEntity.getPedidoId())
                .tipoPagamento(pagamentoEntity.getTipoPagamento())
                .valor(pagamentoEntity.getValor())
                .status(pagamentoEntity.getStatus())
                .motivo(pagamentoEntity.getMotivo())
                .build();
    }

    public static String pagamentoToRetornoPagamentoForm (Pagamento pagamento) {
        return new Gson().toJson(RetornoPagamentoForm.builder().pedidoId(pagamento.getPedidoId())
                        .statusRetorno(pagamento.getStatus())
                        .motivo(pagamento.getMotivo()).build()
                , RetornoPagamentoForm.class);
    }
}
