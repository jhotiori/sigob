package org.javapi.sigob.controller.listagem;

import java.util.List;

import org.javapi.sigob.controller.listagem.base.BaseListagemController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.model.service.MoedaService;
import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroMoedaScreen;
import org.javapi.sigob.view.v2.screens.listagem.ListagemMoedaScreen;

/**
 * Controller da listagem de moedas.
 */
public final class ListagemMoedaController
        extends BaseListagemController<Moeda, ListagemMoedaScreen> {

    /**
     * Serviço de moedas.
     *
     * @see MoedaService
     */
    private final MoedaService moedaService;

    /**
     * Tela de cadastro.
     *
     * @see CadastroMoedaScreen
     */
    private final CadastroMoedaScreen cadastroScreen;

    /**
     * Construtor.
     *
     * @param screen         - Tela
     * @param cadastroScreen - Tela de cadastro
     */
    public ListagemMoedaController(
            ListagemMoedaScreen screen,
            CadastroMoedaScreen cadastroScreen) {
        super(screen);

        this.moedaService = ServiceFactory.moedas();
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
                            "O ID da moeda deve ser preenchido!");

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

                    return moedaService.findById(id)
                            .stream()
                            .toList();
                });

        bindSearch(
                "nome",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O nome da moeda deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return moedaService.findByNome(text);
                });

        bindSearch(
                "sigla",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "A sigla da moeda deve ser preenchida!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return moedaService.findBySigla(text)
                            .stream()
                            .toList();
                });

        bindSearch(
                "cifrao",
                () -> {
                    String text = SCREEN.searchText();

                    boolean isTextNotBlank = UIValidation.notBlank(
                            text,
                            "O cifrão da moeda deve ser preenchido!");

                    if (!isTextNotBlank) {
                        return List.of();
                    }

                    return moedaService.findByCifrao(text);
                });
    }

    /**
     * Busca todas as moedas.
     *
     * @return List<Moeda> - Moedas encontradas
     */
    @Override
    protected List<Moeda> findAll() {
        return moedaService.findAll();
    }

    /**
     * Remove moeda.
     *
     * @param moeda - Moeda
     */
    @Override
    protected void delete(Moeda moeda) {
        moedaService.delete(moeda);
    }

    /**
     * Realiza edição.
     *
     * @param moeda - Moeda
     */
    @Override
    protected void edit(Moeda moeda) {
        cadastroScreen.form().set(
                "nome",
                moeda.getNome());

        cadastroScreen.form().set(
                "cifrao",
                moeda.getCifrao());

        cadastroScreen.form().set(
                "sigla",
                moeda.getSigla());

        ScreenContext.show(
                cadastroScreen.id());
    }

    /**
     * Mensagem de entidade não selecionada.
     *
     * @return String - Mensagem
     */
    @Override
    protected String selectEntityMessage() {
        return "Selecione uma moeda primeiro!";
    }

    /**
     * Mensagem de confirmação.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteConfirmationMessage() {
        return "Deseja realmente excluir esta moeda?";
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String deleteSuccessMessage() {
        return "Moeda excluída com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro ocorrido
     * @return String - Mensagem
     */
    @Override
    protected String deleteErrorMessage(
            Throwable e) {
        return "Erro ao excluir moeda: " + e.getMessage();
    }
}
