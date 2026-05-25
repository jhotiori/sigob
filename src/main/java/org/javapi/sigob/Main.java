package org.javapi.sigob;

import org.javapi.sigob.config.FlywayConfig;
import org.javapi.sigob.view.Async;
import org.javapi.sigob.view.windows.LoginWindow;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {

    public static void main(String[] args) {
        FlywayConfig.migrate();
        Async.ui(() -> {
            FlatMacDarkLaf.setup();
            LoginWindow login = new LoginWindow();
            login.show();
        });
    }
}
