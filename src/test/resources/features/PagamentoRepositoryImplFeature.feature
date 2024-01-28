# language: pt

Funcionalidade: Gerenciamento de Pagamento

Cenário: Encontrar um Pagamento por id
  Dado um Pagamento com id
  Quando eu procuro pelo Pagamento com id
  Então o Pagamento deve ser retornado

Cenário: Salvar um novo Pagamento
  Dado um novo Pagamento
  Quando eu salvo o Pagamento
  Então o Pagamento deve ser salvo