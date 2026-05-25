-- =========================
-- MOEDAS
-- =========================
INSERT INTO moedas (
    id,
    nome,
    cifrao,
    sigla
) VALUES
(1, 'Real Brasileiro', 'R$', 'BRL');

-- =========================
-- CATEGORIAS
-- =========================
INSERT INTO categorias (
    id,
    nome
) VALUES
(1, 'Eletrônicos'),
(2, 'Alimentos'),
(3, 'Vestuário'),
(4, 'Bebidas'),
(5, 'Outros');

-- =========================
-- ACESSOS
-- =========================
INSERT INTO acessos (
    id,
    nome,
    descricao
) VALUES
(1, 'ADMIN', 'Acesso total'),
(2, 'VENDEDOR', 'Pode realizar vendas'),
(3, 'FUNCIONARIO', 'Permissão para ver relatórios');

-- =========================
-- DOCUMENTOS
-- =========================
INSERT INTO documentos (
    id,
    documento,
    tipo
) VALUES
(1, '12345678910', 'CPF'),
(2, '98765432100', 'CPF'),
(3, '11122233344', 'CPF'),
(4, '55566677788', 'CPF'),
(5, '99988877766', 'CNPJ'),
(6, '77766655544', 'CNPJ'),
(7, '33322211100', 'CNPJ'),
(8, '88877766655', 'CNPJ');

-- =========================
-- CLIENTES
-- =========================
INSERT INTO clientes (
    id,
    nome,
    data_nascimento,
    documento_id
) VALUES
(1, 'João Silva', '1990-05-10', 1),
(2, 'Maria Souza', '1985-03-22', 2),
(3, 'Carlos Lima', NULL, NULL),
(4, 'Ana Costa', '2000-01-15', NULL),
(5, 'Pedro Oliveira', '1995-07-30', 3),
(6, 'Luisa Santos', NULL, 4),
(7, 'Rafael Ferreira', '1992-09-12', 5),
(8, 'Isabela Almeida', '1988-11-25', 6),
(9, 'Guilherme Rodrigues', NULL, 7);

-- =========================
-- FUNCIONARIOS
-- =========================
INSERT INTO funcionarios (
    id,
    nome,
    codigo,
    documento_id
) VALUES
(1, 'admin', 'admin', 1),
(2, 'vendedor', 'vendedor', 2),
(3, 'funcionario', 'funcionario', 3);

-- =========================
-- FUNCIONARIOS_ACESSOS
-- =========================
INSERT INTO funcionarios_acessos (
    funcionario_id,
    acesso_id
) VALUES
(1, 1),
(2, 2),
(3, 3);

-- =========================
-- ESTOQUES
-- =========================
INSERT INTO estoques (
    id,
    codigo,
    nome
) VALUES
(1, 'EST001', 'Estoque Principal'),
(2, 'EST002', 'Estoque Secundário'),
(3, 'EST003', 'Estoque Terciário');

-- =========================
-- PRODUTOS
-- =========================
INSERT INTO produtos (
    id,
    codigo,
    nome,
    valor_compra,
    valor_venda,
    categoria_id,
    moeda_id
) VALUES
(1, 'P001', 'Notebook', 2500.00, 3500.00, 1, 1),
(2, 'P002', 'Camiseta', 20.00, 50.00, 3, 1),
(3, 'P003', 'Arroz 5kg', 15.00, 25.00, 2, 1),
(4, 'P004', 'Coca-Cola 2L', 5.00, 10.00, 4, 1),
(5, 'P005', 'Feijão 1kg', 8.00, 15.00, 2, 1),
(6, 'P006', 'Cerveja 500ml', 3.00, 6.00, 4, 1),
(7, 'P007', 'Bolacha 100g', 2.00, 4.00, 5, 1),
(8, 'P008', 'Leite 1L', 4.00, 8.00, 4, 1),
(9, 'P009', 'Macarrão 500g', 6.00, 12.00, 2, 1),
(10, 'P010', 'Suco 1L', 3.00, 6.00, 4, 1);

-- =========================
-- PRODUTOS_ESTOQUES
-- =========================
INSERT INTO produtos_estoques (
    id,
    quantidade,
    produto_id,
    estoque_id
) VALUES
(1, 10, 1, 1),
(2, 100, 2, 1),
(3, 40, 3, 2),
(4, 20, 4, 2),
(5, 70, 5, 2),
(6, 10, 6, 2),
(7, 30, 7, 2),
(8, 60, 8, 2),
(9, 150, 9, 2),
(10, 20, 10, 2);

-- =========================
-- VENDAS
-- =========================
INSERT INTO vendas (
    id,
    status,
    data_abertura,
    data_finalizada,
    valor_total,
    cliente_id,
    funcionario_id
) VALUES
(1, 'finalizada', NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 3550.00, 1, 1),
(2, 'finalizada', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 100.00, 2, 2),
(3, 'aberta', NOW(), NULL, 0.00, 3, 3),
(4, 'aberta', NOW(), NULL, 0.00, 4, 3),
(5, 'aberta', NOW(), NULL, 0.00, 5, 3),
(6, 'aberta', NOW(), NULL, 0.00, 6, 3),
(7, 'aberta', NOW(), NULL, 0.00, 7, 3),
(8, 'aberta', NOW(), NULL, 0.00, 8, 3),
(9, 'aberta', NOW(), NULL, 0.00, 9, 3);

-- =========================
-- RESET DAS SEQUENCES
-- =========================

SELECT setval(
    pg_get_serial_sequence('moedas', 'id'),
    COALESCE(MAX(id), 1)
) FROM moedas;

SELECT setval(
    pg_get_serial_sequence('categorias', 'id'),
    COALESCE(MAX(id), 1)
) FROM categorias;

SELECT setval(
    pg_get_serial_sequence('acessos', 'id'),
    COALESCE(MAX(id), 1)
) FROM acessos;

SELECT setval(
    pg_get_serial_sequence('documentos', 'id'),
    COALESCE(MAX(id), 1)
) FROM documentos;

SELECT setval(
    pg_get_serial_sequence('clientes', 'id'),
    COALESCE(MAX(id), 1)
) FROM clientes;

SELECT setval(
    pg_get_serial_sequence('funcionarios', 'id'),
    COALESCE(MAX(id), 1)
) FROM funcionarios;

SELECT setval(
    pg_get_serial_sequence('estoques', 'id'),
    COALESCE(MAX(id), 1)
) FROM estoques;

SELECT setval(
    pg_get_serial_sequence('produtos', 'id'),
    COALESCE(MAX(id), 1)
) FROM produtos;

SELECT setval(
    pg_get_serial_sequence('produtos_estoques', 'id'),
    COALESCE(MAX(id), 1)
) FROM produtos_estoques;

SELECT setval(
    pg_get_serial_sequence('vendas', 'id'),
    COALESCE(MAX(id), 1)
) FROM vendas;
