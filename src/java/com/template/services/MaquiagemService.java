package com.template.services;

import com.template.model.dao.MaquiagemDAO;
import com.template.model.dto.MaquiagemDTO;

import java.util.ArrayList;
import java.util.List;

public class MaquiagemService {
    public List<String> validar(MaquiagemDTO dto) {
        List<String> erros = new ArrayList<>();

        if (dto == null) {
            erros.add("Dados da maquiagem não foram fornecidos.");
            return erros;
        }

        // Validação de campos obrigatórios
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            erros.add("Aviso: O campo 'Nome' é obrigatório!");
        }
        if (dto.getMarca() == null || dto.getMarca().trim().isEmpty()) {
            erros.add("Aviso: O campo 'Marca' é obrigatório!");
        }
        if (dto.getCor() == null || dto.getCor().trim().isEmpty()) {
            erros.add("Aviso: O campo 'Cor' é obrigatório!");
        }

        // Validação de números
        if (dto.getPreco() == null) {
            erros.add("Erro: Preço em formato inválido!");
        } else if (dto.getPreco() <= 0) {
            erros.add("Aviso: O preço deve ser maior que zero!");
        }

        if (dto.getQuantidade() < 0) {
            erros.add("Aviso: A quantidade não pode ser negativa!");
        }

        return erros;
    }
    private final MaquiagemDAO dao = new MaquiagemDAO();

    // Este é o método que o Controller não estava encontrando:
    public List<MaquiagemDTO> listar() {
        return dao.listar();
    }

    public void salvar(MaquiagemDTO maquiagem) {
        if (maquiagem.getId() > 0) {
            dao.atualizar(maquiagem);
        } else {
            dao.cadastrarMaquiagem(maquiagem);
        }
    }

    public void excluir(int id) {
        dao.excluir(id);
    }
}

