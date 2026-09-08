package com.sonar.backend.validador;

public class UtilitarioValidador {

    public static <T> boolean campoNulo(T campo) {
        return campo == null;
    }

    public static boolean stringEmBranco(String string) {
        return string.isBlank();
    }

}
