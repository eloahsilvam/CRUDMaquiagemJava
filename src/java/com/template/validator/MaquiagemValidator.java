package com.template.validator;

import com.template.model.dto.MaquiagemDTO;
import java.util.ArrayList;
import java.util.List;

public class MaquiagemValidator implements IMaquiagemValidator{

    public List<String> validar(MaquiagemDTO dto) {
        List<String> erros = new ArrayList<>();

        if (dto == null) {
            erros.add("Dados da maquiagem não foram informados.");
            return erros;
        }

        List<Validador> validadores = new ArrayList<>();

        // Validadores de texto
        validadores.add(new CampoObrigatorioValidator("Nome", dto.getNome()));
        validadores.add(new CampoObrigatorioValidator("Marca", dto.getMarca()));
        validadores.add(new CampoObrigatorioValidator("Cor", dto.getCor()));

        // Converte a quantidade Integer para String para o QuantidadeValidador aceitar
        validadores.add(new QuantidadeValidador(String.valueOf(dto.getQuantidade())));

        // Loop de validação sequencial
        for (Validador validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                erros.add(validador.getMesagemErro());
                break;
            }
        }

        // Validação do preço
        if (erros.isEmpty()) {
            if (dto.getPreco() == null || dto.getPreco() <= 0) {
                erros.add("Informe um preço válido maior que zero.");
            }
        }

        return erros;
    }

    @Override
    public boolean validarMaquiagem(String nome, String marca, String cor, boolean preco, boolean quantidade) {
        return false;
    }

    @Override
    public boolean validarMarca(String marca) {
        return false;
    }

    @Override
    public boolean validarCor(String cor) {
        return false;
    }

    @Override
    public boolean validarPreco(boolean preco) {
        return false;
    }

    @Override
    public boolean validarQuantidade(boolean quantidade) {
        return false;
    }
}