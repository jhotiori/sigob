package org.javapi.sigob.view.v2.context;

/**
 * Contexto de permissões.
 */
public final class PermissionContext {

    /**
     * Verifica se é administrador.
     *
     * @return boolean
     */
    public static boolean admin() {

        return SessionContext.hasFuncionarioAcesso(
                "ADMIN");
    }

    /**
     * Verifica acesso.
     *
     * @param acesso - acesso
     * @return boolean
     */
    public static boolean has(
            String acesso) {

        return admin()
                ||
                SessionContext.hasFuncionarioAcesso(
                        acesso);
    }

    /**
     * Construtor privado.
     */
    private PermissionContext() {

    }
}
