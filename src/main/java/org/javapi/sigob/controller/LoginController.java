package org.javapi.sigob.controller;

import java.util.List;

import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.core.bootstrap.ApplicationBootstrap;
import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.entity.Documento;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.service.AcessoService;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.model.service.FuncionarioService;

import org.javapi.sigob.view.v2.context.WindowContext;
import org.javapi.sigob.view.v2.dialogs.AcessoDialog;
import org.javapi.sigob.view.v2.dialogs.DocumentoDialog;
import org.javapi.sigob.view.v2.dialogs.base.BaseEntityDialog;
import org.javapi.sigob.view.v2.framework.ui.UIDialogs;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;

import org.javapi.sigob.view.v2.screens.LoginScreen;


public final class LoginController {

    /**
     * Tela do controller.
     *
     * @see LoginScreen
     */
    private final LoginScreen screen;

    /**
     * Serviço de Funcionarios.
     *
     * @see FuncionarioService
     */
    private final FuncionarioService funcionarioService;

    /**
     * Serviço de Documentos.
     *
     * @see DocumentoService
     */
    private final DocumentoService documentoService;

    /**
     * Serviço de Acessos.
     *
     * @see AcessoService
     */
    private final AcessoService acessoService;

    /**
     * Construtor do LoginController.
     *
     * @param screen - Tela
     * @param service - Serviço
     */
    public LoginController(LoginScreen screen) {
        this.screen = screen;
        this.funcionarioService = ServiceFactory.funcionarios();
        this.documentoService = ServiceFactory.documentos();
        this.acessoService = ServiceFactory.acessos();
        setup();
    }

    /**
     * Realiza setup da tela de forma interna.
     */
    private void setup() {
        screen.onLogin(this::login);
        screen.onRegistrar(this::registrar);
        screen.onSair(this::sair);
    }

    /**
     * Realiza login.
     */
    private void login() {
        String usuario = screen.getUsuario();
        String codigo = screen.getCodigo();

        if (!validate(usuario, codigo)) {
            return;
        }

        Funcionario funcionario = authenticate(
            usuario,
            codigo
        );

        if (funcionario == null) {
            UIDialogs.error("Usuário ou Código incorretos!");
            return;
        }

        ApplicationBootstrap.bootstrap(funcionario);
    }

    /**
     * Realiza criação de usuário na tela de login.
     */
    private void registrar() {
        String usuario = screen.getUsuario();
        String codigo = screen.getCodigo();
        if (!validate(usuario, codigo)) {
            return;
        }

        Funcionario funcionario = getFuncionarioByNome(usuario);
        if (funcionario != null) {
            UIDialogs.error("Usuário já cadastrado!");
            return;
        }

        Documento documento = select(
            new DocumentoDialog(),
            documentoService.findAll()
        );

        if (documento == null) {
            return;
        }

        Acesso acesso = select(
            new AcessoDialog(),
            acessoService.findAll()
        );

        if (acesso == null) {
            return;
        }

        Funcionario novoFuncionario = createFuncionario(
                usuario,
                codigo,
                documento,
                acesso
        );

        try {
            funcionarioService.save(novoFuncionario);
            UIDialogs.info("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            UIDialogs.error("Erro ao cadastrar usuário: " + e.getMessage());
        }

    }

    /**
     * Realiza autenticação.
     *
     * @param usuario - Usuário
     * @param codigo  - Código
     * @return Funcionario - Funcionario autenticado
     */
    private Funcionario authenticate(
        String usuario,
        String codigo
    ) {
        Funcionario funcionario = getFuncionarioByNome(usuario);

        if (funcionario == null) {
            return null;
        }

        if (!codigo.equals(funcionario.getCodigo())) {
            return null;
        }

        return funcionario;
    }

    /**
     * Callback de saida do login.
     */
    private void sair() {
        WindowContext.disposeCurrentWindow();
        System.exit(0);
    }

    /**
     * Seleciona um entity.
     *
     * @param dialog - Dialogo
     * @param entities - Entidades
     * @return T - Entity selecionada
     */
    private <T> T select(
        BaseEntityDialog<T> dialog,
        Iterable<T> entities
    ) {
        dialog.setEntities(entities);

        boolean confirmed = UIDialogs.custom(
                dialog.title(),
                dialog
        );

        if (!confirmed) {
            return null;
        }

        return dialog.getSelectedEntity();
    }


    /**
     * Cria um novo funcionário.
     *
     * @param nome - Nome
     * @param codigo - Código
     * @param documento - Documento
     * @param acesso - Acesso
     * @return Funcionário
     */
    private Funcionario createFuncionario(
            String nome,
            String codigo,
            Documento documento,
            Acesso acesso
    ) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCodigo(codigo);
        funcionario.setDocumento(documento);
        funcionario.addAcesso(acesso);

        return funcionario;
    }

    /**
     * Busca um funcionário pelo nome.
     *
     * @param nome - Nome
     * @return Funcionário
     */
    private Funcionario getFuncionarioByNome(String nome) {
        List<Funcionario> funcionarios = funcionarioService.findByNome(nome);
        if (funcionarios.isEmpty()) {
            return null;
        }

        return funcionarios.get(0);
    }

    /**
     * Valida os campos da tela.
     */
    private boolean validate(
            String usuario,
            String codigo
    ) {
        boolean usuarioNotEmpty = UIValidation.notBlank(usuario, "Digite um usuário!");
        boolean codigoNotEmpty = UIValidation.notBlank(codigo, "Digite o código de usuário!");
        return usuarioNotEmpty && codigoNotEmpty;
    }
}
