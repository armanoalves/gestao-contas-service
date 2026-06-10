CREATE TABLE fornecedores (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE contas (
    id UUID PRIMARY KEY,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    situacao VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    valor DECIMAL(12, 2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    fornecedor_id UUID NOT NULL,
    fornecedor_nome VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_contas_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
);

CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_contas_situacao ON contas(situacao);
CREATE INDEX idx_contas_data_vencimento ON contas(data_vencimento);
CREATE INDEX idx_contas_data_pagamento ON contas(data_pagamento);
CREATE INDEX idx_contas_fornecedor_id ON contas(fornecedor_id);
CREATE INDEX idx_usuarios_email ON usuarios(email);
