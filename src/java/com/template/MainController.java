package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private static final Logger logger = Logger.getLogger(MaquiagemDAO.class.getName());
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
    @FXML private TextField txtID;
    @FXML private TextField txtNome;
    @FXML private TextField txtMarca;
    @FXML private TextField txtCor;
    @FXML private TextField txtPreco;
    @FXML private TextField txtQuantidade;
    @FXML private TableView<MaquiagemDTO> tblMaquiagem;
    @FXML private TableColumn<MaquiagemDTO, Integer> colID;
    @FXML private TableColumn<MaquiagemDTO, String> colNome;
    @FXML private TableColumn<MaquiagemDTO, String> colMarca;
    @FXML private TableColumn<MaquiagemDTO, String> colCor;
    @FXML private TableColumn<MaquiagemDTO, Double> colPreco;
    @FXML private TableColumn<MaquiagemDTO, Double> colQuantidade;
    @FXML
    private void carregarMaquiagem() {
        MaquiagemDAO maquiagemDAO = new MaquiagemDAO();
        List<MaquiagemDTO> listarMaquiagem = maquiagemDAO.listar();
        tblMaquiagem.setItems(FXCollections.observableArrayList(listarMaquiagem));
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String nome = txtNome.getText();

        MaquiagemDTO maquiagemdto = new MaquiagemDTO();
        maquiagemdto.setNome(nome);

        MaquiagemDAO maquiagemdao = new MaquiagemDAO();
        maquiagemdao.cadastrarMaquiagem(maquiagemdto);

        carregarMaquiagem();

    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        MaquiagemDTO maquiagemSelecionada = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {
            MaquiagemDAO maquiagemdao = new MaquiagemDAO();
            maquiagemdao.excluir(maquiagemSelecionada.getId());
            carregarMaquiagem();
        } else {
            logger.log(Level.WARNING, "Maquiagem nao selecionada.");
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        MaquiagemDTO maquiagemSelecionada = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {
            String novoNome = txtNome.getText();

            MaquiagemDTO maquiagemdto = new MaquiagemDTO();
            maquiagemdto.setId(maquiagemSelecionada.getId());
            maquiagemdto.setNome(novoNome);

            MaquiagemDAO maquiagemdao = new MaquiagemDAO();
            maquiagemdao.atualizar(maquiagemdto);
            carregarMaquiagem();
        } else {
            logger.log(Level.WARNING, "Maquiagem nao selecionada.");
        }
    }

    private void initialize()
    {
        logger.log(Level.INFO, "FXML loaded successfully!");

    }
}

