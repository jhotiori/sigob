package org.javapi.sigob.view.screens.cadastros;

import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.Messages;
import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;
import org.javapi.sigob.view.styles.Fonts;
import org.javapi.sigob.view.styles.Spacing;

/**
 * Tela de cadastro de funcionários.
 */
public final class CadastroFuncionarioScreen extends BaseScreen {

    /**
     * Campo de código.
     *
     * @see {@link JTextField}
     */
    private final JTextField codigoField = UI.textField(field -> {
        field.setColumns(32);
    });

    /**
     * Campo de nome.
     *
     * @see {@link JTextField}
     */
    private final JTextField nomeField = UI.textField(field -> {
        field.setColumns(64);
    });

    /**
     * ComboBox de documentos.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> documentoBox = UI.comboBox();

    /**
     * Lista de acessos.
     *
     * @see {@link JList}
     */
    private final JList<String> acessosList = UI.list();

    /**
     * Botão de cadastro.
     *
     * @see {@link JButton}
     */
    private final JButton cadastrarButton = UI.button("Cadastrar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Botão de limpeza.
     *
     * @see {@link JButton}
     */
    private final JButton limparButton = UI.button("Limpar", button -> {
        button.setFont(Fonts.MEDIUM_BOLD);
    });

    /**
     * Mapa de documentos.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> documentosMap
            = new LinkedHashMap<>();

    /**
     * Mapa de acessos.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> acessosMap
            = new LinkedHashMap<>();

    /**
     * Cria tela de cadastro de funcionários.
     */
    public CadastroFuncionarioScreen() {
        super("cadastro-funcionario");

        init();
        setup();
    }

    /**
     * Realiza setup da tela.
     */
    @Override
    protected void setup() {
        update();

        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(() -> {
                try {
                    String codigo = codigoField.getText();
                    String nome = nomeField.getText();

                    String documentoSelecionado
                            = documentoBox.getSelectedItem().toString();

                    Integer documentoId
                            = documentosMap.get(documentoSelecionado);

                    Documento documento = ApplicationContext
                            .getDocumentoService()
                            .findById(documentoId)
                            .orElseThrow();

                    Funcionario funcionario = new Funcionario();

                    funcionario.setCodigo(codigo);
                    funcionario.setNome(nome);
                    funcionario.setDocumento(documento);

                    List<String> acessosSelecionados
                            = acessosList.getSelectedValuesList();

                    acessosSelecionados.forEach(nomeAcesso -> {
                        Integer acessoId = acessosMap.get(nomeAcesso);

                        Acesso acesso = ApplicationContext
                                .getAcessoService()
                                .findById(acessoId)
                                .orElseThrow();

                        funcionario.addAcesso(acesso);
                    });

                    ApplicationContext
                            .getFuncionarioService()
                            .save(funcionario);

                    Messages.success(
                            "Funcionário '%s' cadastrado com sucesso!"
                                    .formatted(nome)
                    );

                    clearForm();
                } catch (Exception e) {
                    e.printStackTrace();
                    Messages.error(e.getMessage());
                }
            });
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });
    }

    /**
     * Constrói interface da tela.
     *
     * @return JPanel - Painel raiz
     */
    @Override
    protected JPanel build() {
        return UI.border()
                .center(buildForm())
                .padding(Spacing.XL)
                .build();
    }

    /**
     * Atualiza dados da tela.
     */
    @Override
    public void update() {
        updateDocumentos();
        updateAcessos();
    }

    /**
     * Atualiza documentos disponíveis.
     */
    private void updateDocumentos() {
        List<Documento> documentos = ApplicationContext
                .getDocumentoService()
                .findAll();

        documentosMap.clear();

        documentos.forEach(documento -> {
            documentosMap.put(
                    "%s (%s)".formatted(
                            documento.getDocumento(),
                            documento.getTipo()
                    ),
                    documento.getId()
            );
        });

        documentoBox.setModel(
                new DefaultComboBoxModel<>(
                        documentosMap.keySet().toArray(new String[0])
                )
        );
    }

    /**
     * Atualiza acessos disponíveis.
     */
    private void updateAcessos() {
        List<Acesso> acessos = ApplicationContext
                .getAcessoService()
                .findAll();

        acessosMap.clear();

        DefaultListModel<String> model = new DefaultListModel<>();

        acessos.forEach(acesso -> {
            acessosMap.put(
                    acesso.getNome(),
                    acesso.getId()
            );

            model.addElement(acesso.getNome());
        });

        acessosList.setModel(model);
    }

    /**
     * Constrói formulário principal.
     *
     * @return JPanel - Formulário construído
     */
    private JPanel buildForm() {
        return UI.column()
                .add(buildTitle(), buildSubtitle())
                .glue()
                .add(
                        UI.field(
                                UI.fieldLabel(
                                        "Código [Senha de acesso, curta e rapida]"
                                ),
                                codigoField
                        ),
                        UI.field(
                                UI.fieldLabel("Nome"),
                                nomeField
                        ),
                        UI.field(
                                UI.fieldLabel("Documento"),
                                documentoBox
                        ),
                        UI.field(
                                UI.fieldLabel("Acessos"),
                                UI.scroll(acessosList)
                        )
                )
                .glue()
                .add(
                        UI.actions(
                                cadastrarButton,
                                limparButton
                        )
                )
                .build();
    }

    /**
     * Constrói título da tela.
     *
     * @return JLabel - Título construído
     */
    private JLabel buildTitle() {
        return UI.label("Cadastro de Funcionários", label -> {
            label.setFont(Fonts.TITLE_MEDIUM);
        });
    }

    /**
     * Constrói subtítulo da tela.
     *
     * @return JLabel - Subtítulo construído
     */
    private JLabel buildSubtitle() {
        return UI.subtitle(
                "Gerencia funcionários e permissões de acesso do sistema."
        );
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UI.clearFields(
                codigoField,
                nomeField
        );

        documentoBox.setSelectedIndex(0);

        acessosList.clearSelection();
    }

}
