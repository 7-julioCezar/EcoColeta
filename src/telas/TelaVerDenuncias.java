package telas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Import adicionado para compatibilidade com Java 8

public class TelaVerDenuncias extends JFrame {

    private final Color VERDE     = new Color(56, 142, 60);
    private final Color CINZA_BG  = new Color(245, 245, 245);

    private JPanel painelLista;
    private JScrollPane scroll;

    public TelaVerDenuncias() {
        configurarJanela();
        initComponents();
        carregarDenuncias("Todas");
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Denúncias");
        setSize(360, 690);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
    }

    private void initComponents() {

        // ── Header ─────────────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 360, 55);
        header.setBackground(VERDE);
        add(header);

        JButton btnMenu = new JButton("☰");
        btnMenu.setBounds(10, 12, 36, 30);
        btnMenu.setBackground(new Color(40, 100, 45));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusPainted(false);
        btnMenu.setBorder(BorderFactory.createEmptyBorder());
        btnMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMenu.addActionListener(e -> { new TelaMenu().setVisible(true); dispose(); });
        header.add(btnMenu);

        JLabel lblTitulo = new JLabel("Denúncias", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 12, 360, 30);
        header.add(lblTitulo);

        JButton btnNova = new JButton("+ Nova");
        btnNova.setBounds(285, 12, 65, 30);
        btnNova.setBackground(new Color(40, 100, 45));
        btnNova.setForeground(Color.WHITE);
        btnNova.setFocusPainted(false);
        btnNova.setBorder(BorderFactory.createEmptyBorder());
        btnNova.setFont(new Font("Arial", Font.BOLD, 11));
        btnNova.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNova.addActionListener(e -> { new TelaAdicionarDenuncia().setVisible(true); dispose(); });
        header.add(btnNova);

        // ── Filtro ─────────────────────────────────────────────────
        JLabel lblFiltro = new JLabel("Filtrar:");
        lblFiltro.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFiltro.setBounds(15, 65, 45, 26);
        add(lblFiltro);

        String[] opcoes = {"Todas", "Pendente", "Em análise", "Resolvida"};
        JComboBox<String> cbFiltro = new JComboBox<>(opcoes);
        cbFiltro.setBounds(62, 65, 140, 26);
        cbFiltro.setBackground(Color.WHITE);
        cbFiltro.setFont(new Font("Arial", Font.PLAIN, 12));
        add(cbFiltro);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(210, 65, 80, 26);
        btnBuscar.setBackground(VERDE);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder());
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 11));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e ->
                carregarDenuncias((String) cbFiltro.getSelectedItem()));
        add(btnBuscar);

        // ── Legenda ────────────────────────────────────────────────
        add(criarBadge("Pendente",   new Color(158, 158, 158), 15,  98));
        add(criarBadge("Em análise", new Color(255, 152,   0), 98,  98));
        add(criarBadge("Resolvida",  VERDE,                    200, 98));

        // ── Lista ──────────────────────────────────────────────────
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setBackground(Color.WHITE);

        scroll = new JScrollPane(painelLista);
        scroll.setBounds(8, 128, 340, 548);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        add(scroll);
    }

    private void carregarDenuncias(String filtro) {
        painelLista.removeAll();

        // Alterado de .toList() para .collect(Collectors.toList()) devido ao Java 8
        List<String[]> lista = getDenunciasExemplo().stream()
                .filter(d -> filtro.equals("Todas") || d[3].equals(filtro))
                .collect(Collectors.toList());

        if (lista.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhuma denúncia encontrada.",
                    SwingConstants.CENTER);
            lblVazio.setForeground(Color.GRAY);
            lblVazio.setFont(new Font("Arial", Font.ITALIC, 13));
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelLista.add(Box.createVerticalStrut(40));
            painelLista.add(lblVazio);
        } else {
            for (String[] d : lista) {
                painelLista.add(criarCard(d[0], d[1], d[2], d[3]));
                painelLista.add(Box.createVerticalStrut(8));
            }
        }

        painelLista.revalidate();
        painelLista.repaint();
        scroll.getVerticalScrollBar().setValue(0);
    }

    private JPanel criarCard(String titulo, String local, String data, String status) {

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(325, 110));
        card.setMaximumSize(new Dimension(325, 110));
        card.setBackground(CINZA_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                new EmptyBorder(8, 10, 8, 10)));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Arial", Font.BOLD, 13));
        lblTit.setBounds(6, 6, 215, 20);
        card.add(lblTit);

        JLabel badge = criarBadge(status, corStatus(status), 228, 6);
        card.add(badge);

        JLabel lblLocal = new JLabel("📍 " + local);
        lblLocal.setFont(new Font("Arial", Font.PLAIN, 11));
        lblLocal.setForeground(Color.GRAY);
        lblLocal.setBounds(6, 30, 300, 16);
        card.add(lblLocal);

        JLabel lblData = new JLabel("🗓 " + data);
        lblData.setFont(new Font("Arial", Font.PLAIN, 11));
        lblData.setForeground(Color.GRAY);
        lblData.setBounds(6, 48, 200, 16);
        card.add(lblData);

        JButton btnDet = new JButton("Ver detalhes");
        btnDet.setBounds(6, 75, 105, 24);
        btnDet.setBackground(VERDE);
        btnDet.setForeground(Color.WHITE);
        btnDet.setFocusPainted(false);
        btnDet.setFont(new Font("Arial", Font.PLAIN, 11));
        btnDet.setBorder(BorderFactory.createEmptyBorder());
        btnDet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDet.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Título: " + titulo
                        + "\nLocal: " + local
                        + "\nData: " + data
                        + "\nStatus: " + status,
                        "Detalhes", JOptionPane.INFORMATION_MESSAGE));
        card.add(btnDet);

        return card;
    }

    private JLabel criarBadge(String texto, Color cor, int x, int y) {
        JLabel b = new JLabel(texto, SwingConstants.CENTER);
        b.setFont(new Font("Arial", Font.BOLD, 10));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBackground(cor);
        b.setBounds(x, y, 88, 20);
        return b;
    }

    // Método corrigido para switch tradicional compatível com Java 8
    private Color corStatus(String status) {
        switch (status) {
            case "Resolvida":
                return VERDE;
            case "Em análise":
                return new Color(255, 152, 0);
            default:
                return new Color(158, 158, 158);
        }
    }

    private List<String[]> getDenunciasExemplo() {
        List<String[]> lista = new ArrayList<>();
        lista.add(new String[]{"Lixo em terreno baldio",   "Rua das Flores, 123 - SP", "20/06/2026", "Pendente"});
        lista.add(new String[]{"Descarte ilegal de óleo",  "Av. Brasil, 456 - RJ",     "18/06/2026", "Em análise"});
        lista.add(new String[]{"Esgoto a céu aberto",      "Rua 7 de Setembro - MG",   "15/06/2026", "Resolvida"});
        lista.add(new String[]{"Queima de lixo irregular", "Estrada Municipal, km 3",  "10/06/2026", "Pendente"});
        lista.add(new String[]{"Entulho na calçada",       "Rua Ipiranga, 89 - SP",    "05/06/2026", "Em análise"});
        lista.add(new String[]{"Rio contaminado",          "Bairro Verde - SC",        "01/06/2026", "Resolvida"});
        return lista;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaVerDenuncias().setVisible(true));
    }
}