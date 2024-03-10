package br.com.pagamentovalhallakitchen.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class Pagamento {

    private Long id;
    private UUID clienteId;
    private Long pedidoId;
    private TipoPagamento tipoPagamento;
    private BigDecimal preco;

    @Builder.Default
    private Date dataTransacao = new Date();

    @Builder.Default
    @Setter
    private Status status = Status.PENDENTE;

}
