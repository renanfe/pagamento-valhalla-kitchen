package br.com.pagamentovalhallakitchen.adapter.driven.infra.entity;

import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.TipoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Entity()
@Table(name = "pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "pagamento_generator", sequenceName = "pagamento_id_seq" , allocationSize = 1)
public class PagamentoEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_generator")
        @Column(name = "id")
        private Long id;

        @Column(name = "clienteId")
        private UUID clienteId;

        @Column(name = "pedidoId")
        private Long pedidoId;

        @Column(name = "tipoPagamento")
        @Enumerated(EnumType.STRING)
        private TipoPagamento tipoPagamento;

        @Column(name = "preco")
        private BigDecimal preco;

        @Column(name = "dataTransacao")
        private Date dataTransacao;

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        @Builder.Default
        private Status status = Status.PENDENTE;
}
