package org.javapi.sigob.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javapi.sigob.entity.Documento;

/**
 * Modelo de tabela de documentos.
 */
public class DocumentoTableModel extends AbstractTableModel {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Documento",
        "Tipo"
    };

    /**
     * Lista de documentos.
     */
    private List<Documento> documentos = new ArrayList<>();

    /**
     * Define documentos da tabela.
     *
     * @param documentos - Lista de documentos
     */
    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos != null
                ? documentos
                : new ArrayList<>();

        fireTableDataChanged();
    }

    /**
     * Retorna documento da linha.
     *
     * @param row - Índice da linha
     * @return Documento - Documento encontrado
     */
    public Documento getDocumento(int row) {
        if (row < 0 || row >= documentos.size()) {
            return null;
        }

        return documentos.get(row);
    }

    @Override
    public int getRowCount() {
        return documentos.size();
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
        Documento documento = documentos.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                documento.getId();

            case 1 ->
                documento.getDocumento();

            case 2 ->
                documento.getTipo();

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
