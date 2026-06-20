package org.javapi.sigob.view.v2.context;

import java.util.List;
import java.util.Set;

import org.javapi.sigob.model.entity.Acesso;
import org.javapi.sigob.model.entity.Funcionario;

/**
 * Contexto de sessão da aplicação.
 */
public final class SessionContext {
    /**
     * Funcionário atualmente logado.
     *
     * @see Funcionario
     */
    private static Funcionario FUNCIONARIO;

    /**
     * Define o funcionário atualmente logado.
     *
     * @param funcionario - Funcionario logado
     */
    public static void setFuncionarioLogado(Funcionario funcionario) {
        SessionContext.FUNCIONARIO = funcionario;
    }

    /**
     * Retorna o funcionário atualmente logado.
     *
     * @return Funcionario - Funcionario logado
     */
    public static Funcionario getFuncionarioLogado() {
        return FUNCIONARIO;
    }

    /**
     * Verifica se o funcionário atualmente logado foi definido.
     *
     * @return boolean - Se o funcionário atualmente logado foi definido
     */
    public static boolean hasFuncionarioLogado() {
        return FUNCIONARIO != null;
    }

    /**
     * Retorna os acessos do funcionário atualmente logado.
     *
     * @return List<Acesso> - Acessos do funcionário atualmente logado
     */
    private static List<Acesso> getFuncionarioAcessos() {
        Funcionario loggedFuncionario = getFuncionarioLogado();
        if (loggedFuncionario == null) {
            return null;
        }

        Set<Acesso> acessosSet = loggedFuncionario.getAcessos();
        if (acessosSet == null || acessosSet.isEmpty()) {
            return null;
        }

        return acessosSet.stream().toList();
    }

    /**
     * Verifica se o funcionário atualmente logado possui acesso.
     *
     * @param acesso - Acesso
     * @return boolean - Se possui acesso
     */
    public static boolean hasFuncionarioAcesso(String acesso) {
        List<Acesso> acessos = getFuncionarioAcessos();
        if (acessos == null) {
            return false;
        }

        return acessos
            .stream()
            .anyMatch(a -> a.getNome().toLowerCase().equals(acesso.toLowerCase()));
    }

    /**
     * Construtor privado.
     */
    private SessionContext() {

    }
}
