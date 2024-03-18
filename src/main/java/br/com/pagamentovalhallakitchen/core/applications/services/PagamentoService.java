package br.com.pagamentovalhallakitchen.core.applications.services;

import br.com.pagamentovalhallakitchen.adapter.driven.infra.ports.PagamentoServiceAdapter;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RespostaPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepositoryAdapter;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoSQSOUTAdapter;
import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoService implements PagamentoServiceAdapter {

    private final PagamentoRepositoryAdapter pagamentoRepository;

    private final PagamentoSQSOUTAdapter pagamentoSQSOUT;

    public PagamentoService(PagamentoRepositoryAdapter pagamentoRepository, PagamentoSQSOUTAdapter pagamentoSQSOUT) {
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
