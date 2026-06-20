package org.javapi.sigob.view.v2.screens;

import javax.swing.JPanel;

import org.javapi.sigob.view.v2.framework.base.BaseScreen;
import org.javapi.sigob.view.v2.framework.ui.UILayouts;

public final class SaldoScreen extends BaseScreen {
    public SaldoScreen() {
        super("saldos");
    }

    @Override
    protected JPanel build() {
        return UILayouts.column()

            .build();
    }
}
