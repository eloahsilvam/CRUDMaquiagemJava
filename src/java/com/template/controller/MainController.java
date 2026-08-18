package com.template.controller;


import com.template.model.dto.MaquiagemDTO;
import com.template.services.MaquiagemService;
import com.template.util.DialogUtil;
import com.template.util.FormUtil;
import com.template.validator.MaquiagemValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import java.util.List;


public class MainController {
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

    private final MaquiagemService maquiagemService = new MaquiagemService();
    private final MaquiagemValidator maquiagemValidator = new MaquiagemValidator();

    @FXML
    private void initialize() {
        configurarTabela();
        configurarCampos();
        carregarMaquiagens();

        tblMaquiagem.getSelectionModel().selectedItemProperty()
                .addListener((observacao, antigaMaquiagem, novaMaquiagem) -> {
                    preencherFormulario(novaMaquiagem);
                });
    }

    private void configurarTabela() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    }

    private void configurarCampos() {
        txtNome.setPromptText("Ex: Batom Matte");
        txtMarca.setPromptText("Ex: Ruby Rose");
        txtCor.setPromptText("Ex: Vermelho");
        txtPreco.setPromptText("Ex: 29.90");
        txtQuantidade.setPromptText("Ex: 10");

        txtID.setEditable(false);
        txtID.setDisable(true);

        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    private void carregarMaquiagens() {
        tblMaquiagem.setItems(
                FXCollections.observableArrayList(
                        maquiagemService.listar()
                )
        );
    }

    private void preencherFormulario(MaquiagemDTO maquiagem) {
        if (maquiagem != null) {
            txtID.setText(String.valueOf(maquiagem.getId()));

            FormUtil.preencherCampos(
                    maquiagem,
                    txtNome,
                    txtMarca,
                    txtCor,
                    txtPreco,
                    txtQuantidade
            );

            btnEditar.setDisable(false);
            btnDeletar.setDisable(false);
            lblMensagem.setText("");
        }
    }

    @FXML
    private void carregarCampos(MouseEvent evento) {
        MaquiagemDTO maquiagemSelecionada =
                tblMaquiagem.getSelectionModel().getSelectedItem();

        preencherFormulario(maquiagemSelecionada);
    }

    @FXML
    private void btnSalvarAction(ActionEvent evento) {

        MaquiagemDTO maquiagem =
                FormUtil.extrairMaquiagem(
                        txtNome,
                        txtMarca,
                        txtCor,
                        txtPreco,
                        txtQuantidade
                );

        List<String> erros =
                maquiagemValidator.validar(maquiagem);

        if (!erros.isEmpty()) {
            lblMensagem.setText(erros.get(0));
            return;
        }

        maquiagemService.salvar(maquiagem);

        posAcaoSucesso("Maquiagem cadastrada!");
    }

    @FXML
    private void btnEditarAction(ActionEvent evento) {

        MaquiagemDTO maquiagemSelecionada =
                tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada == null) {
            lblMensagem.setText(
                    "Aviso: Selecione uma maquiagem para editar."
            );
            return;
        }

        MaquiagemDTO maquiagem =
                FormUtil.extrairMaquiagem(
                        txtNome,
                        txtMarca,
                        txtCor,
                        txtPreco,
                        txtQuantidade
                );

        maquiagem.setId(maquiagemSelecionada.getId());

        List<String> erros =
                maquiagemValidator.validar(maquiagem);

        if (!erros.isEmpty()) {
            lblMensagem.setText(erros.get(0));
            return;
        }

        maquiagemService.salvar(maquiagem);

        posAcaoSucesso("Maquiagem atualizada!");
    }

    @FXML
    private void btnDeletarAction(ActionEvent evento) {

        MaquiagemDTO maquiagemSelecionada =
                tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {

            boolean confirmou =
                    DialogUtil.exibirConfirmacao(
                            "Confirmação de Exclusão",
                            "Deseja realmente excluir a maquiagem: "
                                    + maquiagemSelecionada.getNome() + "?"
                    );

            if (confirmou) {
                maquiagemService.excluir(
                        maquiagemSelecionada.getId()
                );

                posAcaoSucesso("Registro excluído!");
            }
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent evento) {

        FormUtil.limparCampos(
                tblMaquiagem,
                txtID,
                txtNome,
                txtMarca,
                txtCor,
                txtPreco,
                txtQuantidade
        );

        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);

        lblMensagem.setText("");

        txtNome.requestFocus();
    }

    private void posAcaoSucesso(String mensagem) {

        carregarMaquiagens();

        btnLimparAction(null);

        lblMensagem.setText(mensagem);

        txtNome.requestFocus();
    }
}