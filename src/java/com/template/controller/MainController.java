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

import java.awt.event.MouseEvent;
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

    // Delegados de responsabilidade
    private final MaquiagemService services = new MaquiagemService();
    private final MaquiagemValidator validator = new MaquiagemValidator();

    @FXML
    private void initialize() {
        configurarTabela();
        configurarCamposUI();
        carregarMaquiagem();

        // Listener para seleção de linha na tabela
        tblMaquiagem.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            preencherFormulario(selecionado);
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

    private void configurarCamposUI() {
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

    private void carregarMaquiagem() {
        tblMaquiagem.setItems(FXCollections.observableArrayList(services.listar()));
    }

    private void preencherFormulario(MaquiagemDTO maquiagemDto) {
        if (maquiagemDto != null) {
            txtID.setText(String.valueOf(maquiagemDto.getId()));
            FormUtil.preencherCampos(maquiagemDto, txtNome, txtMarca, txtCor, txtPreco, txtQuantidade);

            btnEditar.setDisable(false);
            btnDeletar.setDisable(false);
            lblMensagem.setText("");
        }
    }

    // Método chamado pelo evento onMouseClicked do FXML
    @FXML
    private void carregarCampos(MouseEvent event) {
        MaquiagemDTO selecionado = tblMaquiagem.getSelectionModel().getSelectedItem();
        preencherFormulario(selecionado);
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        MaquiagemDTO dto = FormUtil.extrairDTO(txtNome, txtMarca, txtCor, txtPreco, txtQuantidade);

        List<String> erros = validator.validar(dto);
        if (!erros.isEmpty()) {
            lblMensagem.setText(erros.get(0));
            return;
        }

        services.salvar(dto);
        posAcaoSucesso("Maquiagem cadastrada!");
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        MaquiagemDTO selecionado = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            lblMensagem.setText("Aviso: Selecione uma maquiagem para editar.");
            return;
        }

        MaquiagemDTO dto = FormUtil.extrairDTO(txtNome, txtMarca, txtCor, txtPreco, txtQuantidade);
        dto.setId(selecionado.getId());

        List<String> erros = validator.validar(dto);
        if (!erros.isEmpty()) {
            lblMensagem.setText(erros.get(0));
            return;
        }

        services.salvar(dto);
        posAcaoSucesso("Maquiagem atualizada!");
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        MaquiagemDTO selecionado = tblMaquiagem.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            boolean confirmado = DialogUtil.exibirConfirmacao(
                    "Confirmação de Exclusão",
                    "Deseja realmente excluir a maquiagem: " + selecionado.getNome() + "?"
            );

            if (confirmado) {
                services.excluir(selecionado.getId());
                posAcaoSucesso("Registro excluído!");
            }
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        FormUtil.limparCampos(tblMaquiagem, txtID, txtNome, txtMarca, txtCor, txtPreco, txtQuantidade);

        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);

        if (event != null) {
            lblMensagem.setText("");
        }
        txtNome.requestFocus();
    }

    private void posAcaoSucesso(String mensagem) {
        carregarMaquiagem();
        btnLimparAction(null);
        lblMensagem.setText(mensagem);
        txtNome.requestFocus();
    }
}