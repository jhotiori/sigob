package org.javapi.sigob.controller.cadastro;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroDocumentoScreen;

/**
 * Controller de cadastro de documentos.
 */
public final class CadastroDocumentoController extends BaseCadastroController<Documento, CadastroDocumentoScreen> {

    /**
     * Serviço de documentos.
     *
     * @see DocumentoService
     */
    private final DocumentoService documentoService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro
     */
    public CadastroDocumentoController(
            CadastroDocumentoScreen screen) {
        super(screen);
        this.documentoService = ServiceFactory.documentos();

        setup();
    }

    /**
     * Salva documento.
     *
     * @param documento - Documento
     */
    @Override
    protected void save(Documento documento) {
        documentoService.save(documento);
    }

    /**
     * Cria entidade baseada na tela.
     *
     * @return Documento - Documento criado
     */
    @Override
    protected Documento entity() {
        Documento documento = new Documento();

        documento.setDocumento(
                SCREEN.value("documento"));

        documento.setTipo(
                SCREEN.value("tipo"));

        return documento;
    }

    /**
     * Valida campos da tela.
     *
     * @return boolean - Se os campos são válidos
     */
    @Override
    protected boolean validate() {
        String documento = SCREEN.value("documento");
        String tipo = SCREEN.value("tipo");

        boolean documentoNotBlank = UIValidation.notBlank(
                documento,
                "O documento deve ser preenchido!");

        boolean tipoNotBlank = UIValidation.notBlank(
                tipo,
                "O tipo do documento deve ser preenchido!");

        return documentoNotBlank && tipoNotBlank;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Documento registrado com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao registrar Documento: " + e.getMessage();
    }
}
