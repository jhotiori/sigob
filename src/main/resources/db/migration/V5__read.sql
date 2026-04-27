-- =========================
-- INDEXs
-- =========================
CREATE INDEX idx_clientes_documento_id ON clientes(documento_id);

CREATE INDEX idx_funcionarios_documento_id ON funcionarios(documento_id);

CREATE INDEX idx_funcionarios_acessos_funcionario ON funcionarios_acessos(funcionario_id);
CREATE INDEX idx_funcionarios_acessos_acesso ON funcionarios_acessos(acesso_id);

CREATE INDEX idx_vendas_cliente ON vendas(cliente_id);
CREATE INDEX idx_vendas_funcionario ON vendas(funcionario_id);

CREATE INDEX idx_item_vendas_venda ON item_vendas(venda_id);
CREATE INDEX idx_item_vendas_produto_estoque ON item_vendas(produtoEstoque_id);

CREATE INDEX idx_produtos_categoria ON produtos(categoria_id);
CREATE INDEX idx_produtos_moeda ON produtos(moeda_id);

CREATE INDEX idx_produtos_estoques_produto ON produtos_estoques(produto_id);
CREATE INDEX idx_produtos_estoques_estoque ON produtos_estoques(estoque_id);

CREATE INDEX idx_vendas_status ON vendas(status);
CREATE INDEX idx_vendas_data_abertura ON vendas(data_abertura);

CREATE INDEX idx_item_vendas_venda_produto
ON item_vendas(venda_id, produtoEstoque_id);

CREATE INDEX idx_produtos_estoques_produto_estoque
ON produtos_estoques(produto_id, estoque_id);

-- ====================================
-- VENDA COMPLETA
-- ====================================
SELECT
    v.id,
    v.status,
    v.data_abertura,

    c.nome AS cliente,
    d.documento,

    f.nome AS funcionario,
    f.codigo,
    df.documento AS doc_funcionario,

    a.nome AS acesso,

    p.nome AS produto,
    cat.nome AS categoria,
    m.sigla AS moeda,

    e.nome AS estoque,

    iv.quantidade,
    iv.valor_saldo,
    (iv.quantidade * iv.valor_saldo) AS subtotal

FROM vendas v

INNER JOIN clientes c ON c.id = v.cliente_id
LEFT JOIN documentos d ON d.id = c.documento_id

INNER JOIN funcionarios f ON f.id = v.funcionario_id
INNER JOIN documentos df ON df.id = f.documento_id

INNER JOIN funcionarios_acessos fa ON fa.funcionario_id = f.id
INNER JOIN acessos a ON a.id = fa.acesso_id

INNER JOIN item_vendas iv ON iv.venda_id = v.id
INNER JOIN produtos_estoques pe ON pe.id = iv.produtoEstoque_id

INNER JOIN produtos p ON p.id = pe.produto_id
INNER JOIN categorias cat ON cat.id = p.categoria_id
INNER JOIN moedas m ON m.id = p.moeda_id

INNER JOIN estoques e ON e.id = pe.estoque_id;

-- ====================================
-- PRODUTOS (com contexto extra de estoque)
-- ====================================
SELECT
    p.id,
    p.nome,
    p.valor_venda,

    cat.nome AS categoria,
    m.sigla AS moeda,

    pe.quantidade,
    e.nome AS estoque,

    v.id AS venda_id
FROM produtos p

INNER JOIN categorias cat ON cat.id = p.categoria_id
INNER JOIN moedas m ON m.id = p.moeda_id

LEFT JOIN produtos_estoques pe ON pe.produto_id = p.id
LEFT JOIN estoques e ON e.id = pe.estoque_id

LEFT JOIN item_vendas iv ON iv.produtoEstoque_id = pe.id
LEFT JOIN vendas v ON v.id = iv.venda_id;

-- ====================================
-- FUNCIONARIOS + ACESSOS + VENDAS
-- ====================================
SELECT
    f.id,
    f.nome,
    f.codigo,

    d.documento,

    a.nome AS acesso,

    v.id AS venda,
    v.status

FROM funcionarios f

INNER JOIN documentos d ON d.id = f.documento_id

INNER JOIN funcionarios_acessos fa ON fa.funcionario_id = f.id
INNER JOIN acessos a ON a.id = fa.acesso_id

LEFT JOIN vendas v ON v.funcionario_id = f.id;

-- ====================================
-- AUDITORIA PESADO (full outer join)
-- ====================================
SELECT
    p.nome AS produto,
    pe.id AS relacao,
    e.nome AS estoque,
    iv.id AS item_venda,
    v.id AS venda

FROM produtos p

FULL OUTER JOIN produtos_estoques pe ON pe.produto_id = p.id
FULL OUTER JOIN estoques e ON e.id = pe.estoque_id
FULL OUTER JOIN item_vendas iv ON iv.produtoEstoque_id = pe.id
FULL OUTER JOIN vendas v ON v.id = iv.venda_id;

-- ====================================
-- VENDAS (com subtotais)
-- ====================================
SELECT
    v.id,
    v.valor_total,
    SUM(iv.quantidade * iv.valor_saldo) AS calculado
FROM vendas v
INNER JOIN item_vendas iv ON iv.venda_id = v.id
GROUP BY v.id, v.valor_total
ORDER BY v.id;
