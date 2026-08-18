package com.template.util;

import com.template.model.dto.MaquiagemDTO;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FormUtil {
    public static void limparCampos(TableView<?> tabela, TextField... campos) {

        if (campos != null) {
            for (TextField campo : campos) {
                if (campo != null) {
                    campo.clear();
                }
            }
        }

        if (tabela != null && tabela.getSelectionModel() != null) {
            tabela.getSelectionModel().clearSelection();
        }
    }

    public static MaquiagemDTO extrairMaquiagem(
            TextField txtNome,
            TextField txtMarca,
            TextField txtCor,
            TextField txtPreco,
            TextField txtQuantidade) {

        MaquiagemDTO maquiagem = new MaquiagemDTO();

        maquiagem.setNome(txtNome.getText());
        maquiagem.setMarca(txtMarca.getText());
        maquiagem.setCor(txtCor.getText());

        try {
            maquiagem.setPreco(
                    Double.parseDouble(
                            txtPreco.getText().trim().replace(",", ".")
                    )
            );
        } catch (Exception erro) {
            maquiagem.setPreco(null);
        }

        try {
            maquiagem.setQuantidade(
                    Integer.parseInt(txtQuantidade.getText().trim())
            );
        } catch (Exception erro) {
            maquiagem.setQuantidade(-1);
        }

        return maquiagem;
    }

    public static void preencherCampos(
            MaquiagemDTO maquiagem,
            TextField txtNome,
            TextField txtMarca,
            TextField txtCor,
            TextField txtPreco,
            TextField txtQuantidade) {

        if (maquiagem != null) {
            txtNome.setText(maquiagem.getNome() != null ? maquiagem.getNome() : "");
            txtMarca.setText(maquiagem.getMarca() != null ? maquiagem.getMarca() : "");
            txtCor.setText(maquiagem.getCor() != null ? maquiagem.getCor() : "");
            txtPreco.setText(maquiagem.getPreco() != null
                    ? String.valueOf(maquiagem.getPreco())
                    : "");
            txtQuantidade.setText(maquiagem.getQuantidade() >= 0
                    ? String.valueOf(maquiagem.getQuantidade())
                    : "");
        }
    }
}