package org.javapi.sigob.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Utilitário para leitura de dados via CLI. Centraliza parsing, validação e
 * repetição de entrada.
 */
public final class Inputter {

    /**
     * Scanner único da aplicação. Não deve ser fechado manualmente.
     */
    private static final Scanner SC = new Scanner(System.in);

    private Inputter() {
    }

    /**
     * Método base para leitura com parsing e retry automático.
     *
     * @param prompt Mensagem exibida ao usuário
     * @param parser Função de conversão String -> T
     * @param expectedType Descrição do tipo esperado
     * @param <T> Tipo de retorno
     * @return T - Valor convertido com sucesso
     */
    private static <T> T read(String prompt, Function<String, T> parser, String expectedType) {
        while (true) {
            System.out.print(prompt);
            String input = SC.nextLine().trim();

            try {
                return parser.apply(input);
            } catch (Exception e) {
                errorHandler(input, expectedType);
            }
        }
    }

    /**
     * Exibe mensagem de erro padronizada.
     *
     * @param input Valor informado
     * @param expectedType Tipo esperado
     */
    private static void errorHandler(String input, String expectedType) {
        Logger.warn("Entrada inválida (%s) - Esperado valor do tipo %s!".formatted(input, expectedType));
    }

    /**
     * Lê um valor inteiro.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return int - Valor inteiro válido
     */
    public static int readInt(String prompt) {
        return read(prompt, Integer::parseInt, "Integer");
    }

    /**
     * Lê um valor float.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return float - Valor float válido
     */
    public static float readFloat(String prompt) {
        return read(prompt, Float::parseFloat, "Float");
    }

    /**
     * Lê um valor double.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return double - Valor double válido
     */
    public static double readDouble(String prompt) {
        return read(prompt, Double::parseDouble, "Double");
    }

    /**
     * Lê um valor BigInteger.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return BigInteger - Valor válido
     */
    public static BigInteger readBigInt(String prompt) {
        return read(prompt, BigInteger::new, "BigInteger");
    }

    /**
     * Lê um valor BigDecimal.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return BigDecimal - Valor válido
     */
    public static BigDecimal readBigDecimal(String prompt) {
        return read(prompt, BigDecimal::new, "BigDecimal");
    }

    /**
     * Lê um valor booleano. Aceita true/false, t/f, y/n, yes/no, s/n, sim/nao.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return boolean - Valor booleano válido
     * @throws IllegalArgumentException Quando o valor não for reconhecido
     */
    public static boolean readBoolean(String prompt) {
        return read(prompt, Inputter::parseBoolean, "Boolean (s/n)");
    }

    /**
     * Lê uma string (permite valor vazio).
     *
     * @param prompt Mensagem exibida ao usuário
     * @return String - String informada
     */
    public static String readString(String prompt) {
        return read(prompt, s -> s, "String");
    }

    /**
     * Lê uma string que não pode ser vazia.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return String - String informada
     */
    public static String readNotBlankString(String prompt) {
        return read(prompt, String::trim, "String (não pode ser vazia)");
    }

    /**
     * Lê uma data no formato obrigatório dd-MM-yyyy.
     *
     * @param prompt Mensagem exibida ao usuário
     * @return LocalDate - Data válida
     */
    public static LocalDate readLocalDate(String prompt) {
        return read(
                prompt,
                input -> LocalDate.parse(
                        input,
                        DateTimeFormatter
                                .ofPattern("dd-MM-uuuu")
                                .withResolverStyle(ResolverStyle.STRICT)
                ),
                "Data (DD-MM-YYYY)"
        );
    }

    /**
     * Realiza parsing de boolean com múltiplos formatos aceitos.
     *
     * @param input Valor informado pelo usuário
     * @return boolean - Valor convertido
     */
    private static boolean parseBoolean(String input) {
        return switch (input.toLowerCase()) {
            case "true", "t", "y", "yes", "s", "sim" ->
                true;
            case "false", "f", "n", "no", "nao" ->
                false;
            default ->
                Boolean.parseBoolean(input);
        };
    }
}
