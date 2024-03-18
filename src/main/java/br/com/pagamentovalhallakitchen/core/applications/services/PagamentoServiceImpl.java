package br.com.pagamentovalhallakitchen.core.applications.services;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoService;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RespostaPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUT;
import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoSQSOUT pagamentoSQSOUT;

    public PagamentoServiceImpl (PagamentoRepository pagamentoRepository, PagamentoSQSOUT pagamentoSQSOUT) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoSQSOUT = pagamentoSQSOUT;
    }

    public Pagamento criarPagamento(PagamentoForm pagamentoForm){
        Pagamento pagamento = PagamentoMapper.pagamentoFormToPagamento(pagamentoForm);
        pagamento.setStatus(Status.PENDENTE);
        pagamento = pagamentoRepository.salvarPagamento(pagamento);
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
    public Pagamento processarPagamento (RespostaPagamentoForm respostaPagamentoForm) {
        return buscarPagamentoPorId(respostaPagamentoForm.getId())
                .map(pagamento -> aprovarOuReprovarPagamento(pagamento, respostaPagamentoForm.getStatus()))
                .map(pagamentoRepository::salvarPagamento)
                .map(this::enviarMensagem)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    private Pagamento aprovarOuReprovarPagamento(Pagamento pagamento, String status) {
        switch (status) {
            case "CONCLUIDO":
                pagamento.setStatus(Status.CONCLUIDO);
                break;
            case "CANCELADO":
                pagamento.setStatus(Status.CANCELADO);
                break;
            default:
                pagamento.setStatus(Status.PENDENTE);
                break;
        }
        return pagamento;
    }

    private Pagamento enviarMensagem(Pagamento pagamento) {
        this.pagamentoSQSOUT.publicarRetornoPagamento(pagamento);
        return pagamento;
    }
}
