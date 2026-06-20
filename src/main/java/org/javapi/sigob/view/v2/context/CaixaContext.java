package org.javapi.sigob.view.v2.context;

import org.javapi.sigob.model.entity.Caixa;

/**
 * Contexto de caixa da aplicação.
 */
public final class CaixaContext {
    /**
     * Caixa atual do contexto.
     *
     * @see Caixa
     */
    private static Caixa CAIXA;

    /**
     * Define o caixa atual do contexto.
     *
     * @param caixa - Caixa atual
     */
    public static void setCurrentCaixa(Caixa caixa) {
        CAIXA = caixa;
    }

    /**
     * Retorna o caixa atual do contexto.
     *
     * @return Caixa - Caixa atual
     */
    public static Caixa getCurrentCaixa() {
        return CAIXA;
    }

    /**
     * Verifica se o caixa atual do contexto foi definido.
     *
     * @return boolean - Se o caixa atual do contexto foi definido
     */
    public static boolean hasCurrentCaixa() {
        return getCurrentCaixa() != null;
    }

    /**
     * Verifica se o caixa atual do contexto está aberto.
     *
     * @return boolean - Se o caixa atual do contexto está aberto
     */
    public static boolean isCaixaAberto() {
        return hasCurrentCaixa() && getCurrentCaixa().getStatus().equals("aberto");
    }
}
