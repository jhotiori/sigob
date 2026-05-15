INSERT INTO moedas (nome, cifrao, sigla) VALUES
    ('Real Brasileiro', 'R$', 'BRL');

INSERT INTO categorias (nome) VALUES
    ('Refrigerantes'),
    ('Energéticos'),
    ('Cervejas'),
    ('Destilados'),
    ('Sucos'),
    ('Águas');

INSERT INTO acessos (nome, descricao) VALUES
    ('ADMIN', 'Acesso total ao sistema'),
    ('VENDEDOR', 'Pode realizar vendas e consultar estoque');

INSERT INTO documentos (documento, tipo) VALUES
    ('12345678901', 'CPF'),
    ('98765432100', 'CPF'),
    ('11122233344', 'CPF'),
    ('55566677788', 'CPF');

INSERT INTO clientes (nome, data_nascimento, documento_id) VALUES
    ('João Silva', '1990-05-10', 1),
    ('Maria Souza', '1985-03-22', 2),
    ('Carlos Lima', NULL, NULL),
    ('Ana Costa', '2000-01-15', NULL);

INSERT INTO funcionarios (nome, codigo, documento_id) VALUES
    ('admin', 'admin', 3),
    ('João Vendas', 'vend01', 4);

INSERT INTO funcionarios_acessos (funcionario_id, acesso_id) VALUES
    (1, 1),
    (2, 2);

INSERT INTO estoques (codigo, nome) VALUES
    ('EST001', 'Geladeira Expositora 01'),
    ('EST002', 'Geladeira Expositora 02'),
    ('EST003', 'Freezer Horizontal'),
    ('EST004', 'Depósito Interno'),
    ('EST005', 'Frigobar Balcão');

INSERT INTO produtos (codigo, nome, valor_compra, valor_venda, categoria_id, moeda_id) VALUES
    ('P001', 'Coca-Cola 2L', 6.50, 10.00, 1, 1),
    ('P002', 'Guaraná Antarctica 2L', 5.80, 9.50, 1, 1),
    ('P003', 'Red Bull 250ml', 7.00, 12.00, 2, 1),
    ('P004', 'Monster Energy 473ml', 8.50, 14.00, 2, 1),
    ('P005', 'Heineken Long Neck', 5.00, 9.00, 3, 1),
    ('P006', 'Budweiser Lata 350ml', 3.80, 7.00, 3, 1),
    ('P007', 'Smirnoff Ice', 6.00, 11.00, 4, 1),
    ('P008', 'Vodka Smirnoff 998ml', 28.00, 45.00, 4, 1),
    ('P009', 'Suco Del Valle Uva 1L', 4.50, 8.00, 5, 1),
    ('P010', 'Água Mineral 500ml', 1.20, 3.00, 6, 1);

INSERT INTO produtos_estoques (quantidade, produto_id, estoque_id) VALUES
    (20, 1, 1),
    (15, 2, 1),
    (25, 3, 5),
    (18, 4, 5),
    (30, 5, 2),
    (40, 6, 2),
    (12, 7, 3),
    (8, 8, 4),
    (22, 9, 1),
    (60, 10, 1);

INSERT INTO vendas (status, data_abertura, data_finalizada, valor_total, cliente_id, funcionario_id) VALUES
    ('finalizada', NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 31.00, 1, 2),
    ('finalizada', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 24.00, 2, 2),
    ('aberta', NOW(), NULL, 0.00, 3, 2);

INSERT INTO item_vendas (quantidade, valor_saldo, produtoEstoque_id, venda_id) VALUES
    (2, 10.00, 1, 1),
    (1, 11.00, 7, 1),
    (2, 12.00, 3, 2);