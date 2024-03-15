package br.com.pagamentovalhallakitchen.adapter.driver.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RespostaPagamentoForm {

    private Long id;
    private String status;

}
