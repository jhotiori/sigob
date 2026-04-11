package org.javapi.sigob.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public final class Inputter {
    private static final Scanner SC = new Scanner(System.in);

    private Inputter() {}

    /**
     * Lê um inteiro do usuário.
     * @param prompt mensagem exibida antes da leitura
     * @return valor inteiro válido
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (Exception e) {
                erro("int");
            }
        }
    }

    /**
     * Lê um float do usuário.
     * @param prompt mensagem exibida antes da leitura
     * @return float - Valor float valido
     */
    public static float readFloat(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Float.parseFloat(SC.nextLine().trim());
            } catch (Exception e) {
                erro("float");
            }
        }
    }

    /**
     * Lê um double do usuário.
     * @param prompt mensagem exibida antes da leitura
     * @return double - Valor double valido
     */
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(SC.nextLine().trim());
            } catch (Exception e) {
                erro("double");
            }
        }
    }

    /**
     * Lê um booleano do usuário (true/false).
     * @param prompt mensagem exibida antes da leitura
     * @return boolean - Valor boolean valido
     */
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SC.nextLine().trim().toLowerCase();

            if (input.equals("s")) {
                return true;
            }

            if (input.equals("n")) {
                return false;
            }

            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }

            erro("boolean (true/false ou s/n)");
        }
    }

    /**
     * Lê uma string do usuário.
     * @param prompt mensagem exibida antes da leitura
     * @return String - Valor string valido
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    /**
     * Lê um BigInteger do usuário.
     * @param prompt mensagem exibida antes da leitura
     * @return BigInteger - Valor BigInteger valido
     */
    public static BigInteger readBigInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return new BigInteger(SC.nextLine().trim());
            } catch (Exception e) {
                erro("BigInteger");
            }
        }
    }

    /**
     * Lê um BigDecimal do usuário.
     * @param prompt mensagem exibida antes da leitura
     * @return BigDecimal - Valor BigDecimal valido
     */
    public static BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return new BigDecimal(SC.nextLine().trim());
            } catch (Exception e) {
                erro("BigDecimal");
            }
        }
    }

    /**
     * Exibe mensagem padrão de erro para entrada inválida.
     * @param tipo tipo de dado inválido
     */
    private static void erro(String tipo) {
        System.out.println("Input inválida! Insira um valor do tipo " + tipo);
    }
}
