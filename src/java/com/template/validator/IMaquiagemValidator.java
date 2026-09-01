package com.template.validator;

import com.template.model.dto.MaquiagemDTO;

import java.util.List;

public interface IMaquiagemValidator {
    boolean validarMaquiagem(String nome, String marca, String cor, boolean preco, boolean quantidade);
    boolean validarMarca(String marca);
    boolean validarCor(String cor);
    boolean validarPreco(boolean preco);
    boolean validarQuantidade(boolean quantidade);

    List<String> validar(MaquiagemDTO maquiagem);
}
