CREATE TABLE IF NOT EXISTS pagamento(
    id SERIAL NOT NULL,
    cliente_id UUID NOT NULL,
    pedido_id BIGINT NOT NULL,
    tipo_pagamento VARCHAR(30) NOT NULL,
    valor DECIMAL NOT NULL,
    data_transacao DATE NOT NULL,
    status  VARCHAR(30) NOT NULL,
    motivo VARCHAR(100),
    PRIMARY KEY (id)
);