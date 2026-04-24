
SELECT codigo, nome, valor_compra, valor_venda,
       (valor_venda - valor_compra) AS margem
FROM produtos
WHERE (valor_venda - valor_compra) > 5.00
ORDER BY margem DESC;

SELECT nome, data_nascimento
FROM clientes
WHERE data_nascimento BETWEEN '1980-01-01' AND '2000-12-31'
ORDER BY data_nascimento ASC;

SELECT v.id, v.status, v.valor_total, v.data_abertura
FROM vendas v
WHERE v.status = 'aberta'
  AND v.funcionario_id = (
    SELECT id FROM funcionarios WHERE codigo = 'FUNC-003'
);

SELECT f.nome, f.codigo, a.nome AS acesso
FROM funcionarios f
         JOIN acessos a ON a.id = f.acesso_id
WHERE a.nome IN ('ADMIN', 'VENDAS')
ORDER BY a.nome;



SELECT p.codigo, p.nome AS produto, c.nome AS categoria, m.sigla AS moeda, p.valor_compra, p.valor_venda
FROM produtos p
         INNER JOIN categorias c ON c.id = p.categoria_id
         INNER JOIN moedas     m ON m.id = p.moeda_id
ORDER BY c.nome, p.nome;

SELECT cl.nome AS cliente, cl.data_nascimento, d.documento, d.tipo
FROM clientes cl
         LEFT JOIN documentos d ON d.id = cl.documento_id
ORDER BY cl.nome;

SELECT a.nome AS acesso,
       a.descricao,
       f.nome AS funcionario,
       f.codigo
FROM funcionarios f
         RIGHT JOIN acessos a ON a.id = f.acesso_id
ORDER BY a.nome;

SELECT cl.nome AS cliente,
       v.id AS venda_id,
       v.status,
       v.valor_total
FROM clientes cl
         FULL JOIN vendas v ON v.cliente_id = cl.id
ORDER BY cl.nome NULLS LAST;