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

    public static MaquiagemDTO extrairDTO(TextField txtNome, TextField txtMarca, TextField txtCor, TextField txtPreco, TextField txtQuantidade) {
        MaquiagemDTO dto = new MaquiagemDTO();
        dto.setNome(txtNome.getText());
        dto.setMarca(txtMarca.getText());
        dto.setCor(txtCor.getText());

        try {
            dto.setPreco(Double.parseDouble(txtPreco.getText().trim().replace(",", ".")));
        } catch (Exception e) {
            dto.setPreco(null);
        }

        try {
            dto.setQuantidade(Integer.parseInt(txtQuantidade.getText().trim()));
        } catch (Exception e) {
            dto.setQuantidade(-1);
        }

        return dto;
    }

    public static void preencherCampos(MaquiagemDTO dto, TextField txtNome, TextField txtMarca, TextField txtCor, TextField txtPreco, TextField txtQuantidade) {
        if (dto != null) {
            txtNome.setText(dto.getNome() != null ? dto.getNome() : "");
            txtMarca.setText(dto.getMarca() != null ? dto.getMarca() : "");
            txtCor.setText(dto.getCor() != null ? dto.getCor() : "");
            txtPreco.setText(dto.getPreco() != null ? String.valueOf(dto.getPreco()) : "");
            txtQuantidade.setText(dto.getQuantidade() >= 0 ? String.valueOf(dto.getQuantidade()) : "");
        }
    }
}