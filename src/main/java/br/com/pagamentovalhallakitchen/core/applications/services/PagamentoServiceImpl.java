package br.com.pagamentovalhallakitchen.core.applications.services;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoService;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoWebhookForm;
import br.com.pagamentovalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUT;
import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoSQSOUT pagamentoSQSOUT;

    public PagamentoServiceImpl (PagamentoRepository pagamentoRepository, PagamentoSQSOUT pagamentoSQSOUT) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoSQSOUT = pagamentoSQSOUT;
    }

    public Pagamento criarPagamento(PedidoGeradoForm pedidoGeradoForm){
        Pagamento pagamento = PagamentoMapper.pedidoGeradoFormToPagamento(pedidoGeradoForm);
        pagamento.setStatus(Status.PENDENTE);
        pagamento = pagamentoRepository.salvarPagamento(pagamento);
        log.info(pagamento.getId());
        log.info("Pagamento referente ao pedido: {} salvo com sucesso.", pedidoGeradoForm.getPedidoId());
        return pagamento;
    }

    public Optional<Pagamento> buscarPagamentoPorId(Long id) {
        return pagamentoRepository.buscarPagamento(id);
    }

    public Optional<Pagamento> cancelarPagamento(Long id) {
        return buscarPagamentoPorId(id)
                .map(this::cancelarPagamentoStatus)
                .map(pagamentoRepository::salvarPagamento);
    }

    private Pagamento cancelarPagamentoStatus(Pagamento pagamento) {
        pagamento.setStatus(Status.CANCELADO);
        return pagamento;
    }

    public Optional<Integer> removerPagamentoDoCliente(UUID id) {
        return Optional.of(pagamentoRepository.removerPagamentoDoCliente(id));
    }

    @Transactional
    public Pagamento processarPagamento (RetornoWebhookForm retornoWebhookForm) {
        return buscarPagamentoPorId(retornoWebhookForm.getId())
                .map(pagamento -> aprovarOuReprovarPagamento(pagamento, retornoWebhookForm.getStatus(), retornoWebhookForm.getMotivo()))
                .map(pagamentoRepository::salvarPagamento)
                .map(this::enviarMensagem)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    private Pagamento aprovarOuReprovarPagamento(Pagamento pagamento, Status status, String motivo) {
        switch (status) {
            case CONCLUIDO:
                pagamento.setStatus(Status.CONCLUIDO);
                pagamento.setMotivo(motivo);
                break;
            case CANCELADO:
                pagamento.setStatus(Status.CANCELADO);
                pagamento.setMotivo(motivo);
                break;
            default:
                pagamento.setStatus(Status.PENDENTE);
                break;
        }
        return pagamento;
    }

    private Pagamento enviarMensagem(Pagamento pagamento) {
        this.pagamentoSQSOUT.publicarRetornoPagamento(PagamentoMapper.pagamentoToRetornoPagamentoForm(pagamento));
        log.info("Retorno do pagamento id {} enviada para fila.", pagamento.getId());
        return pagamento;
    }
}
