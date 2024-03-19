package br.com.pagamentovalhallakitchen.integration;

import br.com.pagamentovalhallakitchen.SpringTest;
import br.com.pagamentovalhallakitchen.adapter.driver.form.PedidoGeradoForm;
import br.com.pagamentovalhallakitchen.core.domain.Pagamento;
import br.com.pagamentovalhallakitchen.utils.PagamentoHelper;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class PagamentoIntegrationTest extends SpringTest {
    @LocalServerPort
    private String PORT;
    private String ENDPOINT = "http://localhost:";
    private String URL = "/api/v1/pagamentos";
    private Response response;

    private Pagamento pagamento;

    public Pagamento efetuarPagamento(){
        PedidoGeradoForm pagamentoForm = PagamentoHelper.buildPagamentoForm();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoForm)
                .when().post(ENDPOINT+PORT+URL);
        return response.then().extract().as(Pagamento.class);
    }
    @Quando("o cliente efetua o pagamento")
    public void efetuarUmNovoPagamento(){
        PedidoGeradoForm pagamentoForm = PagamentoHelper.buildPagamentoForm();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoForm)
                .when().post(ENDPOINT+PORT+URL);
    }

    @Então("o cliente recebe a informação de que foi paga com sucesso")
    public void recebePagamentoRealizadoComSucesso () {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("schemas/PagamentoResponseSchema.json"));
    }

    @Quando("o cliente solicita a busca de pagamento")
    public void buscaPorPagamento(){
        pagamento = efetuarPagamento();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT+PORT+URL+"/{id}", pagamento.getId().toString());
    }

    @Então("recebe as informações do pagamento")
    public void recebeInformaçõesdoPagamento () {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/GetPagamentoResponseSchema.json"));
    }

    @Quando("o cliente solicita o cancelamento do pagamento")
    public void cancelaOPagamento(){
        pagamento = efetuarPagamento();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(ENDPOINT+PORT+URL+"/{id}", pagamento.getId().toString());
    }

    @Então("recebe as informações de pagamento cancelado")
    public void recebeInformaçãoPAgamentoCancelado() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/GetPagamentoResponseSchema.json"))
                .body("status", equalTo("CANCELADO"));
    }
}
