package com.template.controller;

import com.template.model.dto.MaquiagemDTO;
import com.template.services.MaquiagemService;
import com.template.util.DialogUtil;
import com.template.util.FormUtil;
import com.template.validator.IMaquiagemValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class MainController {

    // [O QUE FAZ]: Guarda a referência do validador que virá do Main
    private final IMaquiagemValidator maquiagemValidator;

    // [CRIAÇÃO DE OBJETO]: Instancia diretamente a classe de serviço para manipular o banco de dados
    private final MaquiagemService maquiagemService = new MaquiagemService();

    // Mapeamento dos componentes visuais criados no Scene Builder / FXML
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

    // [INJEÇÃO DE DEPENDÊNCIA]: O objeto validador é criado fora (no Main) e passado para cá pelo construtor
    public MainController(IMaquiagemValidator maquiagemValidator) {
        this.maquiagemValidator = maquiagemValidator;
    }

    // [CICLO DE VIDA DO JAVAFX]: O JavaFX chama este método automaticamente após carregar a tela
    @FXML
    private void initialize() {
        configurarTabela();
        configurarCampos();
        carregarMaquiagens();

        // [UM CHAMA O OUTRO / OBSERVER]:
        // 1. tblMaquiagem.getSelectionModel(): Pega o gerenciador de seleções da tabela.
        // 2. selectedItemProperty().addListener(): Fica "escutando" a tabela.
        // 3. Quando o usuário clica em uma linha, chama o método preencherFormulario(novaMaquiagem).
        tblMaquiagem.getSelectionModel().selectedItemProperty()
                .addListener((observacao, antigaMaquiagem, novaMaquiagem) -> {
                    preencherFormulario(novaMaquiagem);
                });
    }

    // [O QUE FAZ]: Associa cada coluna da tabela ao atributo correspondente do objeto MaquiagemDTO (via Reflection / Getters)
    private void configurarTabela() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    }

    // [O QUE FAZ]: Configura o estado inicial da tela (textos de dica e botões desabilitados)
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

    // [UM CHAMA O OUTRO]:
    // Chama maquiagemService.listar() para pegar os dados e atualiza os itens exibidos na TableView
    private void carregarMaquiagens() {
        tblMaquiagem.setItems(
                FXCollections.observableArrayList(
                        maquiagemService.listar()
                )
        );
    }

    // [UM CHAMA O OUTRO]:
    // Chama a classe utilitária FormUtil para jogar os dados do objeto MaquiagemDTO dentro das caixas de texto
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

    // [O QUE FAZ]: Método ativado quando o usuário clica com o mouse sobre a tabela
    @FXML
    private void carregarCampos(MouseEvent evento) {
        // [OBTÉM OBJETO SELECIONADO]:
        // tblMaquiagem.getSelectionModel().getSelectedItem() descobre qual objeto MaquiagemDTO está destacado na linha clicada
        MaquiagemDTO maquiagemSelecionada =
                tblMaquiagem.getSelectionModel().getSelectedItem();

        // Chama o método para preencher as caixas de texto
        preencherFormulario(maquiagemSelecionada);
    }

    // [O QUE FAZ]: Executado ao clicar no botão "Salvar"
    @FXML
    private void btnSalvarAction(ActionEvent evento) {

        // [CRIAÇÃO DE OBJETO]:
        // Chama FormUtil.extrairMaquiagem que lê os TextField e CRIA (instancia) um novo objeto MaquiagemDTO
        MaquiagemDTO maquiagem =
                FormUtil.extrairMaquiagem(
                        txtNome,
                        txtMarca,
                        txtCor,
                        txtPreco,
                        txtQuantidade
                );

        // [UM CHAMA O OUTRO]: Envia o objeto DTO recém-criado para o validador checar as regras
        List<String> erros = maquiagemValidator.validar(maquiagem);

        // Se houver erros, exibe a mensagem na tela e para a execução
        if (!erros.isEmpty()) {
            lblMensagem.setText(erros.get(0));
            return;
        }

        // [UM CHAMA O OUTRO]: Se passou na validação, chama a camada de serviço para salvar no banco
        maquiagemService.salvar(maquiagem);

        // Chama o método auxiliar para atualizar a tela
        posAcaoSucesso("Maquiagem cadastrada!");
    }

    // [O QUE FAZ]: Executado ao clicar no botão "Editar"
    @FXML
    private void btnEditarAction(ActionEvent evento) {

        // [OBTÉM OBJETO SELECIONADO]: Pega o objeto da maquiagem selecionada na tabela
        MaquiagemDTO maquiagemSelecionada =
                tblMaquiagem.getSelectionModel().getSelectedItem();

        // Validação defensiva: Impede a edição caso nada esteja selecionado
        if (maquiagemSelecionada == null) {
            lblMensagem.setText("Aviso: Selecione uma maquiagem para editar.");
            return;
        }

        // [CRIAÇÃO DE OBJETO]: Cria um novo objeto DTO com os novos dados digitados nas caixas de texto
        MaquiagemDTO maquiagem =
                FormUtil.extrairMaquiagem(
                        txtNome,
                        txtMarca,
                        txtCor,
                        txtPreco,
                        txtQuantidade
                );

        // Mantém o mesmo ID original para garantir que o banco atualize o registro correto (UPDATE)
        maquiagem.setId(maquiagemSelecionada.getId());

        // [UM CHAMA O OUTRO]: Envia para validação
        List<String> erros = maquiagemValidator.validar(maquiagem);

        if (!erros.isEmpty()) {
            lblMensagem.setText(erros.get(0));
            return;
        }

        // [UM CHAMA O OUTRO]: Salva as edições no banco
        maquiagemService.salvar(maquiagem);

        posAcaoSucesso("Maquiagem atualizada!");
    }

    // [O QUE FAZ]: Executado ao clicar no botão "Deletar"
    @FXML
    private void btnDeletarAction(ActionEvent evento) {

        // [OBTÉM OBJETO SELECIONADO]: Pega o registro ativo na tabela
        MaquiagemDTO maquiagemSelecionada =
                tblMaquiagem.getSelectionModel().getSelectedItem();

        if (maquiagemSelecionada != null) {

            // [UM CHAMA O OUTRO]: Chama DialogUtil para abrir uma caixa de diálogo na tela perguntando se confirma
            boolean confirmou =
                    DialogUtil.exibirConfirmacao(
                            "Confirmação de Exclusão",
                            "Deseja realmente excluir a maquiagem: "
                                    + maquiagemSelecionada.getNome() + "?"
                    );

            // Se o usuário clicou em "Sim"
            if (confirmou) {
                // [UM CHAMA O OUTRO]: Chama o serviço enviando apenas o ID do registro para deletar
                maquiagemService.excluir(
                        maquiagemSelecionada.getId()
                );

                posAcaoSucesso("Registro excluído!");
            }
        }
    }

    // [O QUE FAZ]: Reseta o formulário e limpa a seleção da tabela
    @FXML
    private void btnLimparAction(ActionEvent evento) {

        // [UM CHAMA O OUTRO]: Limpa os campos de texto usando a classe utilitária
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

        // Coloca o cursor de digitação de volta no campo Nome
        txtNome.requestFocus();
    }

    // [O QUE FAZ]: Método utilitário interno executado após criar, editar ou excluir com sucesso
    private void posAcaoSucesso(String mensagem) {

        // [UM CHAMA O OUTRO]: Recarrega a tabela com os dados atualizados do banco
        carregarMaquiagens();

        // [UM CHAMA O OUTRO]: Limpa os campos da tela chamando o método de limpar
        btnLimparAction(null);

        lblMensagem.setText(mensagem);

        txtNome.requestFocus();
    }
}