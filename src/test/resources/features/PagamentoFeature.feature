# language: pt
Funcionalidade: Teste de API de pagamento

  Cenário: O cliente efetua uma chamada POST /pagamentos
    Quando o cliente efetua o pagamento
    Então o cliente recebe a informação de que foi paga com sucesso

  Cenário: Solicitar busca de pagamento
    Quando o cliente solicita a busca de pagamento
    Então recebe as informações do pagamento

  Cenário: Solicitar o cancelamento do pagamento
    Quando o cliente solicita o cancelamento do pagamento
    Então recebe as informações de pagamento cancelado