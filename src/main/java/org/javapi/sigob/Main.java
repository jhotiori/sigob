package org.javapi.sigob;

import org.javapi.sigob.cli.MenuMain;
import org.javapi.sigob.config.FlywayConfig;

public class Main {
    public static void main(String[] args) {
        FlywayConfig.migrate();
        new MenuMain().exibir();
    }
}
