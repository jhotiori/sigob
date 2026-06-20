package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.ProdutosEstoques;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Produtos em Estoque.
 */
public final class ProdutosEstoquesTableModel extends BaseEntityTableModel<ProdutosEstoques> {

    /**
     * Construtor.
     */
    public ProdutosEstoquesTableModel() {
        super(
                new EntityTableColumn<>(
                        "Produto",
                        produtoEstoque -> produtoEstoque.getProduto().getNome()
                ),
                new EntityTableColumn<>(
                        "Código do Produto",
                        produtoEstoque -> produtoEstoque.getProduto().getCodigo()
                ),
                new EntityTableColumn<>(
                        "Estoque",
                        produtoEstoque -> produtoEstoque.getEstoque().getNome()
                ),
                new EntityTableColumn<>(
                        "Quantidade",
                        ProdutosEstoques::getQuantidade
                )
        );
    }
}
