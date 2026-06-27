package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class GuiaInformativoView {

    public void show(Stage stage) {
        stage.setTitle("EcoColeta — Guia Informativo");

        HBox topBar = HomeView.criarTopBar(stage, "Guia Informativo");

        // ── Busca ──────────────────────────────────────────────────
        TextField txtBusca = new TextField();
        txtBusca.setPromptText("🔍  Buscar no guia...");
        txtBusca.setStyle(EstiloApp.campotexto());

        HBox buscaBox = new HBox(txtBusca);
        buscaBox.setPadding(new Insets(12, 16, 8, 16));
        HBox.setHgrow(txtBusca, Priority.ALWAYS);

        // ── Botão novo artigo ──────────────────────────────────────
        Button btnNovo = new Button("➕  Novo artigo");
        btnNovo.setStyle(EstiloApp.botaoPrimario());
        btnNovo.setOnAction(e -> mostrarFormulario(stage, null));

        HBox acaoBar = new HBox(btnNovo);
        acaoBar.setPadding(new Insets(0, 16, 8, 16));
        acaoBar.setAlignment(Pos.CENTER_RIGHT);

        // ── Artigos ────────────────────────────────────────────────
        Object[][] artigos = {
            {"📄", "Como separar o lixo corretamente",
             "Aprenda a classificar papel, plástico, vidro, metal e orgânico.",
             "Reciclagem"},
            {"🌱", "Compostagem doméstica",
             "Transforme resíduos orgânicos em adubo para plantas.",
             "Orgânico"},
            {"💧", "Descarte correto de óleo de cozinha",
             "Nunca jogue óleo na pia! Saiba como descartar com responsabilidade.",
             "Líquidos"},
            {"💻", "Lixo eletrônico: o que fazer?",
             "Pilhas, celulares e eletrodomésticos precisam de descarte especial.",
             "Eletrônicos"},
            {"🏭", "Pontos de coleta na sua cidade",
             "Veja como encontrar os pontos de coleta mais próximos de você.",
             "Localização"},
            {"♻", "Símbolos de reciclagem e o que significam",
             "Entenda os números e símbolos nas embalagens plásticas.",
             "Educação"},
        };

        VBox listaArtigos = new VBox(10);
        listaArtigos.setPadding(new Insets(4, 16, 16, 16));

        for (Object[] art : artigos) {
            listaArtigos.getChildren().add(criarCardArtigo(stage, art));
        }

        ScrollPane scroll = new ScrollPane(listaArtigos);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox root = new VBox(topBar, buscaBox, acaoBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        stage.setScene(new Scene(root, 420, 640));
        stage.show();
    }

    private VBox criarCardArtigo(Stage stage, Object[] art) {
        Label icone = new Label((String) art[0]);
        icone.setStyle("-fx-font-size:28px;");

        Label titulo = new Label((String) art[1]);
        titulo.setStyle("-fx-font-weight:bold; -fx-font-size:13px;");
        titulo.setWrapText(true);

        Label resumo = new Label((String) art[2]);
        resumo.setStyle(EstiloApp.labelSecao() + "-fx-font-size:11px;");
        resumo.setWrapText(true);

        Label tag = new Label((String) art[3]);
        tag.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";"
                + "-fx-text-fill:" + EstiloApp.VERDE_ESCURO + ";"
                + "-fx-font-size:10px; -fx-padding:2 6; -fx-background-radius:10;");

        VBox info = new VBox(4, titulo, resumo, tag);
        HBox.setHgrow(info, Priority.ALWAYS);

        Button btnLer = new Button("Ler ›");
        btnLer.setStyle(EstiloApp.botaoSecundario() + "-fx-font-size:11px; -fx-padding:4 10;");
        btnLer.setOnAction(e -> mostrarArtigo(stage, art));

        HBox conteudo = new HBox(12, icone, info, btnLer);
        conteudo.setAlignment(Pos.CENTER_LEFT);

        Button btnEditar  = new Button("✏ Editar");
        btnEditar.setStyle("-fx-background-color:transparent; -fx-text-fill:" + EstiloApp.VERDE_ESCURO
                + "; -fx-font-size:11px; -fx-cursor:hand; -fx-padding:2 4;");
        btnEditar.setOnAction(e -> mostrarFormulario(stage, art));

        Button btnRemover = new Button("🗑 Remover");
        btnRemover.setStyle("-fx-background-color:transparent; -fx-text-fill:" + EstiloApp.VERMELHO
                + "; -fx-font-size:11px; -fx-cursor:hand; -fx-padding:2 4;");
        btnRemover.setOnAction(e -> confirmarRemocao(stage, (String) art[1]));

        HBox rodape = new HBox(8, btnEditar, btnRemover);

        VBox card = new VBox(8, conteudo, rodape);
        card.setStyle(EstiloApp.card());
        return card;
    }

    private void mostrarArtigo(Stage stage, Object[] art) {
        Stage dialog = new Stage();
        dialog.setTitle((String) art[1]);

        Label icone  = new Label((String) art[0]);
        icone.setStyle("-fx-font-size:36px;");
        Label titulo = new Label((String) art[1]);
        titulo.setStyle(EstiloApp.labelTitulo());
        titulo.setWrapText(true);
        Label tag    = new Label("Categoria: " + art[3]);
        tag.setStyle(EstiloApp.labelSecao());

        TextArea conteudo = new TextArea(
            (String) art[2] + "\n\n"
            + "Este artigo faz parte do Guia Informativo do EcoColeta. "
            + "A reciclagem correta é fundamental para a preservação do meio ambiente "
            + "e para a sustentabilidade das cidades. Compartilhe este conhecimento!\n\n"
            + "Dicas práticas:\n"
            + "• Separe os resíduos por tipo antes do descarte\n"
            + "• Limpe as embalagens antes de reciclar\n"
            + "• Use os pontos de coleta do EcoColeta\n"
            + "• Incentive sua família e vizinhos"
        );
        conteudo.setWrapText(true);
        conteudo.setEditable(false);
        conteudo.setStyle("-fx-background-color:white; -fx-border-color:transparent;");
        VBox.setVgrow(conteudo, Priority.ALWAYS);

        Button btnFechar = new Button("Fechar");
        btnFechar.setStyle(EstiloApp.botaoSecundario());
        btnFechar.setMaxWidth(Double.MAX_VALUE);
        btnFechar.setOnAction(e -> dialog.close());

        VBox layout = new VBox(12, icone, titulo, tag, new Separator(), conteudo, btnFechar);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color:white;");

        dialog.setScene(new Scene(layout, 400, 480));
        dialog.show();
    }

    private void mostrarFormulario(Stage stage, Object[] art) {
        boolean editando = art != null;
        Stage dialog = new Stage();
        dialog.setTitle(editando ? "Editar Artigo" : "Novo Artigo");

        TextField txtTitulo = new TextField(editando ? (String) art[1] : "");
        txtTitulo.setPromptText("Título do artigo");
        txtTitulo.setStyle(EstiloApp.campotexto());

        TextField txtCategoria = new TextField(editando ? (String) art[3] : "");
        txtCategoria.setPromptText("Categoria (ex: Reciclagem)");
        txtCategoria.setStyle(EstiloApp.campotexto());

        TextArea txtConteudo = new TextArea(editando ? (String) art[2] : "");
        txtConteudo.setPromptText("Conteúdo do artigo...");
        txtConteudo.setPrefRowCount(5);
        txtConteudo.setStyle(EstiloApp.campotexto());

        Button btnSalvar = new Button(editando ? "Salvar" : "Publicar");
        btnSalvar.setStyle(EstiloApp.botaoPrimario());
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnSalvar.setOnAction(e -> {
            if (txtTitulo.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Informe o título.", ButtonType.OK);
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

        Label lbl = new Label(editando ? "Editar Artigo" : "Novo Artigo");
        lbl.setStyle(EstiloApp.labelTitulo());

        VBox form = new VBox(10,
                lbl,
                new Label("Título"), txtTitulo,
                new Label("Categoria"), txtCategoria,
                new Label("Conteúdo"), txtConteudo,
                btnSalvar, btnCancelar
        );
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        dialog.setScene(new Scene(new ScrollPane(form), 380, 440));
        dialog.show();
    }

    private void confirmarRemocao(Stage stage, String titulo) {
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                "Remover o artigo \"" + titulo + "\"?",
                ButtonType.YES, ButtonType.NO);
        conf.setHeaderText(null);
        conf.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) show(stage);
        });
    }
}
