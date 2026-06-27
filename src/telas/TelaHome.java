package telas;

import ecocoleta.dao.TipoMaterialDAO;
import ecocoleta.model.PontoColeta;
import ecocoleta.model.TipoMaterial;
import ecocoleta.service.PontoColetaService;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelaHome extends JFrame {

    private final Color verdePrincipal = new Color(46, 139, 70);
    private final Color cinzaClaro = new Color(245, 245, 245);
    private final Color cinzaTexto = new Color(140, 140, 140);

    private final PontoColetaService pontoService = new PontoColetaService();
    private final TipoMaterialDAO materialDAO = new TipoMaterialDAO();

    private JPanel conteudo, mapa, listaCards;
    private static final int ALTURA_BASE = 280;
    private static final int ALTURA_CARD = 165;

    private final Set<Integer> idsMateriaisSelecionados = new LinkedHashSet<>();

    public TelaHome() {
        setTitle("EcoColeta - Home");
        setSize(360, 690);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        conteudo = new JPanel(null);
        conteudo.setBackground(Color.WHITE);

        JPanel header = new JPanel(null);
        header.setBackground(verdePrincipal);
        header.setBounds(0, 0, 360, 60);
        conteudo.add(header);

        JLabel lblMenu = new JLabel("☰");
        lblMenu.setFont(new Font("Arial", Font.PLAIN, 20));
        lblMenu.setBounds(15, 15, 30, 30);
        header.add(lblMenu);

        JLabel lblTitulo = new JLabel("EcoColeta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(0, 15, 360, 30);
        header.add(lblTitulo);

        JLabel lblAvatar = new JLabel();
        lblAvatar.setOpaque(true);
        lblAvatar.setBackground(new Color(200, 200, 200));
        lblAvatar.setBounds(310, 10, 40, 40);
        lblAvatar.setBorder(criarBordaArredondada(Color.WHITE, 40));
        header.add(lblAvatar);

        JLabel lblInstrucao = new JLabel("selecione o material que deseja descartar");
        lblInstrucao.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInstrucao.setBounds(20, 75, 320, 20);
        conteudo.add(lblInstrucao);

        JButton btnMateriais = new JButton("Materiais  ▾");
        btnMateriais.setHorizontalAlignment(SwingConstants.LEFT);
        btnMateriais.setFont(new Font("Arial", Font.BOLD, 14));
        btnMateriais.setBackground(verdePrincipal);
        btnMateriais.setForeground(Color.WHITE);
        btnMateriais.setFocusPainted(false);
        btnMateriais.setBorder(BorderFactory.createCompoundBorder(
                criarBordaArredondada(verdePrincipal, 10),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)));
        btnMateriais.setBounds(20, 105, 320, 42);
        conteudo.add(btnMateriais);

        JPopupMenu popupMateriais = criarPopupMateriais();
        btnMateriais.addActionListener(e -> popupMateriais.show(btnMateriais, 0, btnMateriais.getHeight()));

        mapa = new JPanel(null);
        mapa.setBackground(new Color(225, 230, 226));
        mapa.setBounds(0, 160, 360, 120);
        conteudo.add(mapa);

        listaCards = new JPanel();
        listaCards.setLayout(new BoxLayout(listaCards, BoxLayout.Y_AXIS));
        listaCards.setBackground(Color.WHITE);
        listaCards.setBounds(0, ALTURA_BASE, 360, 0);
        conteudo.add(listaCards);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        atualizarConteudo();

        lblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new TelaMenu().setVisible(true);
                dispose();
            }
        });
    }

    private JPopupMenu criarPopupMateriais() {
        JPopupMenu popup = new JPopupMenu();
        List<TipoMaterial> todos = materialDAO.listarTodos();

        if (todos.isEmpty()) {
            popup.add(new JMenuItem("Nenhum material cadastrado"));
            return popup;
        }

        for (TipoMaterial material : todos) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(material.getNome());
            item.addActionListener(e -> {
                if (item.isSelected()) idsMateriaisSelecionados.add(material.getId());
                else idsMateriaisSelecionados.remove(material.getId());
                atualizarConteudo();
            });
            popup.add(item);
        }
        return popup;
    }

    // Busca no banco — usa filtrarPorMaterial se algo estiver selecionado, senão listarTodos
    private List<PontoColeta> buscarPontosVisiveis() {
        if (idsMateriaisSelecionados.isEmpty()) {
            return pontoService.listarTodos();
        }

        // Combina resultados de cada material selecionado, sem duplicar pontos
        Map<Integer, PontoColeta> combinados = new LinkedHashMap<>();
        for (int idMaterial : idsMateriaisSelecionados) {
            for (PontoColeta p : pontoService.filtrarPorMaterial(idMaterial)) {
                combinados.put(p.getId(), p);
            }
        }
        return new ArrayList<>(combinados.values());
    }

    private void atualizarConteudo() {
        List<PontoColeta> visiveis = buscarPontosVisiveis();

        mapa.removeAll();
        for (PontoColeta ponto : visiveis) {
            Point pos = converterParaPixel(ponto.getLatitude(), ponto.getLongitude());
            mapa.add(criarPino(String.valueOf(ponto.getId()), pos.x, pos.y));
        }
        mapa.revalidate();
        mapa.repaint();

        listaCards.removeAll();
        if (visiveis.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhum ponto de coleta encontrado.");
            lblVazio.setForeground(cinzaTexto);
            lblVazio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            listaCards.add(lblVazio);
        } else {
            for (PontoColeta ponto : visiveis) {
                listaCards.add(criarCardPonto(ponto));
                listaCards.add(criarSeparador());
            }
        }

        int alturaLista = visiveis.isEmpty() ? 60 : visiveis.size() * ALTURA_CARD;
        listaCards.setBounds(0, ALTURA_BASE, 360, alturaLista);
        conteudo.setPreferredSize(new Dimension(360, ALTURA_BASE + alturaLista + 20));
        conteudo.revalidate();
        conteudo.repaint();
    }

    // Conversão simples lat/long -> pixel. Ajuste os limites pra sua região real.
    private Point converterParaPixel(double latitude, double longitude) {
        double latMin = -27.5, latMax = -27.0;
        double lonMin = -50.9, lonMax = -50.8;
        int x = (int) ((longitude - lonMin) / (lonMax - lonMin) * 320) + 10;
        int y = (int) ((latMax - latitude) / (latMax - latMin) * 100) + 10;
        return new Point(Math.max(0, Math.min(x, 320)), Math.max(0, Math.min(y, 90)));
    }

    private JButton criarPino(String numero, int x, int y) {
        JButton pino = new JButton(numero);
        pino.setFont(new Font("Arial", Font.BOLD, 12));
        pino.setBackground(Color.WHITE);
        pino.setForeground(new Color(60, 60, 60));
        pino.setFocusPainted(false);
        pino.setBorder(criarBordaArredondada(new Color(200, 200, 200), 30));
        pino.setBounds(x, y, 32, 32);
        return pino;
    }

    private JPanel criarCardPonto(PontoColeta ponto) {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(360, ALTURA_CARD));
        card.setMaximumSize(new Dimension(360, ALTURA_CARD));

        JLabel lblNumero = new JLabel(String.valueOf(ponto.getId()), SwingConstants.CENTER);
        lblNumero.setOpaque(true);
        lblNumero.setBackground(verdePrincipal);
        lblNumero.setForeground(Color.WHITE);
        lblNumero.setFont(new Font("Arial", Font.BOLD, 28));
        lblNumero.setBounds(15, 10, 70, 130);
        card.add(lblNumero);

        JLabel lblDescricaoTitulo = new JLabel("DESCRIÇÃO");
        lblDescricaoTitulo.setFont(new Font("Arial", Font.BOLD, 11));
        lblDescricaoTitulo.setBounds(100, 10, 150, 15);
        card.add(lblDescricaoTitulo);

        JLabel lblNomeLocal = new JLabel(ponto.getNome().toUpperCase());
        lblNomeLocal.setFont(new Font("Arial", Font.PLAIN, 10));
        lblNomeLocal.setForeground(cinzaTexto);
        lblNomeLocal.setBounds(100, 27, 150, 15);
        card.add(lblNomeLocal);

        JLabel lblEndereco = new JLabel(ponto.getEndereco());
        lblEndereco.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEndereco.setBounds(100, 44, 150, 15);
        card.add(lblEndereco);

        JLabel lblDescEstab = new JLabel("<html>" + (ponto.getDescricao() != null ? ponto.getDescricao() : "") + "</html>");
        lblDescEstab.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescEstab.setBounds(100, 64, 150, 45);
        card.add(lblDescEstab);

        JLabel lblItensTitulo = new JLabel("ITENS", SwingConstants.CENTER);
        lblItensTitulo.setFont(new Font("Arial", Font.BOLD, 11));
        lblItensTitulo.setBounds(250, 10, 90, 15);
        card.add(lblItensTitulo);

        List<TipoMaterial> itensDoPonto = ponto.getMateriais();
        JPanel gradeItens = new JPanel(new GridLayout(0, 2, 5, 5));
        gradeItens.setBackground(cinzaClaro);
        int linhas = Math.max(1, (int) Math.ceil(itensDoPonto.size() / 2.0));
        gradeItens.setBounds(245, 30, 100, linhas * 45);
        for (TipoMaterial item : itensDoPonto) {
            gradeItens.add(criarItem(item.getNome()));
        }
        card.add(gradeItens);

        return card;
    }

    private JPanel criarItem(String nome) {
        JPanel item = new JPanel();
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setBackground(cinzaClaro);

        JLabel bola = new JLabel(" ");
        bola.setOpaque(true);
        bola.setBackground(new Color(210, 210, 210));
        bola.setAlignmentX(Component.CENTER_ALIGNMENT);
        bola.setPreferredSize(new Dimension(18, 18));
        bola.setMaximumSize(new Dimension(18, 18));
        bola.setBorder(criarBordaArredondada(new Color(210, 210, 210), 18));

        JLabel texto = new JLabel(nome, SwingConstants.CENTER);
        texto.setFont(new Font("Arial", Font.PLAIN, 9));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);

        item.add(bola);
        item.add(Box.createVerticalStrut(2));
        item.add(texto);
        return item;
    }

    private JSeparator criarSeparador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        sep.setMaximumSize(new Dimension(360, 1));
        return sep;
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
                return new Insets(4, 4, 4, 4);
            }
        };
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaHome().setVisible(true));
    }
}