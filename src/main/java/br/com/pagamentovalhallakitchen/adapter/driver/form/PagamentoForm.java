package br.com.pagamentovalhallakitchen.adapter.driver.form;

import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PagamentoForm {

    private UUID clienteId;
    private Long pedidoId;
    private TipoPagamento tipoPagamento;
    private BigDecimal preco;
    private Status status;

}
