-- =========================
-- MOEDAS
-- =========================
INSERT INTO moedas (id, nome, cifrao, sigla) VALUES
(1, 'Real Brasileiro', 'R$', 'BRL');

-- =========================
-- CATEGORIAS
-- =========================
INSERT INTO categorias (id, nome) VALUES
(1, 'Eletrônicos'),
(2, 'Alimentos'),
(3, 'Vestuário');

-- =========================
-- ACESSOS
-- =========================
INSERT INTO acessos (id, nome, descricao) VALUES
(1, 'ADMIN', 'Acesso total'),
(2, 'VENDEDOR', 'Pode realizar vendas');

-- =========================
-- DOCUMENTOS
-- =========================
INSERT INTO documentos (id, documento, tipo) VALUES
(1, '12345678901', 'CPF'),
(2, '98765432100', 'CPF'),
(3, '11122233344', 'CPF'),
(4, '55566677788', 'CPF');

-- =========================
-- CLIENTES (alguns sem documento)
-- =========================
INSERT INTO clientes (id, nome, data_nascimento, documento_id) VALUES
(1, 'João Silva', '05-10-1990', 1),
(2, 'Maria Souza', '03-22-1985', 2),
(3, 'Carlos Lima', NULL, NULL),
(4, 'Ana Costa', '01-15-2000', NULL);

-- =========================
-- FUNCIONARIOS
-- =========================
INSERT INTO funcionarios (id, nome, codigo, documento_id) VALUES
(1, 'admin', 'admin', 3),
(2, 'vendedor', 'vendedor', 4);

-- =========================
-- FUNCIONARIOS_ACESSOS (1 acesso por funcionário)
-- =========================
INSERT INTO funcionarios_acessos (funcionario_id, acesso_id) VALUES
(1, 1),
(2, 2);

-- =========================
-- ESTOQUES
-- =========================
INSERT INTO estoques (id, codigo, nome) VALUES
(1, 'EST001', 'Estoque Principal'),
(2, 'EST002', 'Estoque Secundário');

-- =========================
-- PRODUTOS
-- =========================
INSERT INTO produtos (id, codigo, nome, valor_compra, valor_venda, categoria_id, moeda_id) VALUES
(1, 'P001', 'Notebook', 2500.00, 3500.00, 1, 1),
(2, 'P002', 'Camiseta', 20.00, 50.00, 3, 1),
(3, 'P003', 'Arroz 5kg', 15.00, 25.00, 2, 1);

-- =========================
-- PRODUTOS_ESTOQUES (1 produto → 1 estoque)
-- =========================
INSERT INTO produtos_estoques (id, quantidade, produto_id, estoque_id) VALUES
(1, 10, 1, 1),
(2, 100, 2, 1),
(3, 200, 3, 2);

-- =========================
-- VENDAS
-- =========================
INSERT INTO vendas (id, status, data_abertura, data_finalizada, valor_total, cliente_id, funcionario_id) VALUES
(1, 'finalizada', NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 3550.00, 1, 1),
(2, 'finalizada', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 100.00, 2, 2);

-- =========================
-- ITEM_VENDAS (valor_saldo = preço unitário)
-- =========================

-- Venda 1:
-- Notebook (1 × 3500) + Camiseta (1 × 50) = 3550
INSERT INTO item_vendas (id, quantidade, valor_saldo, produtoEstoque_id, venda_id) VALUES
(1, 1, 3500.00, 1, 1),
(2, 1, 50.00, 2, 1);

-- Venda 2:
-- Camiseta (2 × 50) = 100
INSERT INTO item_vendas (id, quantidade, valor_saldo, produtoEstoque_id, venda_id) VALUES
(3, 2, 50.00, 2, 2);
