package telas;

import ecocoleta.s.RoundedButton;
import ecocoleta.s.RoundedPasswordField;
import ecocoleta.s.RoundedTextField;
import ecocoleta.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de cadastro de usuário do EcoColeta.
 * Mesmo padrão visual e estrutural da TelaLogin.
 */
public class TelaCadastroUsuario extends JFrame {

    private final Color VERDE = new Color(46, 139, 70);
    private final Color CINZA = new Color(120, 120, 120);

    private RoundedTextField txtNome;
    private RoundedTextField txtEmail;
    private RoundedPasswordField txtSenha;
    private RoundedPasswordField txtConfirmarSenha;
    private JComboBox<String> cbTipo;

    private RoundedButton btnCadastrar;
    private JButton btnVoltarLogin;

    private final UsuarioService usuarioService = new UsuarioService();

    public TelaCadastroUsuario() {
        configurarJanela();
        initComponents();
        initEvents();
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Cadastro");
        setSize(360, 690);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
    }

    private void initComponents() {

        JLabel lblLogo = new JLabel("EcoColeta", SwingConstants.CENTER);
        lblLogo.setBounds(0, 50, 360, 35);
        lblLogo.setForeground(VERDE);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblLogo);

        JLabel lblTitulo = new JLabel("Criar uma conta", SwingConstants.CENTER);
        lblTitulo.setBounds(0, 100, 360, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitulo);

        JLabel lblSubtitulo = new JLabel(
                "<html><center>Insira seus dados para se cadastrar<br>neste aplicativo</center></html>",
                SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 135, 360, 35);
        lblSubtitulo.setForeground(VERDE);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        add(lblSubtitulo);

        txtNome = new RoundedTextField("Nome completo");
        txtNome.setBounds(30, 190, 300, 42);
        add(txtNome);

        txtEmail = new RoundedTextField("email@dominio.com");
        txtEmail.setBounds(30, 240, 300, 42);
        add(txtEmail);

        txtSenha = new RoundedPasswordField();
        txtSenha.setBounds(30, 290, 300, 42);
        add(txtSenha);

        txtConfirmarSenha = new RoundedPasswordField();
        txtConfirmarSenha.setBounds(30, 340, 300, 42);
        add(txtConfirmarSenha);

        JLabel lblTipo = new JLabel("Tipo de usuário");
        lblTipo.setBounds(30, 390, 200, 18);
        lblTipo.setForeground(CINZA);
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 12));
        add(lblTipo);

        cbTipo = new JComboBox<>(new String[]{"CIDADAO", "EMPRESA"});
        cbTipo.setBounds(30, 410, 300, 38);
        cbTipo.setFont(new Font("Arial", Font.PLAIN, 13));
        cbTipo.setBackground(Color.WHITE);
        add(cbTipo);

        btnCadastrar = new RoundedButton("Cadastrar");
        btnCadastrar.setBounds(30, 465, 300, 42);
        add(btnCadastrar);

        btnVoltarLogin = new JButton("Já possui conta? Entrar");
        btnVoltarLogin.setBounds(30, 520, 300, 25);
        btnVoltarLogin.setContentAreaFilled(false);
        btnVoltarLogin.setBorderPainted(false);
        btnVoltarLogin.setForeground(VERDE);
        btnVoltarLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltarLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltarLogin);

        JLabel lblRodape = new JLabel(
                "<html><center>Ao se cadastrar você concorda com os<br>"
                + "Termos de Serviço e Política de Privacidade.</center></html>",
                SwingConstants.CENTER);
        lblRodape.setBounds(20, 600, 320, 35);
        lblRodape.setForeground(CINZA);
        lblRodape.setFont(new Font("Arial", Font.PLAIN, 10));
        add(lblRodape);
    }

    private void initEvents() {

        btnCadastrar.addActionListener(e -> tentarCadastrar());
        txtConfirmarSenha.addActionListener(e -> tentarCadastrar());

        btnVoltarLogin.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private void tentarCadastrar() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarSenha.getPassword());
        String tipo = (String) cbTipo.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe seu nome completo.");
            txtNome.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe seu e-mail.");
            txtEmail.requestFocus();
            return;
        }

        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe uma senha.");
            txtSenha.requestFocus();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.",
                    "Cadastro", JOptionPane.ERROR_MESSAGE);
            txtConfirmarSenha.requestFocus();
            return;
        }

        String resultado = usuarioService.cadastrar(nome, email, senha, tipo);

        if (resultado.startsWith("OK")) {
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!",
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
            new TelaLogin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado.replace("ERRO: ", ""),
                    "Erro no cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroUsuario().setVisible(true));
    }
}