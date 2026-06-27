package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class PontoColetaView {

    /**
     * @param modo "adicionar" ou "remover"
     * @param nomePonto nome do ponto (para modo remover), pode ser null
     */
    public void show(Stage stage, String modo, String nomePonto) {
        if ("remover".equals(modo)) {
            mostrarRemover(stage, nomePonto);
        } else {
            mostrarAdicionar(stage);
        }
    }

    // ── Tela Adicionar ─────────────────────────────────────────────
    private void mostrarAdicionar(Stage stage) {
        stage.setTitle("EcoColeta — Adicionar Ponto");

        HBox topBar = topBarVoltar(stage, "Adicionar ponto de coleta");

        // Campos
        Label lblDesc = new Label("Descrição");
        lblDesc.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        Label obg1 = new Label("*Obrigatório");
        obg1.setStyle("-fx-font-size:10px; -fx-text-fill:" + EstiloApp.VERMELHO + ";");
        HBox h1 = new HBox(lblDesc, new Region(), obg1);
        HBox.setHgrow(h1.getChildren().get(1), Priority.ALWAYS);

        TextArea txtDesc = new TextArea();
        txtDesc.setPromptText("Descreva o ponto de coleta e os materiais aceitos...");
        txtDesc.setPrefRowCount(3);
        txtDesc.setStyle(EstiloApp.campotexto() + "-fx-pref-row-count:3;");

        Label lblLocal = new Label("Local");
        lblLocal.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        Label obg2 = new Label("*Obrigatório");
        obg2.setStyle("-fx-font-size:10px; -fx-text-fill:" + EstiloApp.VERMELHO + ";");
        HBox h2 = new HBox(lblLocal, new Region(), obg2);
        HBox.setHgrow(h2.getChildren().get(1), Priority.ALWAYS);

        TextField txtRua  = campo("Rua");
        TextField txtCep  = campo("CEP");
        TextField txtCidade = campo("Cidade");
        TextField txtDesc2  = campo("Descrição do estabelecimento (nome)");

        Label lblImagem = new Label("Envie uma imagem");
        lblImagem.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        Label opc = new Label("*Opcional");
        opc.setStyle("-fx-font-size:10px; -fx-text-fill:" + EstiloApp.CINZA_TEXTO + ";");
        HBox h3 = new HBox(lblImagem, new Region(), opc);
        HBox.setHgrow(h3.getChildren().get(1), Priority.ALWAYS);

        Button btnImagem = new Button("📎  Escolher uma imagem");
        btnImagem.setStyle(EstiloApp.botaoSecundario());
        btnImagem.setMaxWidth(Double.MAX_VALUE);

        Label lblAnon = new Label("Deseja anonimato");
        lblAnon.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        Label opc2 = new Label("*Opcional");
        opc2.setStyle("-fx-font-size:10px; -fx-text-fill:" + EstiloApp.CINZA_TEXTO + ";");
        HBox h4 = new HBox(lblAnon, new Region(), opc2);
        HBox.setHgrow(h4.getChildren().get(1), Priority.ALWAYS);

        ToggleGroup tgAnon = new ToggleGroup();
        RadioButton rbSim = new RadioButton("Sim"); rbSim.setToggleGroup(tgAnon);
        RadioButton rbNao = new RadioButton("Não");  rbNao.setToggleGroup(tgAnon);
        rbNao.setSelected(true);
        HBox radioBox = new HBox(20, rbSim, rbNao);

        Button btnEnviar = new Button("Enviar");
        btnEnviar.setStyle(EstiloApp.botaoPrimario());
        btnEnviar.setMaxWidth(Double.MAX_VALUE);
        btnEnviar.setOnAction(e -> {
            if (txtDesc.getText().isEmpty() || txtRua.getText().isEmpty()) {
                alerta("Preencha os campos obrigatórios.");
                return;
            }
            sucesso("Ponto de coleta adicionado com sucesso!");
            new HomeView().show(stage);
        });

        VBox form = new VBox(10,
                h1, txtDesc,
                h2, txtRua, txtCep, txtCidade, txtDesc2,
                h3, btnImagem,
                h4, radioBox,
                new Region(), btnEnviar
        );
        form.setPadding(new Insets(16));

        ScrollPane scroll = new ScrollPane(form);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        VBox root = new VBox(topBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        stage.setScene(new Scene(root, 420, 640));
        stage.show();
    }

    // ── Tela Remover ───────────────────────────────────────────────
    private void mostrarRemover(Stage stage, String nomePonto) {
        stage.setTitle("EcoColeta — Remover Ponto");

        HBox topBar = topBarVoltar(stage, "Remover ponto de coleta");

        String nome = nomePonto != null ? nomePonto : "Ponto de Coleta";

        Label lblPergunta = new Label(
                "Tem certeza de que deseja excluir o ponto:\n\"" + nome + "\"?\nEsta ação não poderá ser desfeita.");
        lblPergunta.setStyle("-fx-font-size:14px; -fx-text-fill:#212121;");
        lblPergunta.setWrapText(true);

        Button btnSim = new Button("Sim, remover");
        btnSim.setStyle(EstiloApp.botaoPerigo());
        btnSim.setMaxWidth(Double.MAX_VALUE);
        btnSim.setOnAction(e -> {
            sucesso("Ponto removido com sucesso.");
            new HomeView().show(stage);
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(EstiloApp.botaoSecundario());
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setOnAction(e -> new HomeView().show(stage));

        VBox conteudo = new VBox(20, lblPergunta, btnSim, btnCancelar);
        conteudo.setPadding(new Insets(24));
        conteudo.setStyle(EstiloApp.card());

        VBox root = new VBox(topBar, new VBox(conteudo) {{
            setPadding(new Insets(20));
        }});
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        stage.setScene(new Scene(root, 420, 400));
        stage.show();
    }

    // ── Helpers ────────────────────────────────────────────────────
    private TextField campo(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(EstiloApp.campotexto());
        return tf;
    }

    private HBox topBarVoltar(Stage stage, String titulo) {
        Button btnVoltar = new Button("← Voltar ao Início");
        btnVoltar.setStyle("-fx-background-color:transparent; -fx-text-fill:white;"
                + "-fx-font-size:13px; -fx-cursor:hand;");
        btnVoltar.setOnAction(e -> new HomeView().show(stage));

        Label lblT = new Label(titulo);
        lblT.setStyle("-fx-text-fill:white; -fx-font-size:14px; -fx-font-weight:bold;");

        VBox bar = new VBox(4, btnVoltar, lblT);
        bar.setStyle(EstiloApp.topBar());
        return new HBox(bar);
    }

    private void alerta(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        a.setHeaderText(null); a.showAndWait();
    }

    private void sucesso(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null); a.showAndWait();
    }
}
