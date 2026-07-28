package com.template.controller;

import com.template.model.dao.MaquiagemDAO;
import com.template.model.dto.MaquiagemDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.template.util.DialogUtil.exibirConfirmacao;

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

    @FXML private Label lblMensagem;

    @FXML private TableView<MaquiagemDTO> tblMaquiagem;
    @FXML private TableColumn<MaquiagemDTO, Integer> colID;
    @FXML private TableColumn<MaquiagemDTO, String> colNome;
    @FXML private TableColumn<MaquiagemDTO, String> colMarca;
    @FXML private TableColumn<MaquiagemDTO, String> colCor;
    @FXML private TableColumn<MaquiagemDTO, Double> colPreco;
    @FXML private TableColumn<MaquiagemDTO, Integer> colQuantidade;

    @FXML
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
            txtQuantidade.setText(String.valueOf(maquiagemDto.getQuantidade()));

            btnEditar.setDisable(false);
            btnDeletar.setDisable(false);
            lblMensagem.setText("");
        }
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (txtNome.getText().trim().isEmpty() ||
                txtMarca.getText().trim().isEmpty() ||
                txtPreco.getText().trim().isEmpty() ||
                txtQuantidade.getText().trim().isEmpty()) {

            lblMensagem.setText("Aviso:Preencha todos os campos!");
            return;
        }

        try {
            MaquiagemDTO maquiagemDto = new MaquiagemDTO();

            maquiagemDto.setNome(txtNome.getText());
            maquiagemDto.setMarca(txtMarca.getText());
            maquiagemDto.setCor(txtCor.getText());
            maquiagemDto.setPreco(Double.parseDouble(txtPreco.getText()));
            maquiagemDto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            MaquiagemDAO maquiagemDao = new MaquiagemDAO();

            maquiagemDao.cadastrarMaquiagem(maquiagemDto);

            carregarMaquiagem();
            btnLimparAction(null);

            lblMensagem.setText("Maquiagem cadastrada!");

            txtNome.requestFocus();

        } catch (NumberFormatException e) {
            lblMensagem.setText("Erro: números inválidos.");
        } catch (Exception e) {
            lblMensagem.setText("Erro ao salvar o registro.");
            logger.log(Level.SEVERE, "Erro ao salvar.", e);
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        MaquiagemDTO maquiagemSelecionada = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {
            if (txtNome.getText().trim().isEmpty() ||
                    txtMarca.getText().trim().isEmpty() ||
                    txtPreco.getText().trim().isEmpty() ||
                    txtQuantidade.getText().trim().isEmpty()) {

                lblMensagem.setText("Aviso:Não é possível editar");
                return;
            }

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

                lblMensagem.setText("Maquiagem atualizada!");

            } catch (NumberFormatException e) {
                lblMensagem.setText("Erro:use apenas nímeros.");
            } catch (Exception e) {
                lblMensagem.setText("Erro ao atualizar");
                logger.log(Level.SEVERE, "Erro ao editar", e);
            }
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        MaquiagemDTO maquiagemSelecionada = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {
            boolean confirmado = exibirConfirmacao("Confirmação de Exclusão",
                    "Deseja realmente excluir a maquiagem: " + maquiagemSelecionada.getNome() + "?");

            if (confirmado) {
                MaquiagemDAO maquiagemDao = new MaquiagemDAO();
                maquiagemDao.excluir(maquiagemSelecionada.getId());

                carregarMaquiagem();
                btnLimparAction(null);
                lblMensagem.setText("Registro excluído!");
            }
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

        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);
        if (event != null) {
            lblMensagem.setText("");
        }
        txtNome.requestFocus();
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

        txtNome.setPromptText("Ex: Batom Matte");
        txtMarca.setPromptText("Ex: Ruby Rose");
        txtCor.setPromptText("Ex: Vermelho");
        txtPreco.setPromptText("Ex: 29.90");
        txtQuantidade.setPromptText("Ex: 10");

        txtID.setEditable(false);
        txtID.setDisable(true);

        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);

        carregarMaquiagem();

        tblMaquiagem.getSelectionModel().selectedItemProperty().addListener((obs, antigo,定位) -> {
            carregarCampos();
        });
    }
}