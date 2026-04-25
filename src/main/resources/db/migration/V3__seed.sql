INSERT INTO documentos (documento, tipo) VALUES
    ('123.456.789-00', 'CPF'),
    ('987.654.321-00', 'CPF'),
    ('456.789.123-00', 'CPF'),
    ('12.345.678/0001-99', 'CNPJ'),
    ('98.765.432/0001-11', 'CNPJ');


INSERT INTO acessos (nome, descricao) VALUES
    ('ADMIN',    'Acesso total ao sistema'),
    ('ESTOQUE',  'Acesso restrito ao módulo de estoque'),
    ('VENDAS',   'Acesso restrito ao módulo de vendas');


INSERT INTO categorias (nome) VALUES
    ('Energéticos'),
    ('Refrigerantes'),
    ('Álcool');


INSERT INTO moedas (nome, cifrao, sigla) VALUES
    ('Real Brasileiro', 'R$',  'BRL'),
    ('Dólar Americano', 'US$', 'USD'),
    ('Euro',            '€',   'EUR');



INSERT INTO clientes (nome, data_nascimento, documento_id) VALUES
    ('Ana Souza',      '1990-03-15', 1),
    ('Bruno Lima',     '1985-07-22', 2),
    ('Carla Mendes',   '2000-11-05', NULL),
    ('Diego Ferreira', '1978-01-30', 3),
    ('Elena Costa',    '1995-06-18', NULL);



INSERT INTO estoques (codigo, nome) VALUES
    ('EST-001', 'Frigobar 1'),
    ('EST-002', 'Freezer 3'),
    ('EST-003', 'Prateleira 6');



INSERT INTO funcionarios (nome, codigo, acesso_id, documento_id) VALUES
    ('Roberto Admin',    'FUNC-001', 1, 3),
    ('Mariana Estoque',  'FUNC-002', 2, 4),
    ('Paulo Vendas',     'FUNC-003', 3, 5),
    ('Juliana Vendas',   'FUNC-004', 3, 1),
    ('Felipe Estoque',   'FUNC-005', 2, 2);



INSERT INTO produtos (codigo, nome, valor_compra, valor_venda, categoria_id, moeda_id) VALUES
    ('CavalãoL', 'Full Horse 1L',      6.00, 12.00, 1, 1),
    ('Citruz2L', 'Sherepas 2L',         4.50,   9.50, 2, 1),
    ('GuaraJesus2L', 'Guaraná Jesus 2L',     3.00,  9.90, 2, 1),
    ('SkollLataBig','Skoll - Lata 350ml',          3.25,   6.00, 3, 1),
    ('RTouro600', 'ReadTauros 600ml',        2.75,  8.00, 1, 1);



INSERT INTO vendas (status, valor_total, cliente_id, funcionario_id) VALUES
    ('aberta',     3200.00, 1, 3),
    ('aberta',      389.80, 2, 4),
    ('finalizada',   34.90, 3, 3),
    ('aberta',      479.80, 4, 4),
    ('finalizada',  179.90, 5, 3);