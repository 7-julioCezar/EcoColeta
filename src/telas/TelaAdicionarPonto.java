package telas;

import ecocoleta.dao.TipoMaterialDAO;
import ecocoleta.model.TipoMaterial;
import ecocoleta.service.PontoColetaService;
import ecocoleta.util.SessaoUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaAdicionarPonto extends JFrame {

    private final PontoColetaService pontoService = new PontoColetaService();
    private final TipoMaterialDAO materialDAO = new TipoMaterialDAO();
    private final List<JCheckBox> checkboxesMateriais = new ArrayList<>();

    private JTextField txtNome, txtRua, txtCidade;
    private JTextArea txtDescricao;

    private final Color VERDE     = new Color(56, 142, 60);
    private final Color CINZA_BG  = new Color(245, 245, 245);
    private final Color CINZA_BD  = new Color(210, 210, 210);

    public TelaAdicionarPonto() {
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("EcoColeta - Adicionar Ponto");
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

        JLabel lblTitulo = new JLabel("Adicionar Ponto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 12, 360, 30);
        header.add(lblTitulo);

        // ── Formulário ─────────────────────────────────────────────
        int y = 70;

        add(criarLabel("Nome do local  *obrigatório", y));
        txtNome = criarTextField();
        txtNome.setBounds(20, y + 22, 315, 36);
        add(txtNome);

        y += 75;
        add(criarLabel("Descrição  *opcional", y));
        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        scrollDesc.setBounds(20, y + 22, 315, 55);
        scrollDesc.setBorder(BorderFactory.createLineBorder(CINZA_BD));
        add(scrollDesc);

        y += 95;
        add(criarLabel("Rua  *obrigatório", y));
        txtRua = criarTextField();
        txtRua.setBounds(20, y + 22, 315, 36);
        add(txtRua);

        y += 75;
        add(criarLabel("Cidade  *obrigatório", y));
        txtCidade = criarTextField();
        txtCidade.setBounds(20, y + 22, 315, 36);
        add(txtCidade);

        // ── Materiais ──────────────────────────────────────────────
        y += 75;
        add(criarLabel("Materiais aceitos  *obrigatório", y));

        JPanel painelMateriais = new JPanel(new GridLayout(0, 2, 6, 6));
        painelMateriais.setBackground(CINZA_BG);
        painelMateriais.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CINZA_BD),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        List<TipoMaterial> todos = materialDAO.listarTodos();
        for (TipoMaterial m : todos) {
            JCheckBox cb = new JCheckBox(m.getNome());
            cb.setBackground(CINZA_BG);
            cb.setFont(new Font("Arial", Font.PLAIN, 12));
            cb.putClientProperty("idMaterial", m.getId());
            checkboxesMateriais.add(cb);
            painelMateriais.add(cb);
        }

        int linhas = Math.max(1, (int) Math.ceil(todos.size() / 2.0));
        int alturaMat = linhas * 30 + 20;
        painelMateriais.setBounds(20, y + 22, 315, alturaMat);
        add(painelMateriais);

        // ── Botão Salvar ───────────────────────────────────────────
        y += 22 + alturaMat + 15;
        JButton btnSalvar = new JButton("Salvar Ponto");
        btnSalvar.setBounds(20, y, 315, 42);
        btnSalvar.setBackground(VERDE);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder());
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(e -> salvarPonto());
        add(btnSalvar);
    }

    private void salvarPonto() {
        if (!SessaoUsuario.estaLogado()) {
            JOptionPane.showMessageDialog(this, "Você precisa estar logado.");
            return;
        }

        String nome    = txtNome.getText().trim();
        String rua     = txtRua.getText().trim();
        String cidade  = txtCidade.getText().trim();
        String descricao = txtDescricao.getText().trim();

        if (nome.isEmpty() || rua.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha nome, rua e cidade.", "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<TipoMaterial> selecionados = new ArrayList<>();
        for (JCheckBox cb : checkboxesMateriais) {
            if (cb.isSelected()) {
                TipoMaterial tm = new TipoMaterial();
                tm.setId((int) cb.getClientProperty("idMaterial"));
                tm.setNome(cb.getText());
                selecionados.add(tm);
            }
        }

        if (selecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione pelo menos um material.", "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String resultado = pontoService.cadastrar(
                nome, rua + ", " + cidade, descricao,
                SessaoUsuario.getUsuarioLogado().getId(), selecionados);

        if (resultado.startsWith("OK")) {
            JOptionPane.showMessageDialog(this, "Ponto salvo com sucesso!");
            new TelaMenu().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel criarLabel(String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setBounds(20, y, 315, 18);
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        return tf;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaAdicionarPonto().setVisible(true));
    }
}