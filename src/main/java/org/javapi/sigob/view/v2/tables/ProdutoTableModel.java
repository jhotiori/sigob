package org.javapi.sigob.view.v2.tables;

import org.javapi.sigob.model.entity.Produto;
import org.javapi.sigob.view.v2.framework.components.table.BaseEntityTableModel;
import org.javapi.sigob.view.v2.framework.components.table.EntityTableColumn;

/**
 * Modelo de tabela para Produtos.
 */
public final class ProdutoTableModel extends BaseEntityTableModel<Produto> {

    /**
     * Construtor.
     */
    public ProdutoTableModel() {
        super(
                new EntityTableColumn<>(
                        "Código",
                        Produto::getCodigo
                ),
                new EntityTableColumn<>(
                        "Nome",
                        Produto::getNome
                ),
                new EntityTableColumn<>(
                        "Valor de Compra",
                        Produto::getValorCompra
                ),
                new EntityTableColumn<>(
                        "Valor de Venda",
                        Produto::getValorVenda
                ),
                new EntityTableColumn<>(
                        "Categoria",
                        produto -> produto.getCategoria().getNome()
                ),
                new EntityTableColumn<>(
                        "Moeda",
                        produto -> {
                            if (produto.getMoeda() == null) {
                                return "[?]";
                            }

                            return "%s (%s)".formatted(
                                    produto.getMoeda().getCifrao(),
                                    produto.getMoeda().getSigla()
                            );
                        }
                )
        );
    }
}
