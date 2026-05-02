# **UI**

Ponto de entrada para criação e configuração de componentes Swing de forma fluente.

---

## `panel`

Cria um `JPanel` configurável.

**Assinaturas:**
```java
UI.panel(Consumer<JPanel> config)
UI.panel(Consumer<JPanel> config, JComponent... children)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `config` | `Consumer<JPanel>` | Configurações aplicadas ao painel |
| `children` | `JComponent...` | Componentes filhos adicionados ao painel |

**Exemplo:**
```java
JPanel painel = UI.panel(p -> {
    p.setLayout(new BorderLayout());
    p.setBackground(Color.WHITE);
});

// Com filhos
JPanel painel = UI.panel(
    p -> p.setLayout(new FlowLayout()),
    UI.button("Salvar"),
    UI.button("Cancelar")
);
```

---

## `button`

Cria um `JButton`. Quando texto é passado diretamente, o foco via teclado é desabilitado por padrão.

**Assinaturas:**
```java
UI.button(String text)
UI.button(String text, Consumer<JButton> config)
UI.button(Consumer<JButton> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `text` | `String` | Texto exibido no botão |
| `config` | `Consumer<JButton>` | Configurações adicionais |

**Exemplo:**
```java
JButton btn = UI.button("Salvar");

JButton btn = UI.button("Confirmar", b -> {
    b.setBackground(Color.GREEN);
    b.setPreferredSize(new Dimension(100, 30));
});
```

---

## `label`

Cria um `JLabel`.

**Assinaturas:**
```java
UI.label(String text, Consumer<JLabel> config)
UI.label(Consumer<JLabel> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `text` | `String` | Texto exibido no label |
| `config` | `Consumer<JLabel>` | Configurações adicionais |

**Exemplo:**
```java
JLabel titulo = UI.label("Nome:", l -> {
    l.setFont(l.getFont().deriveFont(Font.BOLD));
});
```

---

## `textField`

Cria um `JTextField`.

**Assinaturas:**
```java
UI.textField(String text, Consumer<JTextField> config)
UI.textField(Consumer<JTextField> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `text` | `String` | Texto inicial do campo |
| `config` | `Consumer<JTextField>` | Configurações adicionais |

**Exemplo:**
```java
JTextField campo = UI.textField(f -> f.setColumns(20));

JTextField campo = UI.textField("valor padrão", f -> f.setEnabled(false));
```

---

## `textArea`

Cria um `JTextArea`.

**Assinatura:**
```java
UI.textArea(Consumer<JTextArea> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `config` | `Consumer<JTextArea>` | Configurações aplicadas |

**Exemplo:**
```java
JTextArea area = UI.textArea(a -> {
    a.setRows(5);
    a.setLineWrap(true);
    a.setWrapStyleWord(true);
});
```

---

## `scroll`

Envolve um componente em um `JScrollPane`.

**Assinatura:**
```java
UI.scroll(JComponent component)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `component` | `JComponent` | Componente que receberá o scroll |

**Exemplo:**
```java
JScrollPane scroll = UI.scroll(
    UI.textArea(a -> a.setRows(10))
);
```

---

## `children`

Adiciona múltiplos filhos a um componente pai de uma vez.

**Assinatura:**
```java
UI.children(T parent, JComponent... children)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `parent` | `T extends JComponent` | Componente pai |
| `children` | `JComponent...` | Componentes filhos |

**Exemplo:**
```java
JPanel painel = UI.children(
    UI.panel(p -> p.setLayout(new FlowLayout())),
    UI.label("Nome:", null),
    UI.textField(f -> f.setColumns(15)),
    UI.button("Buscar")
);
```

---

## `frame`

Cria um `JFrame`. O comportamento padrão de fechamento é `EXIT_ON_CLOSE`.

**Assinaturas:**
```java
UI.frame(String title, Consumer<JFrame> config)
UI.frame(Consumer<JFrame> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `title` | `String` | Título da janela |
| `config` | `Consumer<JFrame>` | Configurações aplicadas ao frame |

**Exemplo:**
```java
JFrame janela = UI.frame("Minha Janela", f -> {
    f.setSize(800, 600);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
});
```

---

## Combinações

### Montando um formulário completo

```java
JPanel form = UI.panel(p -> p.setLayout(new BorderLayout()),
    UI.panel(p -> p.setLayout(new FlowLayout()),
        UI.label("Nome:", null),
        UI.textField(f -> f.setColumns(20))
    ),
    UI.scroll(
        UI.textArea(a -> {
            a.setRows(6);
            a.setLineWrap(true);
        })
    ),
    UI.panel(p -> p.setLayout(new FlowLayout(FlowLayout.RIGHT)),
        UI.button("Cancelar"),
        UI.button("Salvar")
    )
);

UI.frame("Cadastro", f -> {
    f.setContentPane(form);
    f.setSize(500, 350);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
});
```

# **Async**

Utilitário para execução de tarefas assíncronas integrado ao EDT (Event Dispatch Thread) do Swing.
O pool de threads é fixo com base no número de processadores disponíveis na máquina.

---

## `execute`

Executa uma tarefa em background fora da EDT.

**Assinaturas:**
```java
Async.execute(Runnable task)
Async.execute(Runnable task, Consumer<Throwable> onError)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `task` | `Runnable` | Tarefa executada em background |
| `onError` | `Consumer<Throwable>` | Callback chamado em caso de erro (opcional) |

**Exemplo:**
```java
Async.execute(() -> {
    // tarefa pesada, fora da EDT
    processarDados();
});

Async.execute(
    () -> processarDados(),
    erro -> System.err.println("Falhou: " + erro.getMessage())
);
```

---

## `ui`

Garante que uma tarefa seja executada na EDT. Se já estiver na EDT, executa diretamente - caso contrário, agenda via `invokeLater`.

**Assinatura:**
```java
Async.ui(Runnable task)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `task` | `Runnable` | Tarefa a ser executada na EDT |

**Exemplo:**
```java
Async.ui(() -> {
    label.setText("Atualizado!");
    painel.repaint();
});
```

---

## `compute`

Executa uma tarefa em background e, ao concluir, chama o callback de sucesso na EDT. Ideal para buscar dados e atualizar a UI em seguida.

**Assinaturas:**
```java
Async.compute(Supplier<T> task, Consumer<T> onSuccess)
Async.compute(Supplier<T> task, Consumer<T> onSuccess, Consumer<Throwable> onError)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `task` | `Supplier<T>` | Tarefa executada em background, retorna um resultado |
| `onSuccess` | `Consumer<T>` | Callback executado na EDT com o resultado |
| `onError` | `Consumer<Throwable>` | Callback executado na EDT em caso de erro (opcional) |

**Exemplo:**
```java
Async.compute(
    () -> repositorio.buscarUsuarios(),
    usuarios -> tabela.setModel(new UsuarioTableModel(usuarios))
);

Async.compute(
    () -> repositorio.buscarUsuarios(),
    usuarios -> tabela.setModel(new UsuarioTableModel(usuarios)),
    erro -> JOptionPane.showMessageDialog(null, "Erro ao carregar: " + erro.getMessage())
);
```

---

## `shutdown`

Encerra o pool de threads de background. Use ao finalizar a aplicação.

**Assinatura:**
```java
Async.shutdown()
```

**Exemplo:**
```java
Runtime.getRuntime().addShutdownHook(new Thread(Async::shutdown));
```

---

---

# **Events**

Ponto de entrada para criação de bindings de eventos em componentes Swing.
Cada método cria, configura e instala o binding no componente automaticamente.

---

## `mouse`

Cria bindings de eventos de mouse em um componente.

**Assinatura:**
```java
Events.mouse(Component component, Consumer<MouseListenerBinding> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `component` | `Component` | Componente que receberá os eventos |
| `config` | `Consumer<MouseListenerBinding>` | Configuração dos handlers |

**Handlers disponíveis em `MouseListenerBinding`:**

| Método | Evento |
|--------|--------|
| `onClicked(Consumer<MouseEvent>)` | Botão clicado |
| `onPressed(Consumer<MouseEvent>)` | Botão pressionado |
| `onReleased(Consumer<MouseEvent>)` | Botão solto |
| `onEntered(Consumer<MouseEvent>)` | Cursor entrou no componente |
| `onExited(Consumer<MouseEvent>)` | Cursor saiu do componente |
| `onMoved(Consumer<MouseEvent>)` | Mouse movido |
| `onDragged(Consumer<MouseEvent>)` | Mouse arrastado |

**Exemplo:**
```java
Events.mouse(painel, m -> m
    .onClicked(e -> System.out.println("Clicado!"))
    .onEntered(e -> painel.setBackground(Color.LIGHT_GRAY))
    .onExited(e -> painel.setBackground(Color.WHITE))
);
```

---

## `key`

Cria bindings de eventos de teclado via `KeyListener`.

**Assinatura:**
```java
Events.key(Component component, Consumer<KeyListenerBinding> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `component` | `Component` | Componente que receberá os eventos |
| `config` | `Consumer<KeyListenerBinding>` | Configuração dos handlers |

**Handlers disponíveis em `KeyListenerBinding`:**

| Método | Evento |
|--------|--------|
| `onPressed(Consumer<KeyEvent>)` | Tecla pressionada |
| `onReleased(Consumer<KeyEvent>)` | Tecla liberada |
| `onTyped(Consumer<KeyEvent>)` | Tecla digitada |

> O componente recebe foco automaticamente ao instalar o binding.

**Exemplo:**
```java
Events.key(campo, k -> k
    .onPressed(e -> System.out.println("Tecla: " + e.getKeyCode()))
    .onReleased(e -> validarCampo())
);
```

---

## `text`

Cria bindings de eventos de alteração de texto via `DocumentListener`.

**Assinatura:**
```java
Events.text(JTextComponent component, Consumer<DocumentListenerBinding> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `component` | `JTextComponent` | Campo de texto que receberá os eventos |
| `config` | `Consumer<DocumentListenerBinding>` | Configuração dos handlers |

**Handlers disponíveis em `DocumentListenerBinding`:**

| Método | Evento |
|--------|--------|
| `onChange(Consumer<String>)` | Texto alterado (insert, remove ou change) |

**Exemplo:**
```java
Events.text(campoBusca, t -> t
    .onChange(texto -> filtrarTabela(texto))
);
```

---

## `keyBinder`

Cria bindings de atalhos de teclado via `InputMap` e `ActionMap`. Diferente de `key`, funciona mesmo sem o componente estar em foco direto (`WHEN_IN_FOCUSED_WINDOW`).

**Assinatura:**
```java
Events.keyBinder(JComponent component, Consumer<KeyBinder> config)
```

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `component` | `JComponent` | Componente associado aos atalhos |
| `config` | `Consumer<KeyBinder>` | Configuração dos atalhos |

**Métodos disponíveis em `KeyBinder`:**

| Método | Descrição |
|--------|-----------|
| `on(String key, Runnable handler)` | Associa uma tecla a uma ação |
| `on(String key, Consumer<ActionEvent> handler)` | Associa uma tecla a uma ação com evento |
| `off(String key)` | Remove a associação de uma tecla |

> O formato de `key` segue o padrão do `KeyStroke.getKeyStroke()` - ex: `"ENTER"`, `"ctrl S"`, `"F5"`.

**Exemplo:**
```java
Events.keyBinder(painel, kb -> kb
    .on("ctrl S", () -> salvar())
    .on("ESCAPE", () -> fechar())
    .on("F5", e -> atualizar())
);
```

---

## Combinações

### Busca em tempo real com `text` + `Async`

```java
Events.text(campoBusca, t -> t
    .onChange(texto -> Async.compute(
        () -> repositorio.buscar(texto),
        resultado -> tabela.setModel(new ResultadoTableModel(resultado)),
        erro -> JOptionPane.showMessageDialog(null, "Erro: " + erro.getMessage())
    ))
);
```

### Atalho de teclado que dispara tarefa assíncrona

```java
Events.keyBinder(painel, kb -> kb
    .on("ctrl R", () -> Async.compute(
        () -> repositorio.recarregar(),
        dados -> tabela.setModel(new DadosTableModel(dados))
    ))
);
```

### Hover com feedback visual via `mouse`

```java
JButton btn = UI.button("Enviar");

Events.mouse(btn, m -> m
    .onEntered(e -> btn.setBackground(Color.CYAN))
    .onExited(e -> btn.setBackground(null))
    .onClicked(e -> Async.compute(
        () -> servico.enviar(),
        res -> JOptionPane.showMessageDialog(null, "Enviado!")
    ))
);
```
