# **Axis**

Representa a orientação de eixo utilizada por layouts baseados em pilha.

## Constantes

```java
X
```

Representa orientação horizontal.

### Exemplo

```java
StackBuilder builder = new StackBuilder(Axis.X);
```

---

```java
Y
```

Representa orientação vertical.

### Exemplo

```java
StackBuilder builder = new StackBuilder(Axis.Y);
```

# **BorderBuilder**

Builder fluente para composição de layouts utilizando `BorderLayout`.

## Construtor

```java
public BorderBuilder(JPanel panel)
```

Cria uma nova instância do builder utilizando um painel alvo.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| panel | JPanel | Painel que receberá os componentes |

### Exemplo

```java
JPanel panel = new JPanel(new BorderLayout());

BorderBuilder builder = new BorderBuilder(panel);
```

---

## top

```java
public BorderBuilder top(Component component)
```

Define o componente superior (`BorderLayout.NORTH`).

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente superior |

### Retorno

| Tipo | Descrição |
|---|---|
| BorderBuilder | Instância atual |

### Exemplo

```java
builder.top(new JLabel("Header"));
```

---

## bottom

```java
public BorderBuilder bottom(Component component)
```

Define o componente inferior (`BorderLayout.SOUTH`).

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente inferior |

### Retorno

| Tipo | Descrição |
|---|---|
| BorderBuilder | Instância atual |

### Exemplo

```java
builder.bottom(new JButton("Salvar"));
```

---

## center

```java
public BorderBuilder center(Component component)
```

Define o componente central (`BorderLayout.CENTER`).

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente central |

### Retorno

| Tipo | Descrição |
|---|---|
| BorderBuilder | Instância atual |

### Exemplo

```java
builder.center(new JScrollPane(textArea));
```

---

## left

```java
public BorderBuilder left(Component component)
```

Define o componente esquerdo (`BorderLayout.WEST`).

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente esquerdo |

### Retorno

| Tipo | Descrição |
|---|---|
| BorderBuilder | Instância atual |

### Exemplo

```java
builder.left(new JPanel());
```

---

## right

```java
public BorderBuilder right(Component component)
```

Define o componente direito (`BorderLayout.EAST`).

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente direito |

### Retorno

| Tipo | Descrição |
|---|---|
| BorderBuilder | Instância atual |

### Exemplo

```java
builder.right(new JButton("Configurações"));
```

# **FlowLayoutBuilder**

Builder fluente para criação e configuração de `FlowLayout`.

## Construtor

```java
public FlowLayoutBuilder()
```

Cria uma nova instância utilizando `FlowLayout` padrão.

### Exemplo

```java
FlowLayoutBuilder builder = new FlowLayoutBuilder();
```

---

## align

```java
public FlowLayoutBuilder align(int align)
```

Define o alinhamento do layout.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| align | int | Alinhamento do FlowLayout |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.align(FlowLayout.CENTER);
```

---

## hgap

```java
public FlowLayoutBuilder hgap(int hgap)
```

Define o espaçamento horizontal.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| hgap | int | Espaçamento horizontal |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.hgap(12);
```

---

## vgap

```java
public FlowLayoutBuilder vgap(int vgap)
```

Define o espaçamento vertical.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| vgap | int | Espaçamento vertical |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.vgap(8);
```

---

## gap

```java
public FlowLayoutBuilder gap(int hgap, int vgap)
```

Define os espaçamentos horizontal e vertical simultaneamente.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| hgap | int | Espaçamento horizontal |
| vgap | int | Espaçamento vertical |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.gap(10, 10);
```

---

## add

```java
public FlowLayoutBuilder add(Component component)
```

Adiciona um componente ao painel.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente filho |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.add(new JButton("Confirmar"));
```

---

## add

```java
public FlowLayoutBuilder add(Component... components)
```

Adiciona múltiplos componentes ao painel.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| components | Component[] | Componentes filhos |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.add(
    new JButton("Sim"),
    new JButton("Não"),
    new JButton("Cancelar")
);
```

---

## addIf

```java
public FlowLayoutBuilder addIf(boolean condition, Component component)
```

Adiciona um componente condicionalmente.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| condition | boolean | Condição de adição |
| component | Component | Componente filho |

### Retorno

| Tipo | Descrição |
|---|---|
| FlowLayoutBuilder | Instância atual |

### Exemplo

```java
builder.addIf(isAdmin, adminButton);
```

---

## panel

```java
public JPanel panel()
```

Retorna o painel configurado.

### Retorno

| Tipo | Descrição |
|---|---|
| JPanel | Painel final |

### Exemplo

```java
JPanel panel = builder.panel();
```

# **FrameBuilder**

Builder fluente para configuração de janelas Swing.

## Construtor

```java
public FrameBuilder()
```

Cria uma nova janela com fechamento padrão `EXIT_ON_CLOSE`.

### Exemplo

```java
FrameBuilder builder = new FrameBuilder();
```

---

## Construtor

```java
public FrameBuilder(String title)
```

Cria uma nova janela com título definido.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| title | String | Título da janela |

### Exemplo

```java
FrameBuilder builder = new FrameBuilder("Dashboard");
```

---

## content

```java
public FrameBuilder content(Container container)
```

Define o conteúdo principal da janela.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| container | Container | Container principal |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.content(mainPanel);
```

---

## size

```java
public FrameBuilder size(int width, int height)
```

Define o tamanho da janela.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| width | int | Largura |
| height | int | Altura |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.size(1280, 720);
```

---

## minimumSize

