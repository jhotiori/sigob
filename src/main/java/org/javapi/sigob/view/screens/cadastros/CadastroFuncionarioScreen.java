package org.javapi.sigob.view.screens.cadastros;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javapi.sigob.entity.Acesso;
import org.javapi.sigob.entity.Documento;
import org.javapi.sigob.entity.Funcionario;
import org.javapi.sigob.view.ApplicationContext;
import org.javapi.sigob.view.Events;
import org.javapi.sigob.view.base.BaseScreen;
import org.javapi.sigob.view.models.FuncionarioTableModel;
import org.javapi.sigob.view.popups.PopupValues;
import org.javapi.sigob.view.popups.Popups;
import org.javapi.sigob.view.styles.Spacing;
import org.javapi.sigob.view.ui.UI;
import org.javapi.sigob.view.ui.UIForm;
import org.javapi.sigob.view.ui.UIScreen;

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
     * Campo de pesquisa.
     *
     * @see {@link JTextField}
     */
    private final JTextField pesquisaField = UI.textField(field -> {
        field.setColumns(24);
    });

    /**
     * ComboBox de modo de pesquisa.
     *
     * @see {@link JComboBox}
     */
    private final JComboBox<String> pesquisaModoCombo = UI.comboBox(
            "Nome",
            "Documento"
    );

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
     * Modelo da tabela de funcionários.
     *
     * @see {@link FuncionarioTableModel}
     */
    private final FuncionarioTableModel tableModel
            = new FuncionarioTableModel();

    /**
     * Tabela de funcionários.
     *
     * @see {@link JTable}
     */
    private final JTable table = UI.table(tableModel);

    /**
     * Mapa de documentos.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<String, Integer> documentosMap
            = new LinkedHashMap<>();

    /**
     * Mapa reverso de documentos.
     *
     * @see {@link LinkedHashMap}
     */
    private final LinkedHashMap<Integer, String> documentosReverseMap
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

        initialize();
    }

    /**
     * Realiza setup interno da tela.
     */
    @Override
    protected void setup() {
        refresh();

        loadFuncionarios();

        Events.mouse(cadastrarButton, mouse -> {
            mouse.onClicked(this::cadastrarFuncionario);
        });

        Events.mouse(limparButton, mouse -> {
            mouse.onClicked(this::clearForm);
        });

        Events.mouse(pesquisarButton, mouse -> {
            mouse.onClicked(this::pesquisarFuncionarios);
        });

        Events.mouse(table, mouse -> {
            mouse.onClicked(event -> {
                if (event.getClickCount() == 2) {
                    editarSelecionado();
                }
            });
        });
    }

    /**
     * Atualiza dados dinâmicos da tela.
     */
    @Override
    public void refresh() {
        refreshDocumentos();
        refreshAcessos();
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
                        UIScreen.title("Cadastro de Funcionários"),
                        UIScreen.subtitle(
                                "Gerencia funcionários e permissões de acesso do sistema."
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
                                UIForm.fieldLabel(
                                        "Código (Senha de acesso, curta e rápida) [obrigatorio]"
                                ),
                                codigoField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Nome [obrigatorio]"),
                                nomeField
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Documento"),
                                documentoBox
                        ),
                        UIForm.field(
                                UIForm.fieldLabel("Acessos"),
                                UI.scroll(acessosList)
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
                                        pesquisaModoCombo,
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
     * Realiza cadastro de funcionário.
     */
    private void cadastrarFuncionario() {
        try {
            String codigo = codigoField.getText().trim();

            if (codigo.isBlank()) {
                Popups.warn("Informe um código!");

                return;
            }

            String nome = nomeField.getText().trim();

            if (nome.isBlank()) {
                Popups.warn("Informe um nome!");

                return;
            }

            String documentoSelecionado = (String) documentoBox
                    .getSelectedItem();

            Integer documentoId = documentosMap.get(
                    documentoSelecionado
            );

            Documento documento = ApplicationContext
                    .getDocumentoService()
                    .findById(documentoId)
                    .orElseThrow();

            Funcionario funcionario = new Funcionario();

            funcionario.setCodigo(codigo);
            funcionario.setNome(nome);
            funcionario.setDocumento(documento);

            List<String> acessosSelecionados = acessosList
                    .getSelectedValuesList();

            if (acessosSelecionados.isEmpty()) {
                Popups.warn(
                        "Selecione ao menos um acesso!"
                );

                return;
            }

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

            Popups.success(
                    "Funcionário cadastrado com sucesso!"
            );

            clearForm();
            loadFuncionarios();

        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null) {
                String lower = message.toLowerCase();

                if (lower.contains("unique")
                        || lower.contains("duplic")
                        || lower.contains("constraint")) {

                    Popups.error("""
                                 Já existe um funcionário com esse código.
                                 Utilize outro código único.
                                 """);

                    return;
                }
            }

            Popups.error(e.getMessage());
        }
    }

    /**
     * Pesquisa funcionários.
     */
    private void pesquisarFuncionarios() {
        try {
            String pesquisa = pesquisaField
                    .getText()
                    .trim();

            if (pesquisa.isBlank()) {
                loadFuncionarios();

                return;
            }

            String modo = (String) pesquisaModoCombo
                    .getSelectedItem();

            if ("Documento".equalsIgnoreCase(modo)) {
                List<Funcionario> funcionarios = ApplicationContext
                        .getFuncionarioService()
                        .findAll()
                        .stream()
                        .filter(funcionario -> {
                            Documento documento
                                    = funcionario.getDocumento();

                            return documento != null
                                    && documento.getDocumento() != null
                                    && documento.getDocumento()
                                            .toLowerCase()
                                            .contains(
                                                    pesquisa.toLowerCase()
                                            );
                        })
                        .toList();

                setResultados(funcionarios);

                return;
            }

            setResultados(
                    ApplicationContext
                            .getFuncionarioService()
                            .findByNome(pesquisa)
            );

        } catch (Exception e) {
            Popups.error(e.getMessage());
        }
    }

    /**
     * Edita funcionário selecionado.
     */
    private void editarSelecionado() {
        int row = table.getSelectedRow();

        if (row < 0) {
            Popups.warn(
                    "Selecione um funcionário!"
            );

            return;
        }

        Funcionario funcionario = tableModel
                .getFuncionario(row);

        if (funcionario == null) {
            Popups.warn(
                    "Funcionário inválido!"
            );

            return;
        }

        boolean confirmacao = Popups.confirm(
                "Você deseja editar esse funcionário?"
        );

        if (!confirmacao) {
            return;
        }

        try {
            updateNome(funcionario);
            updateCodigo(funcionario);
            updateDocumento(funcionario);
            updateAcessos(funcionario);

            ApplicationContext
                    .getFuncionarioService()
                    .update(funcionario);

            Popups.success(
                    "Funcionário atualizado com sucesso!"
            );

            pesquisarFuncionarios();

        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null) {
                String lower = message.toLowerCase();

                if (lower.contains("unique")
                        || lower.contains("duplic")
                        || lower.contains("constraint")) {

                    Popups.error("""
                                 Já existe um funcionário com esse código.
                                 Utilize outro código único.
                                 """);

                    return;
                }
            }

            Popups.error(e.getMessage());
        }
    }

    /**
     * Atualiza nome do funcionário.
     *
     * @param funcionario - Funcionário selecionado
     */
    private void updateNome(
            Funcionario funcionario
    ) {
        String novoNome = Popups.input(
                "Atualizar Nome",
                """
                Valor atual: %s

                Informe o novo nome.
                Deixe vazio para manter o valor atual.
                """
                        .formatted(funcionario.getNome())
        );

        if (PopupValues.wasCancelled(novoNome)) {
            throw new IllegalArgumentException(
                    "Operação cancelada."
            );
        }

        if (!PopupValues.shouldKeep(novoNome)) {
            funcionario.setNome(
                    novoNome.trim()
            );
        }
    }

    /**
     * Atualiza código do funcionário.
     *
     * @param funcionario - Funcionário selecionado
     */
    private void updateCodigo(
            Funcionario funcionario
    ) {
        String novoCodigo = Popups.input(
                "Atualizar Código",
                """
                Valor atual: %s

                Informe o novo código.
                Deixe vazio para manter o valor atual.
                """
                        .formatted(funcionario.getCodigo())
        );

        if (PopupValues.wasCancelled(novoCodigo)) {
            throw new IllegalArgumentException(
                    "Operação cancelada."
            );
        }

        if (!PopupValues.shouldKeep(novoCodigo)) {
            funcionario.setCodigo(
                    novoCodigo.trim()
            );
        }
    }

    /**
     * Atualiza documento do funcionário.
     *
     * @param funcionario - Funcionário selecionado
     */
    private void updateDocumento(
            Funcionario funcionario
    ) {
        List<String> documentosDisponiveis = documentosMap
                .keySet()
                .stream()
                .toList();

        String documentosTexto = documentosDisponiveis
                .stream()
                .collect(Collectors.joining("\n"));

        Documento atual = funcionario.getDocumento();

        String novoDocumento = Popups.input(
                "Atualizar Documento",
                """
                Documento atual: %s

                Digite exatamente um dos documentos abaixo:

                %s

                Deixe vazio para manter o atual.
                """
                        .formatted(
                                atual != null
                                        ? atual.getDocumento()
                                        : "-",
                                documentosTexto
                        )
        );

        if (PopupValues.wasCancelled(novoDocumento)) {
            throw new IllegalArgumentException(
                    "Operação cancelada."
            );
        }

        if (PopupValues.shouldKeep(novoDocumento)) {
            return;
        }

        Integer documentoId = documentosMap.get(
                novoDocumento
        );

        if (documentoId == null) {
            Popups.warn(
                    "Documento inválido!"
            );

            return;
        }

        Documento documento = ApplicationContext
                .getDocumentoService()
                .findById(documentoId)
                .orElseThrow();

        funcionario.setDocumento(documento);
    }

    /**
     * Atualiza acessos do funcionário.
     *
     * @param funcionario - Funcionário selecionado
     */
    private void updateAcessos(
            Funcionario funcionario
    ) {
        String acessosAtuais = funcionario.getAcessos()
                .stream()
                .map(Acesso::getNome)
                .sorted()
                .collect(Collectors.joining(", "));

        String acessosDisponiveis = acessosMap
                .keySet()
                .stream()
                .sorted()
                .collect(Collectors.joining("\n"));

        String novosAcessos = Popups.input(
                "Atualizar Acessos",
                """
                Acessos atuais:
                %s

                Acessos disponíveis:
                %s

                Digite os acessos separados por vírgula.
                Exemplo:
                ADMIN, ESTOQUE

                Deixe vazio para manter os atuais.
                """
                        .formatted(
                                acessosAtuais,
                                acessosDisponiveis
                        )
        );

        if (PopupValues.wasCancelled(novosAcessos)) {
            throw new IllegalArgumentException(
                    "Operação cancelada."
            );
        }

        if (PopupValues.shouldKeep(novosAcessos)) {
            return;
        }

        Set<Acesso> acessosAtualizados = novosAcessos
                .lines()
                .flatMap(line -> List.of(
                line.split(",")
        ).stream())
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .map(acessosMap::get)
                .filter(id -> id != null)
                .map(id -> ApplicationContext
                .getAcessoService()
                .findById(id)
                .orElseThrow())
                .collect(Collectors.toSet());

        if (acessosAtualizados.isEmpty()) {
            Popups.warn(
                    "Informe ao menos um acesso válido!"
            );

            return;
        }

        funcionario.getAcessos().clear();
        funcionario.getAcessos().addAll(
                acessosAtualizados
        );
    }

    /**
     * Define resultados da tabela.
     *
     * @param funcionarios - Lista encontrada
     */
    private void setResultados(
            List<Funcionario> funcionarios
    ) {
        if (funcionarios == null
                || funcionarios.isEmpty()) {

            tableModel.setFuncionarios(
                    List.of()
            );

            Popups.warn(
                    "Nenhum funcionário encontrado!"
            );

            return;
        }

        tableModel.setFuncionarios(
                funcionarios
        );
    }

    /**
     * Carrega todos os funcionários.
     */
    private void loadFuncionarios() {
        setResultados(
                ApplicationContext
                        .getFuncionarioService()
                        .findAll()
        );
    }

    /**
     * Atualiza documentos disponíveis.
     */
    private void refreshDocumentos() {
        List<Documento> documentos = ApplicationContext
                .getDocumentoService()
                .findAll();

        documentosMap.clear();
        documentosReverseMap.clear();

        documentos.forEach(documento -> {
            String label = "%s (%s)"
                    .formatted(
                            documento.getDocumento(),
                            documento.getTipo()
                    );

            documentosMap.put(
                    label,
                    documento.getId()
            );

            documentosReverseMap.put(
                    documento.getId(),
                    label
            );
        });

        documentoBox.setModel(
                new DefaultComboBoxModel<>(
                        documentosMap
                                .keySet()
                                .toArray(new String[0])
                )
        );
    }

    /**
     * Atualiza acessos disponíveis.
     */
    private void refreshAcessos() {
        List<Acesso> acessos = ApplicationContext
                .getAcessoService()
                .findAll();

        acessosMap.clear();

        DefaultListModel<String> model
                = new DefaultListModel<>();

        acessos.forEach(acesso -> {
            acessosMap.put(
                    acesso.getNome(),
                    acesso.getId()
            );

            model.addElement(
                    acesso.getNome()
            );
        });

        acessosList.setModel(model);
    }

    /**
     * Limpa formulário.
     */
    private void clearForm() {
        UIForm.clearFields(
                codigoField,
                nomeField,
                pesquisaField
        );

        if (documentoBox.getItemCount() > 0) {
            documentoBox.setSelectedIndex(0);
        }

        acessosList.clearSelection();
    }

}
