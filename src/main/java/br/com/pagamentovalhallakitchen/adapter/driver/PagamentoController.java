package br.com.pagamentovalhallakitchen.adapter.driver;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoPagamentoForm;
import br.com.pagamentovalhallakitchen.adapter.driver.form.RetornoWebhookForm;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoServiceImpl;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/v1/pagamentos")
public class PagamentoController {

    private final PagamentoServiceImpl pagamentoService;

    public PagamentoController(PagamentoServiceImpl pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@RequestBody PedidoGeradoForm pagamentoForm, UriComponentsBuilder uriBuilder) {
        Pagamento pagamento = pagamentoService.criarPagamento(pagamentoForm);
        String novaUri = uriBuilder.path("/{id}").buildAndExpand(pagamento.getId()).toUriString();
        return ResponseEntity.created(UriComponentsBuilder.fromUriString(novaUri).build().toUri()).body(pagamento);
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> respostaPagamento(@RequestBody RetornoWebhookForm retornoWebhookForm) {
        try {
            pagamentoService.processarPagamento(retornoWebhookForm);
            return ResponseEntity.ok("Pagamento processado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscaProdutoPorId(@PathVariable Long id) {
        return pagamentoService.buscarPagamentoPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pagamento> cancelarPagamentoPorId(@PathVariable Long id) {
        return pagamentoService.cancelarPagamento(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Integer> removerPagamentoPorClienteId(@PathVariable UUID id) {
        return pagamentoService.removerPagamentoDoCliente(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
