package org.javapi.sigob;

import org.javapi.sigob.config.FlywayConfig;
import org.javapi.sigob.view.Async;
import org.javapi.sigob.view.windows.LoginWindow;

import com.formdev.flatlaf.FlatDarkLaf;

public class Main {

    public static void main(String[] args) {
        FlywayConfig.migrate();
        /*new MenuMain(new FuncionarioService()).show();*/
        Async.ui(() -> {
            FlatDarkLaf.setup();
            LoginWindow login = new LoginWindow();
            login.show();

            /*ApplicationScreen app = new ApplicationScreen();
            app.register(new DashboardScreen());
            app.register(new SettingsScreen());
            app.register(new ProdutoCadastroScreen());
            app.show("dashboard");
            app.render();*/
        });
    }
}
