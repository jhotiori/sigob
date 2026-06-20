package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroDocumentoScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemDocumentoScreen;

/**
 * Controller da listagem de documentos.
 */
public final class ListagemDocumentoController extends BaseListagemController<Documento, ListagemDocumentoScreen> {

    /**
     * Serviço de documentos.
     */
    private final DocumentoService documentoService;

    /**
     * Tela de cadastro.
     */
    private final CadastroDocumentoScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         - Tela
     * @param cadastroScreen - Tela de cadastro
     */
    public ListagemDocumentoController(
            ListagemDocumentoScreen screen,
            CadastroDocumentoScreen cadastroScreen) {
        super(screen);

        this.documentoService = ServiceFactory.documentos();
        this.cadastroScreen = cadastroScreen;

        setup();
        reload();
    }

    @Override
    protected void bindSearches() {

        bindSearch(
                "id",
                () -> {
                    String text = SCREEN.searchText();

                    if (!UIValidation.notBlank(
                            text,
                            "O ID deve ser preenchido!")) {
                        return List.of();
                    }

                    Integer id = Integer.parseInt(text);

                    if (!UIValidation.condition(
                            id > 0,
                            "O ID deve ser maior que zero!")) {
                        return List.of();
                    }

                    return documentoService.findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "documento",
                () -> {
                    String text = SCREEN.searchText();

                    if (!UIValidation.notBlank(
                            text,
                            "O documento deve ser preenchido!")) {
                        return List.of();
                    }

                    return documentoService.findByDocumento(text);
                });

        bindSearch(
                "tipo",
                () -> {
                    String text = SCREEN.searchText();

                    if (!UIValidation.notBlank(
                            text,
                            "O tipo deve ser preenchido!")) {
                        return List.of();
                    }

                    return documentoService.findByTipo(text);
                });
    }

    @Override
    protected List<Documento> findAll() {
        return documentoService.findAll();
    }

    @Override
    protected void delete(Documento documento) {
        documentoService.delete(documento);
    }

    @Override
    protected void edit(Documento documento) {

        cadastroScreen.form().set(
                "documento",
                documento.getDocumento());

        cadastroScreen.form().set(
                "tipo",
                documento.getTipo());

        ScreenContext.show(
                cadastroScreen.id());
    }

    @Override
    protected String selectEntityMessage() {
        return "Selecione um documento primeiro!";
    }

    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir este documento?";
    }

    @Override
    protected String deleteSuccessMessage() {
        return "Documento excluído com sucesso!";
    }

    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir documento: "
                + e.getMessage();
    }
}
