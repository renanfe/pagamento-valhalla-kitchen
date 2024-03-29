CREATE TABLE IF NOT EXISTS pagamento(
    id SERIAL NOT NULL,
    cliente_id UUID NOT NULL,
    pedido_id BIGINT NOT NULL,
    tipo_pagamento VARCHAR(30) NOT NULL,
    preco DECIMAL NOT NULL,
    data_transacao DATE NOT NULL,
    status  VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);