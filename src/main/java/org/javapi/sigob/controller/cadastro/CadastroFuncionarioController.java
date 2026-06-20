package org.javapi.sigob.controller.cadastro;

import java.util.List;

import org.javapi.sigob.controller.base.BaseCadastroController;
import org.javapi.sigob.core.ServiceFactory;
import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.entity.Cliente;
import org.javapi.sigob.model.entity.Funcionario;
import org.javapi.sigob.model.service.AcessoService;
import org.javapi.sigob.model.service.ClienteService;
import org.javapi.sigob.model.service.DocumentoService;
import org.javapi.sigob.model.service.FuncionarioService;
import org.javapi.sigob.view.v2.framework.ui.UIValidation;
import org.javapi.sigob.view.v2.screens.cadastro.CadastroFuncionarioScreen;

/**
 * Controller de cadastro de funcionários.
 */
public final class CadastroFuncionarioController extends BaseCadastroController<Funcionario, CadastroFuncionarioScreen> {

    /**
     * Serviço de funcionários.
     *
     * @see FuncionarioService
     */
    private final FuncionarioService funcionarioService;

    /**
     * Serviço de documentos.
     *
     * @see DocumentoService
     */
    private final DocumentoService documentoService;

    /**
     * Serviço de acessos.
     *
     * @see AcessoService
     */
    private final AcessoService acessoService;

    /**
     * Serviço de cliente.
     *
     * @see ClienteService
     */
    private final ClienteService clienteService;

    /**
     * Construtor.
     *
     * @param screen - Tela de cadastro
     */
    public CadastroFuncionarioController(
            CadastroFuncionarioScreen screen) {
        super(screen);

        this.funcionarioService = ServiceFactory.funcionarios();
        this.documentoService = ServiceFactory.documentos();
        this.acessoService = ServiceFactory.acessos();
        this.clienteService = ServiceFactory.clientes();

        loadEntities(
                        SCREEN.documentoBox(),
                        documentoService::findAll
                    );

        loadEntities(
                        SCREEN.acessosList(),
                        acessoService::findAll
                );
        setup();
    }

    /**
     * Salva funcionário.
     *
     * @param funcionario - Funcionário
     */
    @Override
    protected void save(Funcionario funcionario) {
        funcionarioService.save(funcionario);

        Cliente cliente = new Cliente();
        cliente.setNome(funcionario.getNome());
        cliente.setDocumento(funcionario.getDocumento());

        clienteService.save(cliente);
    }

    /**
     * Cria entidade.
     *
     * @return Funcionario - Funcionário criado
     */
    @Override
    protected Funcionario entity() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(SCREEN.value("nome"));
        funcionario.setCodigo(SCREEN.value("codigo"));
        funcionario.setDocumento(SCREEN.entity("documento"));

        SCREEN.entities("acessos").forEach(acesso -> {
                funcionario.addAcesso((Acesso) acesso);
        });

        return funcionario;
    }

    /**
     * Valida campos da tela.
     *
     * @return boolean - Resultado
     */
    @Override
    protected boolean validate() {
        String nome = SCREEN.value("nome");
        String codigo = SCREEN.value("codigo");

        boolean nomeNotBlank = UIValidation.notBlank(
                nome,
                "O nome do funcionário deve ser preenchido!"
        );

        boolean codigoNotBlank = UIValidation.notBlank(
                codigo,
                "O código do funcionário deve ser preenchido!"
        );

        List<Acesso> acessos = SCREEN.entities("acessos");
        boolean acessosNotEmpy = UIValidation.notEmpty(
                acessos,
                "Selecione ao menos um acesso!"
        );

        return nomeNotBlank && codigoNotBlank && acessosNotEmpy;
    }

    /**
     * Mensagem de sucesso.
     *
     * @return String - Mensagem
     */
    @Override
    protected String successMessage() {
        return "Funcionário criado com sucesso!";
    }

    /**
     * Mensagem de erro.
     *
     * @param e - Erro
     * @return String - Mensagem
     */
    @Override
    protected String errorMessage(Throwable e) {
        return "Erro ao criar Funcionário: " + e.getMessage();
    }
}
