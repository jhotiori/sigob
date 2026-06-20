package org.javapi.sigob.controller.cadastro;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Moeda;
import org.javapi.sigob.model.service.MoedaService;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroMoedaScreen;

/**
 * Controller de cadastro de moedas.
 */
public final class CadastroMoedaController extends BaseCadastroController<Moeda, CadastroMoedaScreen> {

    /**
     * Serviço de moedas.
     *
     * @see MoedaService
     */
    private final MoedaService moedaService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro
     */
    public CadastroMoedaController(
            CadastroMoedaScreen screen) {
        super(screen);

        this.moedaService = ServiceFactory.moedas();

        setup();
    }

    /**
     * Salva moeda.
     *
     * @param moeda - Moeda
     */
    @Override
    protected void save(
            Moeda moeda) {
        moedaService.save(moeda);
    }

    /**
     * Cria entidade.
     *
     * @return Moeda - Moeda criada
     */
    @Override
    protected Moeda entity() {
        Moeda moeda = new Moeda();

        moeda.setNome(
                SCREEN.value("nome"));

        moeda.setCifrao(
                SCREEN.value("cifrao"));

        moeda.setSigla(
                SCREEN.value("sigla"));

        return moeda;
    }

    /**
     * Valida campos da tela.
     *
     * @return boolean - Resultado
     */
    @Override
    protected boolean validate() {
        String nome = SCREEN.value("nome");
        String cifrao = SCREEN.value("cifrao");
        String sigla = SCREEN.value("sigla");

        boolean nomeNotBlank = UIValidation.notBlank(
                nome,
                "O nome da moeda deve ser preenchido!");

        boolean cifraoNotBlank = UIValidation.notBlank(
                cifrao,
                "O cifrão da moeda deve ser preenchido!");

        boolean siglaNotBlank = UIValidation.notBlank(
                sigla,
                "A sigla da moeda deve ser preenchida!");

        return nomeNotBlank
                && cifraoNotBlank
                && siglaNotBlank;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Moeda criada com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(
            Throwable e) {
        return "Erro ao criar moeda: "
                + e.getMessage();
    }
}
