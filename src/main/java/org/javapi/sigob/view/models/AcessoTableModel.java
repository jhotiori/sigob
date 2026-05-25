package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de acessos.
 */
public class AcessoTableModel extends BaseTableModel<Acesso> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Nome",
        "Descrição"
    };

    /**
     * Define acessos da tabela.
     *
     * @param acessos - Lista de acessos
     */
    public void setAcessos(
            List<Acesso> acessos
    ) {
        setRows(acessos);
    }

    /**
     * Retorna acesso da linha.
     *
     * @param row - Índice da linha
     * @return Acesso - Acesso encontrado ou null
     */
    public Acesso getAcesso(
            int row
    ) {
        return getRow(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] columns() {
        return COLUMNS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getValueAt(
            Acesso acesso,
            int columnIndex
    ) {
        return switch (columnIndex) {
            case 0 ->
                acesso.getId();

            case 1 ->
                acesso.getNome();

            case 2 ->
                acesso.getDescricao();

            default ->
                null;
        };
    }

}
