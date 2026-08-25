package com.template.validator;

import com.template.model.dto.MaquiagemDTO;
import java.util.ArrayList;
import java.util.List;

public class MaquiagemValidator {

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

        // Converte a quantidade Integer para String para o seu QuantidadeValidador aceitar
        validadores.add(new QuantidadeValidador(String.valueOf(dto.getQuantidade())));

        // Loop de validação sequencial (estilo imagem original)
        for (Validador validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                erros.add(validador.getMesagemErro());
                break;
            }
        }

        // Validação do preço (caso ainda não tenha uma classe PrecoValidador)
        if (erros.isEmpty()) {
            if (dto.getPreco() == null || dto.getPreco() <= 0) {
                erros.add("Informe um preço válido maior que zero.");
            }
        }

        return erros;
    }
}