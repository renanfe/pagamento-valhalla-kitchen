package br.com.pagamentovalhallakitchen.adapter.driven.infra.entity;

import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity()
@Table(name = "pagamento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "pagamento_generator", sequenceName = "pagamento_id_seq" , allocationSize = 1)
public class PagamentoEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_generator")
        @Column(name = "id")
        private Long id;

        @Column(name = "pedido_id")
        private Long pedidoId;

        @Column(name = "cliente_id")
        private UUID clienteId;

        @Column(name = "tipo_pagamento")
        @Enumerated(EnumType.STRING)
        private TipoPagamento tipoPagamento;

        @Column(name = "valor")
        private BigDecimal valor;

        @Column(name = "data_transacao")
        private Date dataTransacao;

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        @Builder.Default
        private Status status = Status.PENDENTE;

        @Column(name = "motivo")
        private String motivo;
}
