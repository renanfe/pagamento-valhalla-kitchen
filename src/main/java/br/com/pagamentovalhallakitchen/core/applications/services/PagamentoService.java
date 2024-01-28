package br.com.pagamentovalhallakitchen.core.applications.services;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.pagamentovalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.pagamentovalhallakitchen.core.domain.Status;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }
    public Pagamento efetuarPagamento(PagamentoForm pagamentoForm){
        Pagamento pagamento = PagamentoMapper.pagamentoFormToPagamento(pagamentoForm);
        confirmarPagamento(pagamento);
        pagamento = pagamentoRepository.salvarPagamento(pagamento);
        return pagamento;
    }

    private void confirmarPagamento(Pagamento pagamento) {
        pagamento.setStatus(Status.CONCLUIDO);
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
}
