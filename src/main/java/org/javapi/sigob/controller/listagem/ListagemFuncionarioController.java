package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.service.FuncionarioService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroFuncionarioScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemFuncionarioScreen;

/**
 * Controller da listagem de funcionários.
 */
public final class ListagemFuncionarioController extends BaseListagemController<Funcionario, ListagemFuncionarioScreen> {

    /**
     * Serviço de funcionários.
     *
     * @see FuncionarioService
     */
    private final FuncionarioService funcionarioService;

    /**
     * Tela de cadastro.
     *
     * @see CadastroFuncionarioScreen
     */
    private final CadastroFuncionarioScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         Tela
     * @param cadastroScreen Tela de cadastro
     */
    public ListagemFuncionarioController(
            ListagemFuncionarioScreen screen,
            CadastroFuncionarioScreen cadastroScreen) {
        super(screen);

        this.funcionarioService = ServiceFactory.funcionarios();
        this.cadastroScreen = cadastroScreen;

        setup();
        reload();
    }

    /**
     * Registra pesquisas.
     */
    @Override
    protected void bindSearches() {
        bindSearch(
                "id",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O ID deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    Integer id = Integer.parseInt(text);

                    boolean isIdValid = UIValidation.condition(
                            id > 0,
                            "O ID deve ser maior que zero!");

                    if (!isIdValid) {
                        return List.of();
                    }

                    return funcionarioService
                            .findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "nome",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O nome do funcionário deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return funcionarioService
                            .findByNome(text);
                });

        bindSearch(
                "documento",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O documento deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return funcionarioService
                            .findByDocumento(text);
                });

        bindSearch(
                "acesso",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O acesso deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return funcionarioService
                            .findByAcesso(text);
                }
        );
    }

    /**
     * Busca todos os funcionários.
     *
     * @return List<Funcionario> Funcionários
     */
    @Override
    protected List<Funcionario> findAll() {
        return funcionarioService.findAll();
    }

    /**
     * Remove funcionário.
     *
     * @param funcionario Funcionário
     */
    @Override
    protected void delete(
            Funcionario funcionario) {
        funcionarioService.delete(funcionario);
    }

    /**
     * Realiza edição.
     *
     * @param funcionario Funcionário
     */
    @Override
    protected void edit(
            Funcionario funcionario) {
        cadastroScreen.form().set(
                "nome",
                funcionario.getNome());

        cadastroScreen.form().set(
                "codigo",
                funcionario.getCodigo());

        cadastroScreen.form().setEntity(
                "documento",
                funcionario.getDocumento());

        cadastroScreen.acessosList()
                .setSelectedEntities(
                        funcionario.getAcessos());

        ScreenContext.show(
                cadastroScreen.id());
    }

    /**
     * Mensagem de entidade não selecionada.
     *
     * @return String Mensagem
     */
    @Override
    protected String selectEntityMessage() {
        return "Selecione um funcionário primeiro!";
    }

    /**
     * Mensagem de confirmação.
     *
     * @return String Mensagem
     */
    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir este funcionário?";
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String Mensagem
     */
    @Override
    protected String deleteSuccessMessage() {
        return "Funcionário excluído com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e Erro ocorrido
     * @return String Mensagem
     */
    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir funcionário: "
                + e.getMessage();
    }
}
