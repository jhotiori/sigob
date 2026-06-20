package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.ItemVenda;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Itens de Venda.
 */
public final class ItemVendaTableModel extends BaseEntityTableModel<ItemVenda> {

    /**
     * Construtor.
     */
    public ItemVendaTableModel() {
        super(
                new EntityTableColumn<>(
                        "Produto",
                        itemVenda -> itemVenda
                                .getProdutoEstoque()
                                .getProduto()
                                .getNome()
                ),
                new EntityTableColumn<>(
                        "Código",
                        itemVenda -> itemVenda
                                .getProdutoEstoque()
                                .getProduto()
                                .getCodigo()
                ),
                new EntityTableColumn<>(
                        "Quantidade",
                        ItemVenda::getQuantidade
                ),
                new EntityTableColumn<>(
                        "Valor",
                        ItemVenda::getValorSaldo
                ),
                new EntityTableColumn<>(
                        "Estoque",
                        itemVenda -> itemVenda
                                .getProdutoEstoque()
                                .getEstoque()
                                .getNome()
                )
        );
    }
}
