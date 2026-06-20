package org.javapi.sigob.controller.base;

import java.util.List;
import java.util.function.Supplier;

import org.javapi.sigob.view.v2.context.ScreenContext;
import org.javapi.sigob.view.v2.framework.components.entity.EntityContainer;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIEvents;
import org.javapi.sigob.view.v2.screens.cadastro.base.BaseCadastroScreen;

public abstract class BaseCadastroController<T, S extends BaseCadastroScreen> {
    /**
     * Tela de Cadastro de Acessos.
     *
     * @see CadastroAcessoScreen
     */
    protected final S SCREEN;

    /**
     * Construtor de BaseCadastroController.
     *
     * @param screen - Tela de Cadastro de Acessos
     */
    public BaseCadastroController(S screen) {
        this.SCREEN = screen;
    }

    /**
     * Realiza setup da tela de forma interna.
     */
    protected void setup() {
        SCREEN.onCriar(this::criar);
        SCREEN.onLimpar(this::limpar);
        SCREEN.onVoltar(this::voltar);

        UIEvents.bind(SCREEN.panel())
                .onShortcut("ctrl C", this::criar)
                .onShortcut("ctrl D", this::voltar)
                .onShortcut("ctrl L", this::limpar);
    }

    /**
     * Limpa campos da tela.
     */
    private void limpar() {
        SCREEN.form().clear();
    }

    /**
     * Retorna para dashboard.
     */
    private void voltar() {
        ScreenContext.show("dashboard");
    }

    /**
     * Realiza criação de acesso.
     */
    private void criar() {
        if (!validate()) {
            return;
        }

        try {
            T entity = entity();
            save(entity);
            UIDialogs.info(successMessage());
        } catch (Exception e) {
            UIDialogs.error(errorMessage(e));
        }
    }

    /**
     * Realiza load de entidades.
     *
     * @param container - ComboBox de entidades
     * @param supplier - Fornecedor de entidades
     */
    protected <E> void loadEntities(
        EntityContainer<E> container,
        Supplier<List<E>> supplier
    ) {
        SCREEN.onUpdate(() -> {
            container.setEntities(
                supplier.get()
            );
        });
    }

    /**
     * Valida campos da tela.
     *
     * @return boolean - true se os campos estiverem corretos
     */
    protected abstract boolean validate();

    /**
     * Cria entidade.
     *
     * @return T - Entidade
     */
    protected abstract T entity();

    /**
     * Realiza save da entidade no service.
     */
    protected abstract void save(T entity);

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    protected abstract String successMessage();

    /**
     * Mensagem de erro.
     *
     * @return String - Mensagem
     */
    protected abstract String errorMessage(Throwable e);
}