```java
public FrameBuilder minimumSize(int width, int height)
```

Define o tamanho mínimo da janela.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| width | int | Largura mínima |
| height | int | Altura mínima |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.minimumSize(800, 600);
```

---

## preferredSize

```java
public FrameBuilder preferredSize(int width, int height)
```

Define o tamanho preferido da janela.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| width | int | Largura preferida |
| height | int | Altura preferida |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.preferredSize(1024, 768);
```

---

## menu

```java
public FrameBuilder menu(JMenuBar menuBar)
```

Define a barra de menu da janela.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| menuBar | JMenuBar | Barra de menu |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.menu(menuBar);
```

---

## icon

```java
public FrameBuilder icon(Image image)
```

Define o ícone da janela.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| image | Image | Ícone da janela |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.icon(iconImage);
```

---

## resizable

```java
public FrameBuilder resizable(boolean resizable)
```

Define se a janela pode ser redimensionada.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| resizable | boolean | Estado de redimensionamento |

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.resizable(false);
```

---

## center

```java
public FrameBuilder center()
```

Centraliza a janela na tela.

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.center();
```

---

## pack

```java
public FrameBuilder pack()
```

Ajusta a janela ao tamanho preferido dos componentes.

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.pack();
```

---

## show

```java
public FrameBuilder show()
```

Exibe a janela.

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.show();
```

---

## hide

```java
public FrameBuilder hide()
```

Esconde a janela.

### Retorno

| Tipo | Descrição |
|---|---|
| FrameBuilder | Instância atual |

### Exemplo

```java
builder.hide();
```

---

## frame

```java
public JFrame frame()
```

Retorna a instância final da janela.

### Retorno

| Tipo | Descrição |
|---|---|
| JFrame | Janela configurada |

### Exemplo

```java
JFrame frame = builder.frame();
```

# **GridLayoutBuilder**

Builder fluente para criação e configuração de `GridLayout`.

## Construtor

```java
public GridLayoutBuilder(int rows, int cols)
```

Cria um layout em grade com quantidade inicial de linhas e colunas.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| rows | int | Quantidade de linhas |
| cols | int | Quantidade de colunas |

### Exemplo

```java
GridLayoutBuilder builder = new GridLayoutBuilder(2, 3);
```

---

## rows

```java
public GridLayoutBuilder rows(int rows)
```

Define a quantidade de linhas.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| rows | int | Quantidade de linhas |

### Retorno

| Tipo | Descrição |
|---|---|
| GridLayoutBuilder | Instância atual |

### Exemplo

```java
builder.rows(4);
```

---

## cols

```java
public GridLayoutBuilder cols(int cols)
```

Define a quantidade de colunas.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| cols | int | Quantidade de colunas |

### Retorno

| Tipo | Descrição |
|---|---|
| GridLayoutBuilder | Instância atual |

### Exemplo

```java
builder.cols(2);
```

---

## gap

```java
public GridLayoutBuilder gap(int hgap, int vgap)
```

Define os espaçamentos da grade.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| hgap | int | Espaçamento horizontal |
| vgap | int | Espaçamento vertical |

### Retorno

| Tipo | Descrição |
|---|---|
| GridLayoutBuilder | Instância atual |

### Exemplo

```java
builder.gap(8, 8);
```

---

## add

```java
public GridLayoutBuilder add(Component component)
```

Adiciona um componente à grade.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente filho |

### Retorno

| Tipo | Descrição |
|---|---|
| GridLayoutBuilder | Instância atual |

### Exemplo

```java
builder.add(new JButton("Login"));
```

---

## addIf

```java
public GridLayoutBuilder addIf(boolean condition, Component component)
```

Adiciona um componente condicionalmente.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| condition | boolean | Condição de adição |
| component | Component | Componente filho |

### Retorno

| Tipo | Descrição |
|---|---|
| GridLayoutBuilder | Instância atual |

### Exemplo

```java
builder.addIf(isVisible, debugPanel);
```

---

## panel

```java
public JPanel panel()
```

Retorna o painel configurado.

### Retorno

| Tipo | Descrição |
|---|---|
| JPanel | Painel final |

### Exemplo

```java
JPanel panel = builder.panel();
```

# **StackBuilder**

Builder fluente para layouts baseados em eixo utilizando `BoxLayout`.

## Construtor

```java
public StackBuilder(Axis axis)
```

Cria uma nova pilha horizontal ou vertical.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| axis | Axis | Orientação da pilha |

### Exemplo

```java
StackBuilder builder = new StackBuilder(Axis.Y);
```

---

## add

```java
public StackBuilder add(Component component)
```

Adiciona um componente ao painel.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| component | Component | Componente filho |

### Retorno

| Tipo | Descrição |
|---|---|
| StackBuilder | Instância atual |

### Exemplo

```java
builder.add(new JLabel("Configurações"));
```

---

## addIf

```java
public StackBuilder addIf(boolean condition, Component component)
```

Adiciona um componente condicionalmente.

### Argumentos

| Argumento | Tipo | Descrição |
|---|---|---|
| condition | boolean | Condição de adição |
| component | Component | Componente filho |

### Retorno

| Tipo | Descrição |
|---|---|
| StackBuilder | Instância atual |

### Exemplo

```java
builder.addIf(isEnabled, toggleButton);
```

---

## panel

```java
public JPanel panel()
```

Retorna o painel configurado.

### Retorno

| Tipo | Descrição |
|---|---|
| JPanel | Painel final |

### Exemplo

```java
JPanel panel = builder.panel();
```
