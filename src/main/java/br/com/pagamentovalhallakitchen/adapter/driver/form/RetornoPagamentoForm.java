package br.com.pagamentovalhallakitchen.adapter.driver.form;

import br.com.pagamentovalhallakitchen.core.domain.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetornoPagamentoForm {

    private Long pedidoId;
    private Status statusRetorno;
    private String motivo;

}
