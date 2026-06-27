package telas;

import ecocoleta.util.SessaoUsuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JFrame {

    private final Color VERDE      = new Color(56, 142, 60);
    private final Color VERDE_ESC  = new Color(40, 100, 45);
    private final Color VERMELHO   = new Color(198, 40, 40);
    private final Color CINZA_BG   = new Color(245, 245, 245);

    public TelaMenu() {
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Menu");
        setSize(360, 690);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
    }

    private void initComponents() {

        // ── Header verde ───────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 360, 90);
        header.setBackground(VERDE);
        add(header);

        JLabel lblLogo = new JLabel("EcoColeta");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(20, 15, 200, 30);
        header.add(lblLogo);

        JLabel lblSub = new JLabel("O que deseja fazer?");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(new Color(200, 240, 200));
        lblSub.setBounds(20, 45, 250, 20);
        header.add(lblSub);

        JButton btnFechar = new JButton("✕");
        btnFechar.setBounds(315, 15, 32, 32);
        btnFechar.setBackground(VERDE_ESC);
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorder(BorderFactory.createEmptyBorder());
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.addActionListener(e -> {
            new TelaHome().setVisible(true);
            dispose();
        });
        header.add(btnFechar);
        // ── Seção: Navegação (Adicionado) ──────────────────────────
        add(criarSecao("NAVEGAÇÃO", 605));

        JButton btnIrHome = criarBotao(
                "🏠  Voltar para o Início",
                "Ir para o mapa e pontos de coleta",
                VERDE, 625);
        btnIrHome.addActionListener(e -> {
            new TelaHome().setVisible(true); // Abre a TelaHome
            dispose();                       // Fecha o Menu atual
        });
        add(btnIrHome);

        // ── Seção: Denúncias ───────────────────────────────────────
        add(criarSecao("DENÚNCIAS", 105));

        JButton btnAddDenuncia = criarBotao(
                "📋  Adicionar denúncia",
                "Registre um problema ambiental",
                VERDE, 125);
        btnAddDenuncia.addActionListener(e -> {
            new TelaAdicionarDenuncia().setVisible(true);
            dispose();
        });
        add(btnAddDenuncia);

        JButton btnVerDenuncia = criarBotao(
                "🔍  Ver denúncias",
                "Acompanhe denúncias registradas",
                VERDE, 195);
        btnVerDenuncia.addActionListener(e -> {
            new TelaVerDenuncias().setVisible(true);
            dispose();
        });
        add(btnVerDenuncia);

        // ── Seção: Pontos de Coleta ────────────────────────────────
        add(criarSecao("PONTOS DE COLETA", 275));

        JButton btnAddColeta = criarBotao(
                "📍  Adicionar ponto de coleta",
                "Cadastre um novo ponto de coleta",
                VERDE, 295);
        btnAddColeta.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Tela em desenvolvimento.", "Em breve",
                        JOptionPane.INFORMATION_MESSAGE));
        add(btnAddColeta);

    // Substitua o bloco antigo do btnRemColeta por este:
        JButton btnRemColeta = criarBotao(
                "🗑  Remover ponto de coleta",
                "Exclua um ponto de coleta existente",
                new Color(100, 100, 100), 365);
        btnRemColeta.addActionListener(e -> {
            new TelaRemoverPonto().setVisible(true); // Abre a nova tela
            dispose();                                     // Fecha o menu atual
        });
        add(btnRemColeta);
        btnRemColeta.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Tela em desenvolvimento.", "Em breve",
                        JOptionPane.INFORMATION_MESSAGE));
        add(btnRemColeta);
        

        // ── Seção: Conta ───────────────────────────────────────────
        add(criarSecao("CONTA", 445));

        JButton btnSair = criarBotao(
                "🚪  Sair da conta",
                "Encerrar sessão atual",
                new Color(100, 100, 100), 465);
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja sair da conta?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SessaoUsuario.logout();
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
        add(btnSair);

        JButton btnRemConta = criarBotao(
                "⚠  Remover conta",
                "Excluir permanentemente sua conta",
                VERMELHO, 535);
        btnRemConta.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza? Esta ação é irreversível.",
                    "Remover conta", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this,
                        "Funcionalidade em desenvolvimento.");
            }
        });
        add(btnRemConta);
    }

    // ── Label de seção ─────────────────────────────────────────────
    private JLabel criarSecao(String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 10));
        lbl.setForeground(new Color(150, 150, 150));
        lbl.setBounds(25, y, 300, 16);
        return lbl;
    }

    // ── Botão com subtítulo ────────────────────────────────────────
    private JButton criarBotao(String titulo, String subtitulo,
                                Color corBorda, int y) {

        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setLayout(null);
        btn.setBounds(20, y, 315, 62);
        btn.setBackground(CINZA_BG);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corBorda, 2),
                BorderFactory.createEmptyBorder()));
        btn.setContentAreaFilled(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitulo.setForeground(corBorda.equals(VERMELHO) ? VERMELHO : new Color(40, 40, 40));
        lblTitulo.setBounds(14, 10, 280, 20);
        btn.add(lblTitulo);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 11));
        lblSub.setForeground(Color.GRAY);
        lblSub.setBounds(14, 32, 280, 16);
        btn.add(lblSub);

        return btn;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaMenu().setVisible(true));
    }
}