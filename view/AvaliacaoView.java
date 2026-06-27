package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class AvaliacaoView {

    public void show(Stage stage, String nomePonto) {
        stage.setTitle("EcoColeta — Avaliação");

        // ── Top bar ────────────────────────────────────────────────
        Button btnVoltar = new Button("←");
        btnVoltar.setStyle("-fx-background-color:transparent; -fx-text-fill:white;"
                + "-fx-font-size:16px; -fx-cursor:hand;");
        btnVoltar.setOnAction(e -> new HomeView().show(stage));

        Label lblTop = new Label("Avaliar Ponto");
        lblTop.setStyle("-fx-text-fill:white; -fx-font-size:16px; -fx-font-weight:bold;");
        HBox.setHgrow(lblTop, Priority.ALWAYS);
        lblTop.setMaxWidth(Double.MAX_VALUE);
        lblTop.setAlignment(Pos.CENTER);

        HBox topBar = new HBox(10, btnVoltar, lblTop, new Label("  "));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle(EstiloApp.topBar());

        // ── Nome do ponto ──────────────────────────────────────────
        String nome = nomePonto != null ? nomePonto : "Selecione um ponto";
        Label lblNome = new Label("📍 " + nome);
        lblNome.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-text-fill:" + EstiloApp.VERDE_ESCURO + ";");
        lblNome.setWrapText(true);

        // ── Estrelas ───────────────────────────────────────────────
        Label lblNota = new Label("Sua nota");
        lblNota.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");

        HBox estrelas = new HBox(6);
        estrelas.setAlignment(Pos.CENTER_LEFT);
        int[] notaSelecionada = {0};
        Label[] btnEstrelas = new Label[5];
        for (int i = 0; i < 5; i++) {
            final int nota = i + 1;
            Label estrela = new Label("☆");
            estrela.setStyle("-fx-font-size:28px; -fx-cursor:hand; -fx-text-fill:#FFA000;");
            estrela.setOnMouseClicked(e -> {
                notaSelecionada[0] = nota;
                for (int j = 0; j < 5; j++) {
                    btnEstrelas[j].setText(j < nota ? "★" : "☆");
                }
            });
            btnEstrelas[i] = estrela;
            estrelas.getChildren().add(estrela);
        }

        // ── Descrição ──────────────────────────────────────────────
        Label lblDesc = new Label("Descrição");
        lblDesc.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");
        Label opc = new Label("*Opcional");
        opc.setStyle("-fx-font-size:10px; -fx-text-fill:" + EstiloApp.CINZA_TEXTO + ";");
        HBox hDesc = new HBox(lblDesc, new Region(), opc);
        HBox.setHgrow(hDesc.getChildren().get(1), Priority.ALWAYS);

        TextArea txtDesc = new TextArea();
        txtDesc.setPromptText("Conte sua experiência neste ponto de coleta...");
        txtDesc.setPrefRowCount(4);
        txtDesc.setStyle(EstiloApp.campotexto());

        // ── Imagem ────────────────────────────────────────────────
        Label lblImg = new Label("Envie uma imagem");
        lblImg.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");
        Button btnImg = new Button("📎  Escolher uma imagem");
        btnImg.setStyle(EstiloApp.botaoSecundario());
        btnImg.setMaxWidth(Double.MAX_VALUE);

        // ── Anonimato ─────────────────────────────────────────────
        Label lblAnon = new Label("Deseja anonimato");
        lblAnon.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbSim = new RadioButton("Sim"); rbSim.setToggleGroup(tg);
        RadioButton rbNao = new RadioButton("Não");  rbNao.setToggleGroup(tg);
        rbNao.setSelected(true);
        HBox radioBox = new HBox(20, rbSim, rbNao);

        // ── Botão enviar ───────────────────────────────────────────
        Button btnEnviar = new Button("Enviar Avaliação");
        btnEnviar.setStyle(EstiloApp.botaoPrimario());
        btnEnviar.setMaxWidth(Double.MAX_VALUE);
        btnEnviar.setOnAction(e -> {
            if (notaSelecionada[0] == 0) {
                alerta("Selecione uma nota de 1 a 5 estrelas.");
                return;
            }
            sucesso("Avaliação enviada! Nota: " + notaSelecionada[0] + " ⭐");
            new HomeView().show(stage);
        });

        // ── Histórico de avaliações ────────────────────────────────
        Label lblHistorico = new Label("Avaliações recentes");
        lblHistorico.setStyle(EstiloApp.labelTitulo() + "-fx-font-size:14px;");

        VBox historico = new VBox(8);
        String[][] avals = {
            {"Maria S.", "★★★★★", "Ótimo ponto, muito organizado!"},
            {"João P.",  "★★★☆☆", "Funciona bem, mas poderia ter mais horários."},
            {"Ana L.",   "★★★★☆", "Bem localizado, equipe atenciosa."},
        };
        for (String[] av : avals) {
            historico.getChildren().add(criarCardAvaliacao(av));
        }

        VBox form = new VBox(12,
                lblNome,
                new Separator(),
                lblNota, estrelas,
                hDesc, txtDesc,
                lblImg, btnImg,
                lblAnon, radioBox,
                btnEnviar,
                new Separator(),
                lblHistorico,
                historico
        );
        form.setPadding(new Insets(16));

        ScrollPane scroll = new ScrollPane(form);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        VBox root = new VBox(topBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        stage.setScene(new Scene(root, 420, 680));
        stage.show();
    }

    private VBox criarCardAvaliacao(String[] av) {
        Label nome   = new Label(av[0]);
        nome.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        Label nota   = new Label(av[1]);
        nota.setStyle("-fx-text-fill:#FFA000; -fx-font-size:13px;");
        Label texto  = new Label(av[2]);
        texto.setStyle(EstiloApp.labelSecao() + "-fx-font-size:12px;");
        texto.setWrapText(true);

        HBox topo = new HBox(10, nome, nota);
        topo.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(4, topo, texto);
        card.setStyle(EstiloApp.card());
        return card;
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
