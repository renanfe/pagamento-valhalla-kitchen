package br.com.pagamentovalhallakitchen.adapter.driver.form;

import br.com.pagamentovalhallakitchen.core.domain.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetornoWebhookForm {

    private Long id;
    private Status status;
    private String motivo;

}
