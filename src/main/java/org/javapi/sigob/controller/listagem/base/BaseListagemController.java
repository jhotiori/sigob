package org.javapi.sigob.controller.listagem.base;

import java.util.List;
import java.util.concurrent.Callable;

import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.screens.listagem.base.BaseListagemScreen;

/**
 * Controller base para telas de listagem.
 *
 * @param <T> Tipo da entidade
 * @param <S> Tipo da tela
 */
public abstract class BaseListagemController<
        T,
        S extends BaseListagemScreen<T>
> {

    /**
     * Tela controlada.
     */
    protected final S SCREEN;

    /**
     * Construtor.
     *
     * @param screen - Tela
     */
    protected BaseListagemController(S screen) {
        this.SCREEN = screen;
    }

    /**
     * Realiza setup da tela.
     */
    protected void setup() {
        SCREEN.onVoltar(this::voltar);
        SCREEN.onEditar(this::editar);
        SCREEN.onExcluir(this::excluir);

        UIEvents.bind(SCREEN.panel())
                .onShortcut("ctrl R", this::reload)
                .onShortcut("ctrl D", this::voltar)
                .onShortcut("ctrl E", this::excluir);

        bindSearches();
        bindSearch(
            "todos",
            () -> findAll()
        );
    }

    /**
     * Atualiza entidades exibidas.
     */
    protected void reload() {
        SCREEN.setEntities(
                findAll()
        );
    }

    /**
     * Retorna para dashboard.
     */
    private void voltar() {
        ScreenContext.show("dashboard");
    }

    /**
     * Realiza edição da entidade selecionada.
     */
    private void editar() {
        T entity = SCREEN.getSelectedEntity();

        if (entity == null) {
            UIDialogs.info(
                    selectEntityMessage()
            );

            return;
        }

        edit(entity);
    }

    /**
     * Realiza exclusão da entidade selecionada.
     */
    private void excluir() {
        T entity = SCREEN.getSelectedEntity();

        if (entity == null) {
            UIDialogs.info(
                    selectEntityMessage()
            );

            return;
        }

        boolean confirmed = UIDialogs.confirm(
                deleteConfirmationMessage()
        );

        if (!confirmed) {
            return;
        }

        try {
            delete(entity);

            UIDialogs.info(
                    deleteSuccessMessage()
            );

            reload();
        } catch (Exception e) {
            UIDialogs.error(
                    deleteErrorMessage(e)
            );
        }
    }

    /**
     * Registra pesquisa.
     *
     * @param identifier - Identificador da pesquisa
     * @param action - Ação executada
     */
    protected void bindSearch(
        String identifier,
        Callable<List<T>> action
    ) {
        SCREEN.onSearch(
            identifier,
            () -> {
                try {
                    SCREEN.setEntities(
                        action.call()
                    );
                } catch (Exception e) {
                    UIDialogs.error(e.getMessage());
                }
            }
        );
    }

    /**
     * Busca todas as entidades.
     *
     * @return List<T> - Entidades
     */
    protected abstract List<T> findAll();

    /**
     * Realiza exclusão da entidade.
     *
     * @param entity - Entidade
     */
    protected abstract void delete(T entity);

    /**
     * Realiza edição da entidade.
     *
     * @param entity - Entidade
     */
    protected abstract void edit(T entity);

    /**
     * Registra ações de pesquisa.
     */
    protected abstract void bindSearches();

    /**
     * Mensagem exibida quando nenhuma
     * entidade estiver selecionada.
     *
     * @return String - Mensagem
     */
    protected abstract String selectEntityMessage();

    /**
     * Mensagem de confirmação da exclusão.
     *
     * @return String - Mensagem
     */
    protected abstract String deleteConfirmationMessage();

    /**
     * Mensagem de sucesso da exclusão.
     *
     * @return String - Mensagem
     */
    protected abstract String deleteSuccessMessage();

    /**
     * Mensagem de erro da exclusão.
     *
     * @param e - Erro ocorrido
     * @return String - Mensagem
     */
    protected abstract String deleteErrorMessage(
            Throwable e
    );
}
