-- V2__seed_data.sql
-- Flyway seed script for all tables

-- =====================
-- categorias
-- =====================
INSERT INTO categorias (cdCategoria, nmCategoria) VALUES
    ('ELETRONICOS',   'Eletrônicos'),
    ('ALIMENTOS',     'Alimentos e Bebidas'),
    ('VESTUARIO',     'Vestuário'),
    ('MOVEIS',        'Móveis e Decoração'),
    ('INFORMATICA',   'Informática');

-- =====================
-- moedas
-- =====================
INSERT INTO moedas (nmMoeda, dsCifrao, dsSigla) VALUES
    ('Real Brasileiro',  'R$',  'BRL'),
    ('Dólar Americano',  '$',   'USD'),
    ('Euro',             '€',   'EUR');

-- =====================
-- produtos
-- =====================
INSERT INTO produtos (cdProduto, nmProduto, dsProduto, vlCusto, vlProduto, fk_idCategoria, fk_idMoeda) VALUES
    ('PROD-001', 'Smartphone X1',      'Smartphone 128GB tela 6.5"',          800.00,  1299.99, 1, 1),
    ('PROD-002', 'Notebook Pro 15',    'Notebook i7 16GB RAM 512GB SSD',     3200.00,  5499.99, 5, 1),
    ('PROD-003', 'Arroz Integral 5kg', 'Arroz integral tipo 1 pacote 5kg',      12.00,    22.90, 2, 1),
    ('PROD-004', 'Camiseta Básica',    'Camiseta algodão 100% unissex',          18.00,    59.90, 3, 1),
    ('PROD-005', 'Cadeira Gamer',      'Cadeira ergonômica reclinável 180°',    350.00,   899.00, 4, 1),
    ('PROD-006', 'Mouse Sem Fio',      'Mouse wireless 2.4GHz DPI ajustável',    35.00,   129.90, 5, 1),
    ('PROD-007', 'Teclado Mecânico',   'Teclado mecânico switch red RGB',        90.00,   299.90, 5, 1),
    ('PROD-008', 'Fone Bluetooth',     'Fone over-ear cancelamento de ruído',   120.00,   349.00, 1, 1);

-- =====================
-- estoques
-- =====================
INSERT INTO estoques (cdEstoque, nmEstoque, dsEstoque, fk_idCategoria) VALUES
    ('EST-ELET',  'Estoque Eletrônicos',   'Depósito principal de eletrônicos',  1),
    ('EST-ALIM',  'Estoque Alimentos',     'Câmara seca para alimentos',         2),
    ('EST-VEST',  'Estoque Vestuário',     'Galpão de roupas e acessórios',      3),
    ('EST-MOV',   'Estoque Móveis',        'Depósito de móveis e decoração',     4),
    ('EST-INFO',  'Estoque Informática',   'Depósito de periféricos e TI',       5);

-- =====================
-- produtosEstoques
-- =====================
INSERT INTO produtosEstoques (nrQuantidade, dsObservacao, fk_idProduto, fk_idEstoque) VALUES
    (50,  'Lote importado Q1',          1, 1),
    (20,  'Disponível para pronta-entrega', 2, 5),
    (200, 'Validade 12/2025',           3, 2),
    (150, 'Tamanhos P M G GG',          4, 3),
    (30,  'Montagem inclusa',           5, 4),
    (100, 'Compatível todos os sistemas', 6, 5),
    (80,  'Layout ABNT2',               7, 5),
    (60,  'Bateria 30h de autonomia',   8, 1);

-- =====================
-- clientes
-- =====================
INSERT INTO clientes (nmCliente, nrDocumento) VALUES
    ('Ana Paula Souza',     '123.456.789-00'),
    ('Carlos Henrique Lima','234.567.890-11'),
    ('Fernanda Oliveira',   '345.678.901-22'),
    ('Ricardo Martins',     '456.789.012-33'),
    ('Juliana Costa',       '567.890.123-44');

-- =====================
-- acessos
-- =====================
INSERT INTO acessos (nmAcesso, cdAcesso, dsAcesso) VALUES
    ('Administrador', 'ADMIN',   'Acesso total ao sistema'),
    ('Vendedor',      'VEND',    'Acesso a vendas e consulta de estoque'),
    ('Estoquista',    'ESTQ',    'Acesso a movimentações de estoque'),
    ('Gerente',       'GEREN',   'Acesso gerencial com relatórios');

-- =====================
-- funcionarios
-- =====================
INSERT INTO funcionarios (nmFuncionario, cdFuncionario, fk_idAcesso) VALUES
    ('Roberto Alves',    'FUNC-001', 1),
    ('Mariana Torres',   'FUNC-002', 2),
    ('João Pedro Silva', 'FUNC-003', 2),
    ('Patrícia Nunes',   'FUNC-004', 3),
    ('Diego Ferreira',   'FUNC-005', 4);

-- =====================
-- vendas
-- =====================
INSERT INTO vendas (dtVenda, vlVenda, flPago, fk_idCliente, fk_idFuncionario) VALUES
    ('2024-01-15 09:30:00+00', 1299.99, true,  1, 2),
    ('2024-02-03 14:15:00+00', 5499.99, true,  2, 3),
    ('2024-02-20 11:00:00+00',  349.80, false, 3, 2),
    ('2024-03-10 16:45:00+00', 1228.90, true,  4, 3),
    ('2024-03-22 10:20:00+00',  899.00, false, 5, 2);

-- =====================
-- produtosVendas
-- =====================
INSERT INTO produtosVendas (nrQuantidade, vlSaldo, fk_idProdutoEstoque, fk_idVenda) VALUES
    (1, 1299.99, 1, 1),
    (1, 5499.99, 2, 2),
    (1,  349.00, 8, 3),
    (1,    0.80, 3, 3),
    (1, 1299.99, 1, 4),
    (1,  129.90, 6, 4),
    (1,  299.90, 7, 4),
    (1,  899.00, 5, 5);
