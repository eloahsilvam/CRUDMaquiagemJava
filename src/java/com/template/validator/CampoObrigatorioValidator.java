package com.template.validator;

public class CampoObrigatorioValidator implements Validador<String>{
    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {
        return this.valor != null && !this.valor.trim().isEmpty();
    }

    @Override
    public String getMesagemErro() {
        return "O campo " + nomeCampo + " deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

class NaoNumeroValidador implements Validador<String> {
    private final String valor;

    public NaoNumeroValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        return this.valor != null && !this.valor.matches(".*\\d.*");
    }

    @Override
    public String getMesagemErro() {
        return "O campo deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}
