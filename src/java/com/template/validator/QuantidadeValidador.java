package com.template.validator;

import java.util.regex.Pattern;

public class QuantidadeValidador implements Validador<String> {
    private static final String QUANTIDADE_REGEX = "^[0-9]+$";
    private final Pattern pattern = Pattern.compile(QUANTIDADE_REGEX);
    private final String quantidade;

    public QuantidadeValidador(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean validar(String valorAtual) {
        return valorAtual != null && pattern.matcher(valorAtual).matches();
    }

    @Override
    public String getMesagemErro() {
        return "Digite uma quantidade válida!";
    }


    @Override
    public String getValor() {
        return quantidade;
    }
}