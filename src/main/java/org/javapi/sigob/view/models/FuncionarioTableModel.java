package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela para funcionários.
 */
public class FuncionarioTableModel extends BaseTableModel<Funcionario> {

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
     * Define funcionários da tabela.
     *
     * @param funcionarios - Lista de funcionários
     */
    public void setFuncionarios(
            List<Funcionario> funcionarios
    ) {
        setRows(funcionarios);
    }

    /**
     * Retorna funcionário da linha.
     *
     * @param row - Índice da linha
     * @return Funcionario - Funcionário encontrado ou null
     */
    public Funcionario getFuncionario(
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
            Funcionario funcionario,
            int columnIndex
    ) {
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
                null;
        };
    }

}
