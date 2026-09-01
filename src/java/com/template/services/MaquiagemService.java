package com.template.services;

import com.template.model.dao.MaquiagemDAO;
import com.template.model.dto.MaquiagemDTO;

import java.util.List;

// [CAMADA DE SERVIÇO]: Atua como intermediária entre a Controller (interface) e o DAO (banco de dados)
public class MaquiagemService {

    // [O QUE FAZ]: Solcita a lista completa de maquiagens cadastradas
    public List<MaquiagemDTO> listar() {
        // [CRIAÇÃO DE OBJETO]: Instancia a classe DAO para ter acesso aos comandos SQL
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();

        // [UM CHAMA O OUTRO]: Chama o método listar() da MaquiagemDAO e devolve o resultado para a Controller
        return maquiagemDAO.listar();
    }

    // [O QUE FAZ]: Aplica a regra de decisão entre incluir ou alterar um registro
    public void salvar(MaquiagemDTO maquiagem) {
        // [CRIAÇÃO DE OBJETO]: Instancia o DAO responsável pela persistência
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();

        // [REGRA DE NEGÓCIO]: Se o objeto já possui ID (> 0), trata-se de uma alteração
        if (maquiagem.getId() > 0) {
            // [UM CHAMA O OUTRO]: Chama o método atualizar (UPDATE) no DAO
            maquiagemDAO.atualizar(maquiagem);
        } else {
            // [UM CHAMA O OUTRO]: Se não possui ID, chama o método cadastrar (INSERT) no DAO
            maquiagemDAO.cadastrarMaquiagem(maquiagem);
        }
    }

    // [O QUE FAZ]: Gerencia o fluxo de remoção de um registro
    public void excluir(int id) {
        // [CRIAÇÃO DE OBJETO]: Instancia a classe de acesso aos dados
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();

        // [UM CHAMA O OUTRO]: Executa a exclusão (DELETE) informando o ID recebido
        maquiagemDAO.excluir(id);
    }
}