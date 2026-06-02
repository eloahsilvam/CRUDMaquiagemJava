package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private static final Logger logger = Logger.getLogger(MaquiagemDAO.class.getName());

    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
    @FXML private Button btnLimpar;

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
    @FXML private TableColumn<MaquiagemDTO, Integer> colQuantidade;

    @FXML //cada fxml esta se comunicando com a view
    private void carregarMaquiagem() {
        MaquiagemDAO maquiagemDao = new MaquiagemDAO();
        List<MaquiagemDTO> listarMaquiagem = maquiagemDao.listar();
        tblMaquiagem.setItems(FXCollections.observableArrayList(listarMaquiagem));
    }

    @FXML
    private void carregarCampos(){
        MaquiagemDTO maquiagemDto = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemDto != null){
            txtID.setText(String.valueOf(maquiagemDto.getId()));
            txtNome.setText(maquiagemDto.getNome());
            txtMarca.setText(maquiagemDto.getMarca());
            txtCor.setText(maquiagemDto.getCor());
            txtPreco.setText(String.valueOf(maquiagemDto.getPreco()));
            txtQuantidade.setText(String.valueOf(maquiagemDto.getQuantidade())); // Corrigido o nome da variável
        }
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String marca = txtMarca.getText();
            String cor = txtCor.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            MaquiagemDTO maquiagemDto = new MaquiagemDTO();
            maquiagemDto.setNome(nome);
            maquiagemDto.setMarca(marca);
            maquiagemDto.setCor(cor);
            maquiagemDto.setPreco(preco);
            maquiagemDto.setQuantidade(quantidade);

            MaquiagemDAO maquiagemDao = new MaquiagemDAO();
            maquiagemDao.cadastrarMaquiagem(maquiagemDto);

            carregarMaquiagem();
            btnLimparAction(null);
            logger.log(Level.INFO, "Salvo com sucesso!");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao salvar.", e);
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        MaquiagemDTO maquiagemSelecionada = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {
            try {
                MaquiagemDTO maquiagemdto = new MaquiagemDTO();

                maquiagemdto.setId(maquiagemSelecionada.getId());

                maquiagemdto.setNome(txtNome.getText());
                maquiagemdto.setMarca(txtMarca.getText());
                maquiagemdto.setCor(txtCor.getText());
                maquiagemdto.setPreco(Double.parseDouble(txtPreco.getText()));
                maquiagemdto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));

                MaquiagemDAO maquiagemdao = new MaquiagemDAO();
                maquiagemdao.atualizar(maquiagemdto);

                carregarMaquiagem();
                btnLimparAction(null);
                logger.log(Level.INFO, "Editado com sucesso!");

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao editar", e);
            }
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        MaquiagemDTO maquiagemSelecionada = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {
            MaquiagemDAO maquiagemDao = new MaquiagemDAO();
            maquiagemDao.excluir(maquiagemSelecionada.getId());

            carregarMaquiagem();
            btnLimparAction(null);
            logger.log(Level.INFO, "Excluído com sucesso!");
        } else {
            logger.log(Level.WARNING, "Maquiagem nao selecionada.");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtID.clear();
        txtNome.clear();
        txtMarca.clear();
        txtCor.clear();
        txtPreco.clear();
        txtQuantidade.clear();
    }

    @FXML
    private void initialize() {
        logger.log(Level.INFO, "FXML loaded successfully!");

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        carregarMaquiagem();
        tblMaquiagem.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novoSelecionado) -> {
            carregarCampos();
        });
    }
}