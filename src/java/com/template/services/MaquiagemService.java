package com.template.services;

import com.template.model.dao.MaquiagemDAO;
import com.template.model.dto.MaquiagemDTO;

import java.util.List;

public class MaquiagemService {

    public List<MaquiagemDTO> listar() {
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();
        return maquiagemDAO.listar();
    }

    public void salvar(MaquiagemDTO maquiagem) {
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();

        if (maquiagem.getId() > 0) {
            maquiagemDAO.atualizar(maquiagem);
        } else {
            maquiagemDAO.cadastrarMaquiagem(maquiagem);
        }
    }

    public void excluir(int id) {
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();
        maquiagemDAO.excluir(id);
    }
}