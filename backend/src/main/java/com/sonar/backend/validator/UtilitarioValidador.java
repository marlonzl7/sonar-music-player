package com.sonar.backend.validator;

public class UtilitarioValidador {

    public static <T> boolean campoNulo(T campo) {
        return campo == null;
    }

    public static boolean stringEmBranco(String string) {
        return string.isBlank();
    }

}
