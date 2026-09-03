package com.sonar.backend.utilitarios;

import com.sonar.backend.exception.RecursoNaoEncontradoException;

public class Utilitario {

    public static <T> T obterOuFalhar(T recurso, String nomeRecurso) {
        if (recurso == null) {
            throw new RecursoNaoEncontradoException(nomeRecurso + " não encontrado");
        }

        return recurso;
    }

}
