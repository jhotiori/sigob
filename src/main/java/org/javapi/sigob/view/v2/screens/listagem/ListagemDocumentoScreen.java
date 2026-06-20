package org.javapi.sigob.view.v2.screens.listagem;

import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;
import org.javapi.sigob.view.v2.tables.DocumentoTableModel;

/**
 * Tela de listagem de documentos.
 */
public final class ListagemDocumentoScreen extends BaseListagemScreen<Documento> {

    /**
     * Construtor.
     */
    public ListagemDocumentoScreen() {
        super(
                "listagem-documento",
                "Listagem de Documentos",
                new DocumentoTableModel()
        );

        searchButton(
                "documento",
                "Buscar por Documento"
        );

        searchButton(
                "tipo",
                "Buscar por Tipo"
        );
    }
}
