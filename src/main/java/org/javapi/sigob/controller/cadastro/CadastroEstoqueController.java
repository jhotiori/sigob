package org.javapi.sigob.controller.cadastro;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Estoque;
import org.javapi.sigob.model.service.EstoqueService;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroEstoqueScreen;

/**
 * Controller de cadastro de estoques.
 */
public final class CadastroEstoqueController extends BaseCadastroController<Estoque, CadastroEstoqueScreen> {

    /**
     * Serviço de estoques.
     *
     * @see EstoqueService
     */
    private final EstoqueService estoqueService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro
     */
    public CadastroEstoqueController(
            CadastroEstoqueScreen screen) {
        super(screen);

        this.estoqueService = ServiceFactory.estoques();

        setup();
    }

    /**
     * Salva entidade.
     *
     * @param estoque - Estoque
     */
    @Override
    protected void save(Estoque estoque) {
        estoqueService.save(estoque);
    }

    /**
     * Cria entidade baseada na tela.
     *
     * @return Estoque - Estoque criado
     */
    @Override
    protected Estoque entity() {
        Estoque estoque = new Estoque();

        estoque.setCodigo(
                SCREEN.value("codigo"));

        estoque.setNome(
                SCREEN.value("nome"));

        return estoque;
    }

    /**
     * Valida campos da tela.
     *
     * @return boolean - Se os campos são válidos
     */
    @Override
    protected boolean validate() {
        String codigo = SCREEN.value("codigo");
        String nome = SCREEN.value("nome");

        boolean codigoNotBlank = UIValidation.notBlank(
                codigo,
                "O código do estoque deve ser preenchido!");

        boolean nomeNotBlank = UIValidation.notBlank(
                nome,
                "O nome do estoque deve ser preenchido!");

        return codigoNotBlank && nomeNotBlank;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Estoque registrado com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao registrar Estoque: " + e.getMessage();
    }
}
