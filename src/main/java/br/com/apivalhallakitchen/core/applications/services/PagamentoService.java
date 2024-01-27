package br.com.apivalhallakitchen.core.applications.services;

import br.com.apivalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.apivalhallakitchen.adapter.utils.mappers.PagamentoMapper;
import br.com.apivalhallakitchen.core.applications.ports.PagamentoRepository;
import br.com.apivalhallakitchen.core.domain.Pagamento;
import br.com.apivalhallakitchen.core.domain.Status;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
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
        pagamento = confirmarPagamento(pagamento);
        pagamentoRepository.salvarPagamento(pagamento);
        return pagamento;
    }

    private Pagamento confirmarPagamento(Pagamento pagamento) {
        pagamento.setStatus(Status.CONCLUIDO);
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
}
