package org.javapi.sigob.view.base;

import java.util.List;

import javax.swing.JTable;

import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.popups.Popups;

/**
 * Tela base para relatórios.
 *
 * @param <T> - Tipo da entidade
 */
public abstract class BaseRelatorioScreen<T>
        extends BaseScreen {

    /**
     * Cria tela base de relatório.
     *
     * @param id - ID da tela
     */
    protected BaseRelatorioScreen(
            String id
    ) {
        super(id);
    }

    /**
     * Retorna tabela da tela.
     *
     * @return JTable - Tabela da tela
     */
    protected abstract JTable table();

    /**
     * Retorna model da tabela.
     *
     * @return BaseTableModel<T> - Model da tabela
     */
    protected abstract BaseTableModel<T> tableModel();

    /**
     * Retorna nome singular da entidade.
     *
     * @return String - Nome singular
     */
    protected abstract String entityNameSingular();

    /**
     * Retorna nome plural da entidade.
     *
     * @return String - Nome plural
     */
    protected abstract String entityNamePlural();

    /**
     * Define resultados da tabela.
     *
     * @param rows - Linhas da tabela
     */
    protected void setResultados(
            List<T> rows
    ) {
        if (rows == null || rows.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum(a) %s encontrado(a)!"
                            .formatted(entityNameSingular())
            );

            return;
        }

        tableModel().setRows(rows);
    }

    /**
     * Limpa resultados da tabela.
     */
    protected void clearResults() {
        tableModel().setRows(List.of());
    }

    /**
     * Retorna linha selecionada.
     *
     * @return T - Linha selecionada ou null
     */
    protected T selectedRow() {
        int row = table().getSelectedRow();

        if (row < 0) {
            return null;
        }

        return tableModel().getRow(row);
    }

    /**
     * Verifica acesso administrativo.
     *
     * @return boolean - true se possuir acesso
     */
    protected boolean hasAdminAccess() {
        return ApplicationContext.hasFuncionarioAcesso(
                "admin"
        );
    }

}
