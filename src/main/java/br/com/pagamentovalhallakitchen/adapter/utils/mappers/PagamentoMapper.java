package br.com.pagamentovalhallakitchen.adapter.utils.mappers;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.entity.PagamentoEntity;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

public class PagamentoMapper {

    PagamentoMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Pagamento pagamentoFormToPagamento(PagamentoForm pagamentoForm){
        return Pagamento.builder()
                .clienteId(pagamentoForm.getClienteId())
                .pedidoId(pagamentoForm.getPedidoId())
                .tipoPagamento(pagamentoForm.getTipoPagamento())
                .preco(pagamentoForm.getPreco())
                .build();
    }

    public static PagamentoEntity pagamentoToEntity(Pagamento pagamento){
        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .pedidoId(pagamento.getPedidoId())
                .tipoPagamento(pagamento.getTipoPagamento())
                .preco(pagamento.getPreco())
                .dataTransacao(pagamento.getDataTransacao())
                .status(pagamento.getStatus())
                .build();
    }

    public static Pagamento pagamentoEntityToPagamento(PagamentoEntity pagamentoEntity){
        return Pagamento.builder()
                .id(pagamentoEntity.getId())
                .clienteId(pagamentoEntity.getClienteId())
                .pedidoId(pagamentoEntity.getPedidoId())
                .tipoPagamento(pagamentoEntity.getTipoPagamento())
                .preco(pagamentoEntity.getPreco())
                .status(pagamentoEntity.getStatus())
                .build();
    }

}
