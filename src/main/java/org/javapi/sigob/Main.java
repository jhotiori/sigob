package org.javapi.sigob;

import org.javapi.sigob.cli.MenuMain;
import org.javapi.sigob.config.FlywayConfig;
import org.javapi.sigob.service.FuncionarioService;

public class Main {
    public static void main(String[] args) {
        FlywayConfig.migrate();
        new MenuMain(new FuncionarioService()).show();
    }
}
