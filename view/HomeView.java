package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HomeView {

    public void show(Stage stage) {
        stage.setTitle("EcoColeta — Home");

        // ── Top bar ────────────────────────────────────────────────
        HBox topBar = criarTopBar(stage, "EcoColeta");

        // ── Mapa simulado ──────────────────────────────────────────
        Pane mapa = criarMapaSimulado();

        // ── Lista de pontos ────────────────────────────────────────
        Label lblPontos = new Label("Pontos de Coleta Próximos");
        lblPontos.setStyle(EstiloApp.labelTitulo());
        lblPontos.setPadding(new Insets(12, 16, 4, 16));

        VBox listaPontos = new VBox(8);
        listaPontos.setPadding(new Insets(0, 16, 16, 16));

        String[][] pontos = {
            {"1", "Ponto Verde Centro",      "Rua das Flores, 100",    "São Paulo", "Papel, Plástico", "aberto"},
            {"2", "Eco Ponto Jardins",       "Av. Paulista, 500",      "São Paulo", "Metal, Vidro",    "aberto"},
            {"3", "Coleta Sustentável Sul",  "Rua XV, 230",            "Curitiba",  "Orgânico",        "fechado"},
            {"4", "Ponto Reciclagem Leste",  "Av. Brasil, 780",        "Rio",       "Eletrônicos",     "aberto"},
            {"5", "EcoCentro Oeste",         "Rua Ipiranga, 45",       "BH",        "Papel, Metal",    "aberto"},
        };

        for (String[] p : pontos) {
            listaPontos.getChildren().add(criarCardPonto(stage, p));
        }

        ScrollPane scrollLista = new ScrollPane(listaPontos);
        scrollLista.setFitToWidth(true);
        scrollLista.setStyle("-fx-background-color:transparent;");

        VBox corpo = new VBox(lblPontos, scrollLista);
        VBox.setVgrow(scrollLista, Priority.ALWAYS);

        VBox root = new VBox(topBar, mapa, corpo);
        VBox.setVgrow(corpo, Priority.ALWAYS);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        stage.setScene(new Scene(root, 420, 680));
        stage.show();
    }

    // ── Card de ponto na lista ─────────────────────────────────────
    private HBox criarCardPonto(Stage stage, String[] p) {
        // Número
        Label num = new Label(p[0]);
        num.setStyle("-fx-background-color:" + EstiloApp.VERDE_ESCURO + ";"
                + "-fx-text-fill:white; -fx-font-weight:bold;"
                + "-fx-min-width:30; -fx-min-height:30;"
                + "-fx-alignment:center; -fx-background-radius:6;");

        // Info
        Label nome = new Label(p[1]);
        nome.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        Label endereco = new Label(p[2] + " — " + p[3]);
        endereco.setStyle(EstiloApp.labelSecao() + "-fx-font-size:11px;");
        Label material = new Label("♻ " + p[4]);
        material.setStyle("-fx-font-size:11px; -fx-text-fill:" + EstiloApp.VERDE_MEDIO + ";");

        VBox info = new VBox(2, nome, endereco, material);
        HBox.setHgrow(info, Priority.ALWAYS);

        // Status + ações
        String cor = p[5].equals("aberto") ? EstiloApp.VERDE_CLARO : EstiloApp.CINZA_BORDA;
        Label status = new Label(p[5].equals("aberto") ? "● aberto" : "● fechado");
        status.setStyle("-fx-font-size:10px; -fx-text-fill:" + cor + ";");

        Button btnAvaliar = new Button("Avaliar");
        btnAvaliar.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";"
                + "-fx-text-fill:" + EstiloApp.VERDE_ESCURO + ";"
                + "-fx-font-size:10px; -fx-background-radius:4; -fx-cursor:hand; -fx-padding:3 6;");
        btnAvaliar.setOnAction(e -> new AvaliacaoView().show(stage, p[1]));

        VBox acoes = new VBox(4, status, btnAvaliar);
        acoes.setAlignment(Pos.CENTER_RIGHT);

        HBox card = new HBox(10, num, info, acoes);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(EstiloApp.card());
        card.setPadding(new Insets(10));
        return card;
    }

    // ── Mapa simulado (placeholder visual) ────────────────────────
    private Pane criarMapaSimulado() {
        Pane mapa = new Pane();
        mapa.setPrefHeight(180);
        mapa.setStyle("-fx-background-color:#C8D8C8;");

        // Grade de ruas
        for (int i = 0; i < 8; i++) {
            Rectangle h = new Rectangle(0, i * 24, 420, 2);
            h.setFill(Color.web("#B0C4B0"));
            Rectangle v = new Rectangle(i * 55, 0, 2, 180);
            v.setFill(Color.web("#B0C4B0"));
            mapa.getChildren().addAll(h, v);
        }

        // Pins de pontos
        int[][] pins = {{80, 60}, {180, 100}, {290, 50}, {340, 130}, {120, 140}};
        for (int[] pin : pins) {
            Label p = new Label("📍");
            p.setLayoutX(pin[0]);
            p.setLayoutY(pin[1]);
            p.setStyle("-fx-font-size:18px;");
            mapa.getChildren().add(p);
        }

        Label lblMapa = new Label("Mapa de Pontos de Coleta");
        lblMapa.setStyle("-fx-background-color:rgba(0,0,0,0.35); -fx-text-fill:white;"
                + "-fx-padding:4 8; -fx-background-radius:4; -fx-font-size:11px;");
        lblMapa.setLayoutX(10);
        lblMapa.setLayoutY(10);
        mapa.getChildren().add(lblMapa);

        return mapa;
    }

    // ── Top bar reutilizável ───────────────────────────────────────
    static HBox criarTopBar(Stage stage, String titulo) {
        Button btnMenu = new Button("☰");
        btnMenu.setStyle("-fx-background-color:transparent; -fx-text-fill:white;"
                + "-fx-font-size:18px; -fx-cursor:hand;");
        btnMenu.setOnAction(e -> new MenuView().show(stage));

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-text-fill:white; -fx-font-size:16px; -fx-font-weight:bold;");
        HBox.setHgrow(lblTitulo, Priority.ALWAYS);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        Label lblAvatar = new Label("👤");
        lblAvatar.setStyle("-fx-font-size:18px;");

        HBox bar = new HBox(10, btnMenu, lblTitulo, lblAvatar);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setStyle(EstiloApp.topBar());
        return bar;
    }
}
