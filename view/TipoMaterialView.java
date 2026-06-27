package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TipoMaterialView {

    public void show(Stage stage) {
        stage.setTitle("EcoColeta — Tipos de Material");

        HBox topBar = HomeView.criarTopBar(stage, "Tipos de Material");

        // ── Botão adicionar ────────────────────────────────────────
        Button btnAdicionar = new Button("➕  Novo tipo de material");
        btnAdicionar.setStyle(EstiloApp.botaoPrimario());
        btnAdicionar.setOnAction(e -> mostrarFormulario(stage, null));

        HBox headerAcao = new HBox(btnAdicionar);
        headerAcao.setPadding(new Insets(12, 16, 4, 16));
        headerAcao.setAlignment(Pos.CENTER_RIGHT);

        // ── Lista de materiais ────────────────────────────────────
        String[][] materiais = {
            {"♻", "Papel",        "Papelão, jornais, revistas, folhas",  "#1565C0"},
            {"🟢", "Plástico",    "Garrafas PET, embalagens, sacolas",   "#2E7D32"},
            {"⚙", "Metal",        "Latinhas, ferragens, fios",           "#4E342E"},
            {"🔵", "Vidro",       "Garrafas, potes, frascos",            "#00838F"},
            {"🌱", "Orgânico",    "Restos de alimentos, folhas secas",   "#558B2F"},
            {"💻", "Eletrônico",  "Pilhas, celulares, computadores",     "#6A1B9A"},
        };

        VBox lista = new VBox(8);
        lista.setPadding(new Insets(8, 16, 16, 16));

        for (String[] mat : materiais) {
            lista.getChildren().add(criarCardMaterial(stage, mat));
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox root = new VBox(topBar, headerAcao, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        stage.setScene(new Scene(root, 420, 620));
        stage.show();
    }

    private HBox criarCardMaterial(Stage stage, String[] mat) {
        // Ícone colorido
        Label icone = new Label(mat[0]);
        icone.setStyle("-fx-font-size:22px;"
                + "-fx-background-color:" + mat[3] + "22;"
                + "-fx-background-radius:8;"
                + "-fx-padding:8;"
                + "-fx-min-width:44; -fx-min-height:44;"
                + "-fx-alignment:center;");

        // Textos
        Label nome = new Label(mat[1]);
        nome.setStyle("-fx-font-weight:bold; -fx-font-size:14px;");
        Label desc = new Label(mat[2]);
        desc.setStyle(EstiloApp.labelSecao() + "-fx-font-size:12px;");
        desc.setWrapText(true);

        VBox info = new VBox(2, nome, desc);
        HBox.setHgrow(info, Priority.ALWAYS);

        // Ações
        Button btnEditar  = new Button("✏");
        btnEditar.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";"
                + "-fx-font-size:13px; -fx-cursor:hand; -fx-background-radius:6; -fx-padding:4 8;");
        btnEditar.setOnAction(e -> mostrarFormulario(stage, mat));

        Button btnRemover = new Button("🗑");
        btnRemover.setStyle("-fx-background-color:#FFEBEE;"
                + "-fx-font-size:13px; -fx-cursor:hand; -fx-background-radius:6; -fx-padding:4 8;");
        btnRemover.setOnAction(e -> confirmarRemocao(stage, mat[1]));

        VBox acoes = new VBox(4, btnEditar, btnRemover);
        acoes.setAlignment(Pos.CENTER);

        HBox card = new HBox(10, icone, info, acoes);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(EstiloApp.card());
        return card;
    }

    private void mostrarFormulario(Stage stage, String[] mat) {
        boolean editando = mat != null;
        Stage dialog = new Stage();
        dialog.setTitle(editando ? "Editar Material" : "Novo Material");

        Label lblTitulo = new Label(editando ? "Editar: " + mat[1] : "Novo Tipo de Material");
        lblTitulo.setStyle(EstiloApp.labelTitulo());

        TextField txtNome = new TextField(editando ? mat[1] : "");
        txtNome.setPromptText("Nome do material");
        txtNome.setStyle(EstiloApp.campotexto());

        TextArea txtDesc = new TextArea(editando ? mat[2] : "");
        txtDesc.setPromptText("Descrição e exemplos...");
        txtDesc.setPrefRowCount(3);
        txtDesc.setStyle(EstiloApp.campotexto());

        Button btnSalvar = new Button(editando ? "Salvar alterações" : "Adicionar");
        btnSalvar.setStyle(EstiloApp.botaoPrimario());
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Informe o nome.", ButtonType.OK);
                a.setHeaderText(null); a.showAndWait();
                return;
            }
            dialog.close();
            show(stage);
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(EstiloApp.botaoSecundario());
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setOnAction(e -> dialog.close());

        VBox form = new VBox(12,
                lblTitulo,
                new Label("Nome"), txtNome,
                new Label("Descrição"), txtDesc,
                btnSalvar, btnCancelar
        );
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        dialog.setScene(new Scene(form, 360, 320));
        dialog.show();
    }

    private void confirmarRemocao(Stage stage, String nome) {
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                "Remover o material \"" + nome + "\"?",
                ButtonType.YES, ButtonType.NO);
        conf.setHeaderText(null);
        conf.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) show(stage);
        });
    }
}
