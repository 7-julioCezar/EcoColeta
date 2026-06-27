package telas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaAdicionarDenuncia extends JFrame {

    private File imagemSelecionada;

    public TelaAdicionarDenuncia() {
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Adicionar Denúncia");
        setSize(360, 690);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
    }

    private void initComponents() {
        Color verde = new Color(56, 142, 60);
        Color cinza = new Color(220, 220, 220);

        // ── Botão Menu (voltar para Home) ──────────────────────────
        JButton btnMenu = new JButton("☰  Menu");
        btnMenu.setBounds(10, 10, 100, 32);
        btnMenu.setBackground(verde);
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusPainted(false);
        btnMenu.setFont(new Font("Arial", Font.BOLD, 13));
        btnMenu.setBorder(BorderFactory.createEmptyBorder());
        btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMenu.addActionListener(e -> {
            new TelaHome().setVisible(true);
            dispose();
        });
        add(btnMenu);

        // ── Título ─────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("Nova Denúncia", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(0, 10, 360, 30);
        add(lblTitulo);

        // ── Descrição ──────────────────────────────────────────────
        JLabel lblDescricao = new JLabel("Descrição  *opcional");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDescricao.setBounds(20, 60, 250, 20);
        add(lblDescricao);

        JTextArea txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBounds(20, 83, 310, 60);
        scrollDescricao.setBorder(BorderFactory.createLineBorder(cinza));
        add(scrollDescricao);

        // ── Local ──────────────────────────────────────────────────
        JLabel lblLocal = new JLabel("Local  *obrigatório");
        lblLocal.setFont(new Font("Arial", Font.PLAIN, 13));
        lblLocal.setBounds(20, 158, 250, 20);
        add(lblLocal);

        JTextField txtCep = new JTextField();
        txtCep.setToolTipText("CEP");
        txtCep.setBounds(20, 181, 310, 32);
        txtCep.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cinza),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        add(txtCep);

        JTextField txtRua = new JTextField();
        txtRua.setToolTipText("Rua");
        txtRua.setBounds(20, 218, 310, 32);
        txtRua.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cinza),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        add(txtRua);

        JTextField txtCidade = new JTextField();
        txtCidade.setToolTipText("Cidade");
        txtCidade.setBounds(20, 255, 310, 32);
        txtCidade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cinza),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        add(txtCidade);

        // ── Imagem ─────────────────────────────────────────────────
        JLabel lblImagem = new JLabel("Imagem  *opcional");
        lblImagem.setFont(new Font("Arial", Font.PLAIN, 13));
        lblImagem.setBounds(20, 302, 250, 20);
        add(lblImagem);

        JLabel lblArquivo = new JLabel("Nenhum arquivo selecionado");
        lblArquivo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblArquivo.setForeground(Color.GRAY);
        lblArquivo.setBounds(20, 363, 310, 18);
        add(lblArquivo);

        JButton btnSelecionarImagem = new JButton("📎  Anexar PNG / JPEG");
        btnSelecionarImagem.setBounds(20, 325, 310, 36);
        btnSelecionarImagem.setBackground(new Color(240, 240, 240));
        btnSelecionarImagem.setFocusPainted(false);
        btnSelecionarImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSelecionarImagem.setFont(new Font("Arial", Font.PLAIN, 13));
        btnSelecionarImagem.setBorder(BorderFactory.createLineBorder(cinza));
        btnSelecionarImagem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecionar imagem");
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Imagens (PNG, JPEG)", "png", "jpg", "jpeg"));
            chooser.setAcceptAllFileFilterUsed(false);

            int resultado = chooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                imagemSelecionada = chooser.getSelectedFile();
                lblArquivo.setText("✔  " + imagemSelecionada.getName());
                lblArquivo.setForeground(new Color(46, 139, 70));
            }
        });
        add(btnSelecionarImagem);

        // ── Anônimo ────────────────────────────────────────────────
        JLabel lblAnonimo = new JLabel("Deseja ser anônimo?  *obrigatório");
        lblAnonimo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblAnonimo.setBounds(20, 395, 280, 20);
        add(lblAnonimo);

        JRadioButton rbSim = new JRadioButton("Sim");
        rbSim.setBounds(20, 418, 70, 25);
        rbSim.setBackground(Color.WHITE);

        JRadioButton rbNao = new JRadioButton("Não");
        rbNao.setBounds(95, 418, 70, 25);
        rbNao.setBackground(Color.WHITE);

        ButtonGroup grupoAnonimo = new ButtonGroup();
        grupoAnonimo.add(rbSim);
        grupoAnonimo.add(rbNao);
        add(rbSim);
        add(rbNao);

        // ── Botão Enviar ───────────────────────────────────────────
        JButton btnEnviar = new JButton("Enviar Denúncia");
        btnEnviar.setBackground(verde);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorder(BorderFactory.createEmptyBorder());
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnviar.setBounds(20, 470, 310, 42);
        btnEnviar.addActionListener(e -> enviarDenuncia(
                txtDescricao, txtCep, txtRua, txtCidade, rbSim, rbNao));
        add(btnEnviar);
    }

    private void enviarDenuncia(JTextArea txtDescricao, JTextField txtCep,
            JTextField txtRua, JTextField txtCidade,
            JRadioButton rbSim, JRadioButton rbNao) {

        if (txtCep.getText().trim().isEmpty()
                || txtRua.getText().trim().isEmpty()
                || txtCidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos de local obrigatórios.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!rbSim.isSelected() && !rbNao.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Informe se deseja ser anônimo.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Denúncia enviada com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        new TelaHome().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaAdicionarDenuncia().setVisible(true));
    }
}