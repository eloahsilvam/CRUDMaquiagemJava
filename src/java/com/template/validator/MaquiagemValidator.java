package com.template.validator;

import com.template.model.dto.MaquiagemDTO;

import java.util.ArrayList;
import java.util.List;

public class MaquiagemValidator {
    public List<String> validar(MaquiagemDTO dto) {
        List<String> erros = new ArrayList<>();

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            erros.add("O campo 'Nome' é obrigatório.");
        }
        if (dto.getMarca() == null || dto.getMarca().trim().isEmpty()) {
            erros.add("O campo 'Marca' é obrigatório.");
        }
        if (dto.getPreco() == null || dto.getPreco() <= 0) {
            erros.add("Informe um preço válido maior que zero.");
        }
        if (dto.getQuantidade() < 0) {
            erros.add("Informe uma quantidade válida (0 ou maior).");
        }

        return erros;
    }
}