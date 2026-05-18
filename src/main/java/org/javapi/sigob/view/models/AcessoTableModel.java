package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.Acesso;

/**
 * Modelo de tabela de acessos.
 */
public class AcessoTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Nome",
        "Descrição"
    };

    /**
     * Lista de acessos.
     */
    private List<Acesso> acessos = new ArrayList<>();

    /**
     * Define acessos da tabela.
     *
     * @param acessos - Lista de acessos
     */
    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos != null
                ? acessos
                : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna acesso da linha.
     *
     * @param row - Índice da linha
     * @return Acesso - Acesso encontrado
     */
    public Acesso getAcesso(int row) {
        if (row < 0 || row >= acessos.size()) {
            return null;
        }

        return acessos.get(row);
    }

    @Override
    public int getRowCount() {
        return acessos.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex
    ) {
        Acesso acesso = acessos.get(rowIndex);

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

    @Override
    public boolean isCellEditable(
            int rowIndex,
            int columnIndex
    ) {
        return false;
    }

}
