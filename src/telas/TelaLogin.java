package telas;

import ecocoleta.model.Usuario;
import ecocoleta.service.UsuarioService;
import ecocoleta.util.SessaoUsuario;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class TelaLogin extends JFrame {

    private final Color verdePrincipal = new Color(46, 139, 70);
    private final UsuarioService usuarioService = new UsuarioService();

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public TelaLogin() {
        configurarJanela();
        initComponents();
        initEvents();
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Login");
        setSize(360, 690);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
    }

    private void initComponents() {
        JLabel lblLogo = new JLabel("EcoColeta", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 26));
        lblLogo.setForeground(verdePrincipal);
        lblLogo.setBounds(0, 130, 360, 35);
        add(lblLogo);

        JLabel lblEntrar = new JLabel("Entrar na conta", SwingConstants.CENTER);
        lblEntrar.setFont(new Font("Arial", Font.BOLD, 17));
        lblEntrar.setBounds(0, 240, 360, 25);
        add(lblEntrar);

        txtEmail = new JTextField();
        txtEmail.setBounds(30, 290, 300, 42);
        txtEmail.setBorder(criarBordaArredondada(new Color(220, 220, 220), 12));
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(30, 345, 300, 42);
        txtSenha.setBorder(criarBordaArredondada(new Color(220, 220, 220), 12));
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntrar.setBackground(verdePrincipal);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorder(criarBordaArredondada(verdePrincipal, 12));
        btnEntrar.setBounds(30, 400, 300, 42);
        add(btnEntrar);

        JButton btnIrCadastro = new JButton("Não tem conta? Cadastre-se");
        btnIrCadastro.setBorderPainted(false);
        btnIrCadastro.setContentAreaFilled(false);
        btnIrCadastro.setForeground(verdePrincipal);
        btnIrCadastro.setFont(new Font("Arial", Font.BOLD, 12));
        btnIrCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIrCadastro.setBounds(30, 455, 300, 30);
        add(btnIrCadastro);

        btnIrCadastro.addActionListener(e -> {
            new TelaCadastroUsuario().setVisible(true);
            dispose();
        });
    }

    private void initEvents() {
        btnEntrar.addActionListener(e -> tentarLogin());
        txtSenha.addActionListener(e -> tentarLogin());
    }

    private void tentarLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe seu e-mail.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe sua senha.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            txtSenha.requestFocus();
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Informe um e-mail válido (ex: nome@dominio.com).",
                    "E-mail inválido", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        btnEntrar.setEnabled(false);

        try {
            Usuario usuario = usuarioService.login(email, senha);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "E-mail ou senha incorretos.",
                        "Falha no login", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
                txtSenha.requestFocus();
                return;
            }

            SessaoUsuario.login(usuario);
            new TelaHome().setVisible(true);
            dispose();

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro interno ao processar os dados. Tente novamente.",
                    "Erro interno", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            // Verifica se a causa raiz do problema é relacionada ao banco de dados MySQL
            String msg = e.getMessage() != null ? e.getMessage() : "";
            if (e.getCause() instanceof java.sql.SQLTimeoutException || msg.contains("timeout")) {
                JOptionPane.showMessageDialog(this,
                        "Servidor demorou para responder. Tente novamente.",
                        "Tempo esgotado", JOptionPane.ERROR_MESSAGE);
            } else if (e.getCause() instanceof java.sql.SQLException || msg.contains("SQL") || msg.contains("database")) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao conectar ao banco:\n" + e.getMessage(),
                        "Erro de conexão", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro inesperado: " + e.getClass().getSimpleName()
                        + (!msg.isEmpty() ? " - " + msg : ""),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            btnEntrar.setEnabled(true);
        }
    }

    private AbstractBorder criarBordaArredondada(Color cor, int raio) {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cor);
                g2.drawRoundRect(x, y, width - 1, height - 1, raio, raio);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(8, 12, 8, 12);
            }
        };
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}