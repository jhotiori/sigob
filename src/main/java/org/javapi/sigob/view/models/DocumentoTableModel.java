package org.javapi.sigob.view.models;

import java.util.List;

import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.view.base.BaseTableModel;

/**
 * Modelo de tabela de documentos.
 */
public class DocumentoTableModel extends BaseTableModel<Documento> {

    /**
     * Colunas da tabela.
     */
    private static final String[] COLUMNS = {
        "ID",
        "Documento",
        "Tipo"
    };

    /**
     * Define documentos da tabela.
     *
     * @param documentos - Lista de documentos
     */
    public void setDocumentos(
            List<Documento> documentos
    ) {
        setRows(documentos);
    }

    /**
     * Retorna documento da linha.
     *
     * @param row - Índice da linha
     * @return Documento - Documento encontrado ou null
     */
    public Documento getDocumento(
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
            Documento documento,
            int columnIndex
    ) {
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

}
