package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MenuView {

    public void show(Stage stage) {
        stage.setTitle("EcoColeta — Menu");

        // ── Cabeçalho ──────────────────────────────────────────────
        VBox header = new VBox(6);
        header.setStyle("-fx-background-color:" + EstiloApp.VERDE_ESCURO + ";");
        header.setPadding(new Insets(24, 16, 20, 16));

        Text logo = new Text("🌿 EcoColeta");
        logo.setFont(Font.font("System", FontWeight.BOLD, 20));
        logo.setFill(Color.WHITE);

        Text sub = new Text("Menu principal");
        sub.setFont(Font.font(12));
        sub.setFill(Color.web("#A5D6A7"));

        header.getChildren().addAll(logo, sub);

        // ── Itens do menu ──────────────────────────────────────────
        VBox itens = new VBox(6);
        itens.setPadding(new Insets(16));

        itens.getChildren().addAll(
                itemMenu("🏠", "Início",                  e -> new HomeView().show(stage)),
                separadorMenu("Pontos de Coleta"),
                itemMenu("➕", "Adicionar ponto de coleta", e -> new PontoColetaView().show(stage, "adicionar", null)),
                itemMenu("📍", "Ver pontos no mapa",       e -> new HomeView().show(stage)),
                itemMenu("✏️", "Editar ponto de coleta",   e -> new HomeView().show(stage)),
                separadorMenu("Avaliações"),
                itemMenu("⭐", "Adicionar avaliação",      e -> new AvaliacaoView().show(stage, null)),
                separadorMenu("Materiais"),
                itemMenu("♻️", "Tipos de material",        e -> new TipoMaterialView().show(stage)),
                separadorMenu("Guia"),
                itemMenu("📖", "Guia Informativo",         e -> new GuiaInformativoView().show(stage)),
                separadorMenu("Conta"),
                itemMenu("👤", "Minha conta",              e -> {}),
                itemMenu("🚪", "Sair",                     e -> new LoginView().show(stage))
        );

        ScrollPane scroll = new ScrollPane(itens);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:white;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox root = new VBox(header, scroll);
        root.setStyle("-fx-background-color:white;");

        stage.setScene(new Scene(root, 420, 620));
        stage.show();
    }

    private Button itemMenu(String icone, String texto, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(icone + "  " + texto);
        btn.setStyle("-fx-background-color:transparent;"
                + "-fx-text-fill:#212121;"
                + "-fx-font-size:14px;"
                + "-fx-alignment:CENTER_LEFT;"
                + "-fx-cursor:hand;"
                + "-fx-padding:10 12;"
                + "-fx-background-radius:8;");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(acao);
        btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle()
                .replace("-fx-background-color:transparent;",
                         "-fx-background-color:" + EstiloApp.VERDE_BG + ";")));
        btn.setOnMouseExited(e -> btn.setStyle(btn.getStyle()
                .replace("-fx-background-color:" + EstiloApp.VERDE_BG + ";",
                         "-fx-background-color:transparent;")));
        return btn;
    }

    private Label separadorMenu(String secao) {
        Label l = new Label(secao.toUpperCase());
        l.setStyle("-fx-font-size:10px; -fx-text-fill:" + EstiloApp.CINZA_TEXTO + ";"
                + "-fx-font-weight:bold; -fx-padding:12 4 2 4;");
        return l;
    }
}
