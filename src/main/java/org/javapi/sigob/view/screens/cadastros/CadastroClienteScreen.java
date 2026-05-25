package org.javapi.sigob.view.screens.cadastros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Cliente;
import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.ClientesTableModel;
import org.javapi.sigob.view.popups.PopupValues;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

/**
 * Tela de cadastro de clientes.
 */
public final class CadastroClienteScreen extends BaseScreen {

    /**
     * Formatador de data.
     *
     * @see {@link DateTimeFormatter}
     */
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Campo de nome.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(32);
    });

    /**
     * Campo de data de nascimento.
     *
     * @see {@link JTextField}
     */
    private final JTextField dataNascimentoField = UI.textField(field -> {
        field.setColumns(16);
    });

    /**
     * Campo de documento.
     *
     * @see {@link JTextField}
     */
    private final JTextField documentoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de tipo do documento.
     *
     * @see {@link JTextField}
     */
    private final JTextField tipoDocumentoField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * Campo de pesquisa.
     *
     * @see {@link JTextField}
     */
    private final JTextField pesquisaField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * ComboBox de tipo de pesquisa.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> tipoPesquisaCombo = UI.comboBox(
            "Nome",
            "Documento"
    );

    /**
     * Modelo da tabela.
     */
    private final ClientesTableModel tableModel
            = new ClientesTableModel();

    /**
     * Tabela de clientes.
     *
     * @see {@link JTable}
     */
    private final JTable table = UI.table(tableModel);

    /**
     * Botão de cadastro.
     *
     * @see {@link JButton}
     */
    private final JButton cadastrarButton = UI.button("Cadastrar");

    /**
     * Botão de limpeza.
     *
     * @see {@link JButton}
     */
    private final JButton limparButton = UI.button("Limpar");

    /**
     * Botão de pesquisa.
     *
     * @see {@link JButton}
     */
    private final JButton pesquisarButton = UI.button("Pesquisar");

    /**
     * Cria tela de cadastro de clientes.
     */
    public CadastroClienteScreen() {
        super("cadastro-cliente");

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    Cliente cliente = new Cliente();

                    cliente.setNome(nomeField.getText());
                    cliente.setDataNascimento(parseDataNascimento());
                    cliente.setDocumento(createDocumento());

                    ApplicationContext
                            .getClienteService()
                            .save(cliente);

                    Popups.success(
                            "Cliente cadastrado com sucesso!"
                    );

                    clearForm();
                } catch (DateTimeParseException e) {
                    Popups.error(
                            "Data inválida! Utilize o formato DD/MM/YYYY."
                    );
                } catch (Exception e) {
                    Popups.error(e.getMessage());
                }
            });
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });

        Events.mouse(pesquisarButton, mouse -> {
            mouse.onClicked(this::pesquisar);
        });

        Events.mouse(table, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    editarSelecionado();
                }
            });
        });

        listarTodos();
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz da tela
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildForm())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Constrói formulário principal.
     *
     * @return JPanel - Formulário construído
     */
    private JPanel buildForm() {
        return UI.column()
                .add(
                        UIScreen.title("Cadastro de Clientes"),
                        UIScreen.subtitle(
                                "Gerencia clientes cadastrados e seus documentos vinculados."
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Cadastro",
                                buildCadastro()
                        )
                )
                .glue()
                .add(
                        UIScreen.section(
                                "Atualizar",
                                buildAtualizar()
                        )
                )
                .build();
    }

    /**
     * Constrói seção de cadastro.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildCadastro() {
        return UI.column()
                .add(
                        UIForm.field(
                                UIForm.fieldLabel("Nome [obrigatorio]"),
                                nomeField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Data de Nascimento (DD/MM/YYYY) [opcional]"
                                ),
                                dataNascimentoField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Documento [opcional]"),
                                documentoField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel(
                                        "Tipo do Documento [opcional]"
                                ),
                                tipoDocumentoField
                        )
                )
                .glue()
                .add(
                        UIScreen.actions(
                                cadastrarButton,
                                limparButton
                        )
                )
                .build();
    }

    /**
     * Constrói seção de atualização.
     *
     * @return JPanel - Painel construído
     */
    private JPanel buildAtualizar() {
        return UI.column()
                .add(
                        UI.row()
                                .add(
                                        tipoPesquisaCombo,
                                        pesquisaField
                                )
                                .glue()
                                .add(
                                        pesquisarButton
                                )
                                .build(),
                        UI.scroll(table)
                )
                .build();
    }

    /**
     * Pesquisa clientes.
     */
    private void pesquisar() {
        try {
            String pesquisa = pesquisaField.getText().trim();

            if (pesquisa.isBlank()) {
                listarTodos();

                return;
            }

            String tipoPesquisa
                    = (String) tipoPesquisaCombo.getSelectedItem();

            if ("Documento".equals(tipoPesquisa)) {
                List<Cliente> clientes = ApplicationContext
                        .getClienteService()
                        .findByDocumento(pesquisa);

                if (clientes.isEmpty()) {
                    Popups.warn(
                            "Cliente nao encontrado!"
                    );
                }

                setResultados(
                        clientes
                );

                return;
            }

            setResultados(
                    ApplicationContext
                            .getClienteService()
                            .findByNome(pesquisa)
            );
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Lista todos os clientes.
     */
    private void listarTodos() {
        try {
            setResultados(
                    ApplicationContext
                            .getClienteService()
                            .findAll()
            );
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Edita cliente selecionado.
     */
    private void editarSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            Popups.warn(
                    "Selecione um cliente!"
            );

            return;
        }

        Cliente cliente = tableModel.getCliente(row);

        if (cliente == null) {
            Popups.warn(
                    "Cliente inválido!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Você deseja editar esse(a) cliente?"
        );

        if (!confirmacao) {
            return;
        }

        try {
            String nome = Popups.input(
                    "Atualizar Nome",
                    """
                    Valor atual: %s

                    Informe o novo nome.
                    Deixe vazio para manter o valor atual.
                    """
                            .formatted(cliente.getNome())
            );

            if (PopupValues.wasCancelled(nome)) {
                return;
            }

            if (!PopupValues.shouldKeep(nome)) {
                cliente.setNome(nome);
            }

            String dataNascimento = Popups.input(
                    "Atualizar Data de Nascimento",
                    """
                    Valor atual: %s

                    Informe a nova data no formato DD/MM/YYYY.
                    Deixe vazio para manter o valor atual.
                    Digite 'null' para limpar.
                    """
                            .formatted(
                                    cliente.getDataNascimento() != null
                                    ? cliente.getDataNascimento()
                                            .format(DATE_FORMATTER)
                                    : "-"
                            )
            );

            if (PopupValues.wasCancelled(dataNascimento)) {
                return;
            }

            if (PopupValues.shouldClear(dataNascimento)) {
                cliente.setDataNascimento(null);
            } else if (!PopupValues.shouldKeep(dataNascimento)) {
                cliente.setDataNascimento(
                        LocalDate.parse(
                                dataNascimento,
                                DATE_FORMATTER
                        )
                );
            }

            updateDocumento(cliente);

            ApplicationContext
                    .getClienteService()
                    .update(cliente);

            Popups.success(
                    "Cliente atualizado com sucesso!"
            );

            pesquisar();
        } catch (DateTimeParseException e) {
            Popups.error(
                    "Data inválida! Utilize o formato DD/MM/YYYY."
            );
        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Atualiza documento do cliente.
     *
     * @param cliente - Cliente selecionado
     */
    private void updateDocumento(Cliente cliente) {
        Documento documentoAtual = cliente.getDocumento();

        String documento = Popups.input(
                "Atualizar Documento",
                """
                Valor atual: %s

                Informe o novo documento.
                Deixe vazio para manter o valor atual.
                Digite 'null' para limpar.
                """
                        .formatted(
                                documentoAtual != null
                                        ? documentoAtual.getDocumento()
                                        : "-"
                        )
        );

        if (PopupValues.wasCancelled(documento)) {
            Popups.warn(
                 "Operação cancelada."
            );
            return;
        }

        String tipoDocumento = Popups.input(
                "Atualizar Tipo do Documento",
                """
                Valor atual: %s

                Informe o novo tipo do documento.
                Deixe vazio para manter o valor atual.
                Digite 'null' para limpar.
                """
                        .formatted(
                                documentoAtual != null
                                        ? documentoAtual.getTipo()
                                        : "-"
                        )
        );

        if (PopupValues.wasCancelled(tipoDocumento)) {
            Popups.warn(
                    "Operação cancelada."
            );
            return;
        }

        boolean limparDocumento
                = PopupValues.shouldClear(documento);

        boolean limparTipo
                = PopupValues.shouldClear(tipoDocumento);

        if (limparDocumento != limparTipo) {
            Popups.warn(
                "Documento e Tipo do Documento devem ser limpos juntos!"
            );
            return;
        }

        if (limparDocumento) {
            cliente.setDocumento(null);

            return;
        }

        if (PopupValues.shouldKeep(documento)
                && PopupValues.shouldKeep(tipoDocumento)) {
            return;
        }

        Documento documentoEntity = documentoAtual;

        if (documentoEntity == null) {
            documentoEntity = new Documento();
        }

        if (!PopupValues.shouldKeep(documento)) {
            documentoEntity.setDocumento(documento);
        }

        if (!PopupValues.shouldKeep(tipoDocumento)) {
            documentoEntity.setTipo(tipoDocumento);
        }

        if (documentoAtual == null) {
            ApplicationContext
                    .getDocumentoService()
                    .save(documentoEntity);
        } else {
            ApplicationContext
                    .getDocumentoService()
                    .update(documentoEntity);
        }

        cliente.setDocumento(documentoEntity);
    }

    /**
     * Define resultados da tabela.
     *
     * @param clientes - Clientes encontrados
     */
    private void setResultados(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            clearResults();

            Popups.warn(
                    "Nenhum cliente encontrado!"
            );

            return;
        }

        tableModel.setClientes(clientes);
    }

    /**
     * Limpa resultados da tabela.
     */
    private void clearResults() {
        tableModel.setClientes(List.of());
    }

    /**
     * Cria documento opcional do cliente.
     *
     * @return Documento - Documento criado ou null
     */
    private Documento createDocumento() {
        String documentoValor = documentoField.getText().trim();
        String tipoDocumento = tipoDocumentoField.getText().trim();

        if (documentoValor.isBlank() && tipoDocumento.isBlank()) {
            return null;
        }

        if (documentoValor.isBlank() || tipoDocumento.isBlank()) {
            Popups.error(
                    "Documento e Tipo do Documento devem ser preenchidos juntos!"
            );

            return null;
        }

        Documento documento = new Documento();

        documento.setDocumento(documentoValor);
        documento.setTipo(tipoDocumento);

        ApplicationContext
                .getDocumentoService()
                .save(documento);

        return documento;
    }

    /**
     * Realiza parse da data de nascimento.
     *
     * @return LocalDate - Data convertida
     */
    private LocalDate parseDataNascimento() {
        String data = dataNascimentoField.getText().trim();

        if (data.isBlank()) {
            return null;
        }

        return LocalDate.parse(data, DATE_FORMATTER);
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UIForm.clearFields(
                nomeField,
                dataNascimentoField,
                documentoField,
                tipoDocumentoField
        );
    }

}
