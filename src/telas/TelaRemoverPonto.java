package telas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TelaRemoverPonto extends JFrame {

    private final Color VERDE     = new Color(56, 142, 60);
    private final Color VERDE_ESC = new Color(40, 100, 45);
    private final Color VERMELHO  = new Color(198, 40, 40);
    private final Color CINZA_BG  = new Color(245, 245, 245);

    private JPanel painelLista;

    public TelaRemoverPonto() {
        configurarJanela();
        initComponents();
        carregarPontos();
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Remover Ponto");
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
        header.setBounds(0, 0, 360, 55);
        header.setBackground(VERDE);
        add(header);

        JButton btnMenu = new JButton("☰");
        btnMenu.setBounds(10, 12, 36, 30);
        btnMenu.setBackground(VERDE_ESC);
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusPainted(false);
        btnMenu.setBorder(BorderFactory.createEmptyBorder());
        btnMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMenu.addActionListener(e -> { new TelaMenu().setVisible(true); dispose(); });
        header.add(btnMenu);

        JLabel lblTitulo = new JLabel("Remover Ponto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 12, 360, 30);
        header.add(lblTitulo);

        // ── Aviso ──────────────────────────────────────────────────
        JPanel painelAviso = new JPanel(null);
        painelAviso.setBounds(10, 63, 338, 32);
        painelAviso.setBackground(new Color(255, 243, 224));
        painelAviso.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 100)));

        JLabel lblAviso = new JLabel("⚠  Selecione o ponto que deseja remover.");
        lblAviso.setFont(new Font("Arial", Font.PLAIN, 11));
        lblAviso.setForeground(new Color(150, 80, 0));
        lblAviso.setBounds(8, 7, 320, 18);
        painelAviso.add(lblAviso);
        add(painelAviso);

        // ── Lista rolável ──────────────────────────────────────────
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setBackground(Color.WHITE);
        painelLista.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(painelLista);
        scroll.setBounds(0, 103, 360, 480);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);

        // ── Botão Excluir Conta ────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setBounds(10, 592, 338, 1);
        sep.setForeground(new Color(220, 220, 220));
        add(sep);

        JLabel lblContaAviso = new JLabel(
                "Zona de perigo — esta ação é irreversível.",
                SwingConstants.CENTER);
        lblContaAviso.setFont(new Font("Arial", Font.PLAIN, 10));
        lblContaAviso.setForeground(Color.GRAY);
        lblContaAviso.setBounds(0, 598, 360, 16);
        add(lblContaAviso);

        JButton btnExcluirConta = new JButton("🗑  Excluir minha conta");
        btnExcluirConta.setBounds(20, 618, 318, 42);
        btnExcluirConta.setBackground(VERMELHO);
        btnExcluirConta.setForeground(Color.WHITE);
        btnExcluirConta.setFont(new Font("Arial", Font.BOLD, 13));
        btnExcluirConta.setFocusPainted(false);
        btnExcluirConta.setBorder(BorderFactory.createEmptyBorder());
        btnExcluirConta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExcluirConta.addActionListener(e -> confirmarExclusaoConta());
        add(btnExcluirConta);
    }
    

    private void carregarPontos() {
        painelLista.removeAll();

        // Substitua por PontoColetaDAO.listarTodos() quando disponível
        List<String[]> pontos = List.of(
            new String[]{"Ecoponto Central",     "Av. Brasil, 100 - São Paulo"},
            new String[]{"Coleta Bairro Verde",  "Rua das Flores, 55 - São Paulo"},
            new String[]{"Ponto Reciclagem Sul", "Rua 7 de Setembro - Belo Horizonte"},
            new String[]{"Centro de Triagem",    "Estrada Municipal, km 2 - Campinas"},
            new String[]{"Ecoponto Norte",       "Av. Paulista, 900 - São Paulo"}
        );

        if (pontos.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhum ponto cadastrado.",
                    SwingConstants.CENTER);
            lblVazio.setForeground(Color.GRAY);
            lblVazio.setFont(new Font("Arial", Font.ITALIC, 13));
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelLista.add(Box.createVerticalStrut(40));
            painelLista.add(lblVazio);
        } else {
            int num = 1;
            for (String[] p : pontos) {
                painelLista.add(criarCard(num++, p[0], p[1]));
                painelLista.add(Box.createVerticalStrut(8));
            }
        }

        painelLista.revalidate();
        painelLista.repaint();
    }

    private JPanel criarCard(int numero, String nome, String endereco) {

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(336, 80));
        card.setMaximumSize(new Dimension(336, 80));
        card.setBackground(CINZA_BG);
        card.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

        // Número verde
        JLabel lblNum = new JLabel(String.valueOf(numero), SwingConstants.CENTER);
        lblNum.setOpaque(true);
        lblNum.setBackground(VERDE);
        lblNum.setForeground(Color.WHITE);
        lblNum.setFont(new Font("Arial", Font.BOLD, 18));
        lblNum.setBounds(0, 0, 52, 80);
        card.add(lblNum);

        // Nome
        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("Arial", Font.BOLD, 13));
        lblNome.setForeground(new Color(30, 30, 30));
        lblNome.setBounds(62, 12, 190, 20);
        card.add(lblNome);

        // Endereço
        JLabel lblEnd = new JLabel("📍 " + endereco);
        lblEnd.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEnd.setForeground(Color.GRAY);
        lblEnd.setBounds(62, 34, 270, 16);
        card.add(lblEnd);

        // Botão Remover
        JButton btnRem = new JButton("Remover");
        btnRem.setBounds(62, 54, 85, 20);
        btnRem.setBackground(VERMELHO);
        btnRem.setForeground(Color.WHITE);
        btnRem.setFocusPainted(false);
        btnRem.setFont(new Font("Arial", Font.BOLD, 11));
        btnRem.setBorder(BorderFactory.createEmptyBorder());
        btnRem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRem.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this,
                    "Remover \"" + nome + "\"?\nEsta ação é irreversível.",
                    "Confirmar remoção",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                card.setVisible(false);
                JOptionPane.showMessageDialog(this,
                        "\"" + nome + "\" removido com sucesso.");
            }
        });
        card.add(btnRem);

        return card;
    }

    private void confirmarExclusaoConta() {
        int resp1 = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir sua conta?\n"
                + "Todos os seus dados serão removidos permanentemente.",
                "Excluir conta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (resp1 != JOptionPane.YES_OPTION) return;

        String confirmacao = JOptionPane.showInputDialog(this,
                "Digite CONFIRMAR para prosseguir:",
                "Confirmação final",
                JOptionPane.WARNING_MESSAGE);

        if ("CONFIRMAR".equals(confirmacao)) {
            // UsuarioDAO.desativar(SessaoUsuario.getUsuarioLogado().getId())
            JOptionPane.showMessageDialog(this,
                    "Conta excluída com sucesso.",
                    "Conta removida",
                    JOptionPane.INFORMATION_MESSAGE);
            new TelaLogin().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Confirmação incorreta. Conta não foi excluída.",
                    "Cancelado",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaRemoverPonto().setVisible(true));
    }
}