package br.com.pagamentovalhallakitchen.adapter.driver.form;

import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PedidoGeradoForm {

    private Long pedidoId;
    private UUID clienteId;
    private TipoPagamento tipoPagamento;
    private BigDecimal valor;

}
