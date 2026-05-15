package org.javapi.sigob.view.screens.cadastros;

import javax.swing.JPanel;

import org.javapi.sigob.view.UI;
import org.javapi.sigob.view.screens.BaseScreen;

public class ProdutoCadastroScreen extends BaseScreen {

    public ProdutoCadastroScreen() {
        super("cadastro-produto");
    }

    @Override
    protected JPanel build() {
        return UI.border(border -> {
            border.top(
                    UI.column(
                            UI.label("Nome:"),
                            UI.textField(""),
                            UI.label("Descrição:"),
                            UI.textField(""),
                            UI.label("Valor:"),
                            UI.textField("")
                    ).instance()
            );
        });
    }
}
