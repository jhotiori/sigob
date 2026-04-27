CREATE TABLE IF NOT EXISTS categorias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS moedas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(32) NOT NULL,
    cifrao VARCHAR(8) NOT NULL,
    sigla VARCHAR(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS documentos (
    id SERIAL PRIMARY KEY,
    documento varchar(64) NOT NULL UNIQUE,
    tipo varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS acessos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(32) UNIQUE NOT NULL,
    descricao TEXT DEFAULT 'Nenhuma descrição foi providenciada'
);

CREATE TABLE IF NOT EXISTS estoques (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(32) UNIQUE NOT NULL,
    nome VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(64) NOT NULL,
    data_nascimento DATE,
    documento_id INT,
    FOREIGN KEY (documento_id) REFERENCES documentos(id)
);

CREATE TABLE IF NOT EXISTS funcionarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(64) NOT NULL,
    codigo VARCHAR(16) UNIQUE NOT NULL,
    documento_id INT NOT NULL,
    FOREIGN KEY (documento_id) REFERENCES documentos(id)
);

CREATE TABLE IF NOT EXISTS produtos (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(64) UNIQUE NOT NULL,
    nome VARCHAR(128) NOT NULL,
    valor_compra DECIMAL(10,2) NOT NULL,
    valor_venda DECIMAL(10,2) NOT NULL,
    categoria_id INT NOT NULL,
    moeda_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (moeda_id) REFERENCES moedas(id)
);

CREATE TABLE IF NOT EXISTS funcionarios_acessos (
    funcionario_id INT NOT NULL,
    acesso_id INT NOT NULL,
    PRIMARY KEY (funcionario_id, acesso_id),
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE,
    FOREIGN KEY (acesso_id) REFERENCES acessos(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vendas (
    id SERIAL PRIMARY KEY,
    status VARCHAR(16) NOT NULL,
    data_abertura TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    data_finalizada TIMESTAMPTZ,
    valor_total DECIMAL(15,2) NOT NULL,
    cliente_id INT NOT NULL,
    funcionario_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id)
);

CREATE TABLE IF NOT EXISTS produtos_estoques (
    id SERIAL PRIMARY KEY,
    quantidade INT NOT NULL,
    produto_id INT NOT NULL,
    estoque_id INT NOT NULL,
    UNIQUE (produto_id, estoque_id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id),
    FOREIGN KEY (estoque_id) REFERENCES estoques(id)
);

CREATE TABLE IF NOT EXISTS item_vendas (
    id SERIAL PRIMARY KEY,
    quantidade INT NOT NULL,
    valor_saldo DECIMAL(10,2) NOT NULL,
    produtoEstoque_id INT NOT NULL,
    venda_id INT NOT NULL,
    UNIQUE (venda_id, produtoEstoque_id),
    FOREIGN KEY (produtoEstoque_id) REFERENCES produtos_estoques(id),
    FOREIGN KEY (venda_id) REFERENCES vendas(id) ON DELETE CASCADE
);
