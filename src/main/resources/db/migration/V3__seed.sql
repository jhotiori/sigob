-- =========================
-- MOEDAS
-- =========================

INSERT INTO moedas (
    id,
    nome,
    cifrao,
    sigla
)
VALUES
(1, 'Real Brasileiro', 'R$', 'BRL');


-- =========================
-- CATEGORIAS
-- =========================

INSERT INTO categorias (
    id,
    nome
)
VALUES
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
)
VALUES
(1, 'ADMIN', 'Acesso total ao sistema'),
(2, 'VENDA', 'Pode realizar vendas'),
(3, 'ESTOQUE', 'Pode gerenciar estoque'),
(4, 'RELATORIO', 'Pode visualizar relatórios');


-- =========================
-- DOCUMENTOS
-- =========================

INSERT INTO documentos (
    id,
    documento,
    tipo
)
VALUES
(1, '12345678910', 'CPF'),
(2, '98765432100', 'CPF'),
(3, '11122233344', 'CPF'),
(4, '55566677788', 'CPF'),
(5, '99988877766', 'CPF'),
(6, '77766655544', 'CPF');


-- =========================
-- CLIENTES
-- =========================

INSERT INTO clientes (
    id,
    nome,
    data_nascimento,
    documento_id
)
VALUES
(1, 'João Silva', '1990-05-10', 1),
(2, 'Maria Souza', '1985-03-22', 2),
(3, 'Carlos Lima', NULL, NULL),
(4, 'Ana Costa', '2000-01-15', NULL);


-- =========================
-- FUNCIONARIOS
-- =========================

INSERT INTO funcionarios (
    id,
    nome,
    codigo,
    documento_id
)
VALUES
(1, 'admin', 'admin', 3),
(2, 'vendedor', 'vendedor', 4),
(3, 'estoque', 'estoque', 5);


-- =========================
-- FUNCIONARIOS_ACESSOS
-- =========================

INSERT INTO funcionarios_acessos (
    funcionario_id,
    acesso_id
)
VALUES

-- Admin
(1,1),

-- Vendedor
(2,2),

-- Estoquista
(3,3);



-- =========================
-- ESTOQUES
-- =========================

INSERT INTO estoques (
    id,
    codigo,
    nome
)
VALUES
(1, 'EST001', 'Estoque Principal'),
(2, 'EST002', 'Estoque Secundário');


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
)
VALUES

(1, 'P001', 'Notebook',
2500.00,
3500.00,
1,
1),

(2, 'P002', 'Camiseta',
20.00,
50.00,
3,
1),

(3, 'P003', 'Arroz 5kg',
15.00,
25.00,
2,
1),

(4, 'P004', 'Coca-Cola 2L',
5.00,
10.00,
4,
1),

(5, 'P005', 'Bolacha 100g',
2.00,
4.00,
5,
1);



-- =========================
-- PRODUTOS_ESTOQUES
-- =========================

INSERT INTO produtos_estoques (
    id,
    quantidade,
    produto_id,
    estoque_id
)
VALUES

(1,10,1,1),
(2,100,2,1),
(3,50,3,2),
(4,40,4,2),
(5,80,5,2);



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
)
VALUES

(
1,
'finalizada',
NOW() - INTERVAL '2 days',
NOW() - INTERVAL '2 days',
3500.00,
1,
1
),

(
2,
'aberta',
NOW(),
NULL,
0.00,
2,
2
);



-- =========================
-- ITEM_VENDAS
-- =========================

INSERT INTO item_vendas (
    id,
    quantidade,
    valor_saldo,
    produtoEstoque_id,
    venda_id
)
VALUES

(
1,
1,
3500.00,
1,
1
);



-- =========================
-- RESET SEQUENCES
-- =========================

SELECT setval(
    pg_get_serial_sequence('moedas','id'),
    COALESCE(MAX(id),1)
)
FROM moedas;


SELECT setval(
    pg_get_serial_sequence('categorias','id'),
    COALESCE(MAX(id),1)
)
FROM categorias;


SELECT setval(
    pg_get_serial_sequence('acessos','id'),
    COALESCE(MAX(id),1)
)
FROM acessos;


SELECT setval(
    pg_get_serial_sequence('documentos','id'),
    COALESCE(MAX(id),1)
)
FROM documentos;


SELECT setval(
    pg_get_serial_sequence('clientes','id'),
    COALESCE(MAX(id),1)
)
FROM clientes;


SELECT setval(
    pg_get_serial_sequence('funcionarios','id'),
    COALESCE(MAX(id),1)
)
FROM funcionarios;


SELECT setval(
    pg_get_serial_sequence('estoques','id'),
    COALESCE(MAX(id),1)
)
FROM estoques;


SELECT setval(
    pg_get_serial_sequence('produtos','id'),
    COALESCE(MAX(id),1)
)
FROM produtos;


SELECT setval(
    pg_get_serial_sequence('produtos_estoques','id'),
    COALESCE(MAX(id),1)
)
FROM produtos_estoques;


SELECT setval(
    pg_get_serial_sequence('vendas','id'),
    COALESCE(MAX(id),1)
)
FROM vendas;


SELECT setval(
    pg_get_serial_sequence('item_vendas','id'),
    COALESCE(MAX(id),1)
)
FROM item_vendas;
