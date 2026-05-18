package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.Funcionario;

/**
 * Modelo de tabela para funcionários.
 */
public final class FuncionarioTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Nome",
        "Documento",
        "Acessos"
    };

    /**
     * Lista atual de funcionários.
     */
    private List<Funcionario> funcionarios
            = new ArrayList<>();

    /**
     * Define funcionários da tabela.
     *
     * @param funcionarios - Lista de funcionários
     */
    public void setFuncionarios(
            List<Funcionario> funcionarios
    ) {
        this.funcionarios = funcionarios != null
                ? funcionarios
                : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna funcionário da linha.
     *
     * @param row - Índice da linha
     * @return Funcionario - Funcionário encontrado
     */
    public Funcionario getFuncionario(
            int row
    ) {
        return funcionarios.get(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return funcionarios.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(
            int column
    ) {
        return COLUMNS[column];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex
    ) {
        Funcionario funcionario = funcionarios.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                funcionario.getId();

            case 1 ->
                funcionario.getNome();

            case 2 ->
                funcionario.getDocumento() != null
                ? funcionario.getDocumento().getDocumento()
                : "-";

            case 3 ->
                funcionario.getAcessos()
                .stream()
                .map(acesso -> acesso.getNome())
                .sorted()
                .reduce((a, b) -> a + ", " + b)
                .orElse("-");

            default ->
                "";
        };
    }

}
