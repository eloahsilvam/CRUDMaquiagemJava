package com.template.validator;

public class PrecoValidator implements Validador<Double> {
    private final Double preco;

    public PrecoValidator(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean validar(Double valor) {
        return valor != null && valor > 0;
    }

    @Override
    public String getMesagemErro() {
        return "Informe um preço válido maior que zero.";
    }

    @Override
    public Double getValor() {
        return preco;
    }
}