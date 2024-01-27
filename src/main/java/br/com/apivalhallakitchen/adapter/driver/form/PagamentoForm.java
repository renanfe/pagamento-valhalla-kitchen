package br.com.apivalhallakitchen.adapter.driver.form;

import br.com.apivalhallakitchen.core.domain.TipoPagamento;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class PagamentoForm {

    private UUID clienteId;
    private Long pedidoId;
    private TipoPagamento tipoPagamento;
    private BigDecimal preco;

}
