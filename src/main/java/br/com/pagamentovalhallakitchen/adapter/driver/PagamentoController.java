package br.com.pagamentovalhallakitchen.adapter.driver;

import br.com.pagamentovalhallakitchen.adapter.driver.form.PagamentoForm;
import br.com.pagamentovalhallakitchen.core.applications.services.PagamentoService;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/v1/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Pagamento> efetuarPagamento(@RequestBody PagamentoForm pagamentoForm, UriComponentsBuilder uriBuilder) {
        Pagamento pagamento = pagamentoService.efetuarPagamento(pagamentoForm);
        String novaUri = uriBuilder.path("/{id}").buildAndExpand(pagamento.getId()).toUriString();
        return ResponseEntity.created(UriComponentsBuilder.fromUriString(novaUri).build().toUri()).body(pagamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscaProdutoPorId(@PathVariable Long id) {
        return pagamentoService.buscarPagamentoPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pagamento> cancelarPagamentoPorId(@PathVariable Long id) {
        return pagamentoService.cancelarPagamento(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Integer> removerPagamentoPorClienteId(@PathVariable UUID id) {
        return pagamentoService.removerPagamentoDoCliente(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
