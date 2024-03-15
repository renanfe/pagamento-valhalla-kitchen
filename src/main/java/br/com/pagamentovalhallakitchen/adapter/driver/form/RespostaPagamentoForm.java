package br.com.pagamentovalhallakitchen.adapter.driver.form;

import br.com.pagamentovalhallakitchen.core.domain.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class RespostaPagamentoForm {

    private Long id;
    private String status;

}
