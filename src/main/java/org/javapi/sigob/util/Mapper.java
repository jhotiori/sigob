package org.javapi.sigob.util;

import java.util.List;
import java.util.function.Function;

/**
 * Classe utilitaria para mapear valores
 */
public final class Mapper {

    /**
     * Mapeia um valor, usando a função `fn`
     *
     * @param value Valor a ser mapeado
     * @param fn Função a ser usada para mapear o valor
     * @return R - O valor mapeado
     */
    public static <T, R> R map(T value, Function<T, R> fn) {
        return fn.apply(value);
    }

    /**
     * Mapeia uma lista de valores, usando a função `fn`
     *
     * @param list A lista de valores
     * @param fn A função a ser usada para mapear os valores
     * @return List<R> - A lista de valores mapeados
     */
    public static <T, R> List<R> mapList(List<T> list, Function<T, R> fn) {
        return list.stream().map(fn).toList();
    }
}
