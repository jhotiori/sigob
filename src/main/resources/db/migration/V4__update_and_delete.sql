UPDATE produtos
SET valor_venda = 11.55
WHERE codigo = 'CavalãoL';

UPDATE produtos
SET nome = 'Skoll - Latão',
    valor_compra = 2.50,
    valor_venda  = 5.50
WHERE codigo = 'SkollLataBig';

DELETE FROM funcionarios
WHERE codigo = 'FUNC-005';

DELETE FROM clientes
WHERE nome like '%Costa';