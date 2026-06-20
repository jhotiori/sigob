package org.javapi.sigob.controller.cadastro;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Categoria;
import org.javapi.sigob.model.service.CategoriaService;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroCategoriaScreen;

/**
 * Controller de Cadastro de Categorias.
 */
public final class CadastroCategoriaController extends BaseCadastroController<Categoria, CadastroCategoriaScreen> {
    /**
     * Serviço de Categorias.
     *
     * @see CategoriaService
     */
    private final CategoriaService categoriaService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro de categoria
     */
    public CadastroCategoriaController(CadastroCategoriaScreen screen) {
        super(screen);
        this.categoriaService = ServiceFactory.categorias();
        setup();
    }

    /**
     * Salva entidade no banco de dados.
     */
    @Override
    protected void save(Categoria categoria) {
        categoriaService.save(categoria);
    }

    /**
     * Cria entidade de acesso.
     *
     * @return Acesso - Novo acesso criado
     */
    @Override
    protected Categoria entity() {
        Categoria categoria = new Categoria();
        categoria.setNome(SCREEN.value("nome"));
        return categoria;
    }

    /**
     * Valida os campos da tela.
     */
    @Override
    protected boolean validate() {
        String nome = SCREEN.value("nome");
        boolean nomeNotBlank = UIValidation.notBlank(nome, "O nome do acesso deve ser preenchido!");
        return nomeNotBlank;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Categoria criada com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao criar Categoria: " + e.getMessage();
    }
}
