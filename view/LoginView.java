package ecocoleta.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class LoginView {

    public void show(Stage stage) {
        stage.setTitle("EcoColeta");

        // ── Logo / Título ──────────────────────────────────────────
        Text logo = new Text("🌿");
        logo.setFont(Font.font(48));

        Text titulo = new Text("EcoColeta");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 26));
        titulo.setFill(Color.web(EstiloApp.VERDE_ESCURO));

        Text subtitulo = new Text("Coleta inteligente para um mundo melhor");
        subtitulo.setFont(Font.font(13));
        subtitulo.setFill(Color.web(EstiloApp.CINZA_TEXTO));

        VBox logoBox = new VBox(4, logo, titulo, subtitulo);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(30, 0, 24, 0));

        // ── Formulário ─────────────────────────────────────────────
        Label lblEmail = new Label("E-mail");
        lblEmail.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("seu@email.com");
        txtEmail.setStyle(EstiloApp.campotexto());
        txtEmail.setMaxWidth(Double.MAX_VALUE);

        Label lblSenha = new Label("Senha");
        lblSenha.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("••••••••");
        txtSenha.setStyle(EstiloApp.campotexto());
        txtSenha.setMaxWidth(Double.MAX_VALUE);

        // ── Botão entrar ───────────────────────────────────────────
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setStyle(EstiloApp.botaoPrimario());
        btnEntrar.setMaxWidth(Double.MAX_VALUE);
        btnEntrar.setOnAction(e -> {
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText();
            if (email.isEmpty() || senha.isEmpty()) {
                mostrarAlerta("Preencha e-mail e senha.");
                return;
            }
            new HomeView().show(stage);
        });

        // ── Separador ──────────────────────────────────────────────
        HBox separador = criarSeparador("ou");

        // ── Botões sociais ─────────────────────────────────────────
        Button btnGoogle = new Button("🔵  Continuar com o Google");
        btnGoogle.setStyle(EstiloApp.botaoSecundario());
        btnGoogle.setMaxWidth(Double.MAX_VALUE);

        Button btnApple = new Button("⚫  Continuar com a Apple");
        btnApple.setStyle(EstiloApp.botaoSecundario());
        btnApple.setMaxWidth(Double.MAX_VALUE);

        // ── Link cadastro ──────────────────────────────────────────
        Hyperlink linkCadastro = new Hyperlink("Criar uma conta");
        linkCadastro.setStyle("-fx-text-fill:" + EstiloApp.VERDE_ESCURO + "; -fx-font-weight:bold;");
        linkCadastro.setOnAction(e -> mostrarCadastro(stage));

        Label lblCadastro = new Label("Não tem conta? ");
        lblCadastro.setStyle(EstiloApp.labelSecao());
        HBox linkBox = new HBox(lblCadastro, linkCadastro);
        linkBox.setAlignment(Pos.CENTER);

        // ── Layout principal ───────────────────────────────────────
        VBox form = new VBox(10,
                lblEmail, txtEmail,
                lblSenha, txtSenha,
                new Region(),
                btnEntrar,
                separador,
                btnGoogle,
                btnApple,
                linkBox
        );
        form.setPadding(new Insets(24));
        form.setStyle(EstiloApp.card());
        VBox.setMargin(btnEntrar, new Insets(6, 0, 0, 0));

        VBox root = new VBox(logoBox, form);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");
        root.setPadding(new Insets(16));
        root.setMaxWidth(400);
        root.setAlignment(Pos.TOP_CENTER);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");

        Scene scene = new Scene(scroll, 420, 620);
        stage.setScene(scene);
        stage.show();
    }

    // ── Tela de cadastro ───────────────────────────────────────────
    private void mostrarCadastro(Stage stage) {
        stage.setTitle("EcoColeta — Criar Conta");

        Text titulo = new Text("🌿 Criar uma conta");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 22));
        titulo.setFill(Color.web(EstiloApp.VERDE_ESCURO));

        TextField txtNome  = campoForm("Nome completo", false);
        TextField txtEmail = campoForm("E-mail", false);
        PasswordField txtSenha    = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setStyle(EstiloApp.campotexto());
        PasswordField txtConfirma = new PasswordField();
        txtConfirma.setPromptText("Confirmar senha");
        txtConfirma.setStyle(EstiloApp.campotexto());

        Button btnCriar = new Button("Criar conta");
        btnCriar.setStyle(EstiloApp.botaoPrimario());
        btnCriar.setMaxWidth(Double.MAX_VALUE);
        btnCriar.setOnAction(e -> {
            if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty()
                    || txtSenha.getText().isEmpty()) {
                mostrarAlerta("Preencha todos os campos.");
                return;
            }
            if (!txtSenha.getText().equals(txtConfirma.getText())) {
                mostrarAlerta("As senhas não coincidem.");
                return;
            }
            mostrarSucesso("Conta criada com sucesso!");
            show(stage);
        });

        Hyperlink linkVoltar = new Hyperlink("← Voltar ao login");
        linkVoltar.setStyle("-fx-text-fill:" + EstiloApp.VERDE_ESCURO + ";");
        linkVoltar.setOnAction(e -> show(stage));

        VBox form = new VBox(12,
                titulo,
                label("Nome"), txtNome,
                label("E-mail"), txtEmail,
                label("Senha"), txtSenha,
                label("Confirmar senha"), txtConfirma,
                btnCriar, linkVoltar
        );
        form.setPadding(new Insets(24));
        form.setStyle(EstiloApp.card());

        VBox root = new VBox(form);
        root.setStyle("-fx-background-color:" + EstiloApp.VERDE_BG + ";");
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(new ScrollPane(root), 420, 580));
    }

    // ── Helpers ────────────────────────────────────────────────────
    private HBox criarSeparador(String texto) {
        Separator s1 = new Separator(); HBox.setHgrow(s1, Priority.ALWAYS);
        Separator s2 = new Separator(); HBox.setHgrow(s2, Priority.ALWAYS);
        Label lbl = new Label("  " + texto + "  ");
        lbl.setStyle(EstiloApp.labelSecao());
        HBox box = new HBox(s1, lbl, s2);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private TextField campoForm(String prompt, boolean senha) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(EstiloApp.campotexto());
        return tf;
    }

    private Label label(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-font-size:13px; -fx-font-weight:bold;");
        return l;
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        a.setHeaderText(null); a.showAndWait();
    }

    private void mostrarSucesso(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null); a.showAndWait();
    }
}
