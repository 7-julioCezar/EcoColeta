package telas;

import javax.swing.*;
import java.awt.*;

public class TelaRemoverConta extends JFrame {

    public TelaRemoverConta() {
        setTitle("EcoColeta - Remover Conta");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel cardUsuario = new JPanel(new BorderLayout(10, 0));
        cardUsuario.setBounds(20, 60, 300, 60);
        cardUsuario.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(new JLabel("Gabriela Vieira"));
        textos.add(new JLabel("gvvieira123@gmail.com"));
        cardUsuario.add(textos, BorderLayout.CENTER);

        JLabel lblExcluir = new JLabel("Excluir", SwingConstants.CENTER);
        lblExcluir.setForeground(Color.RED);
        cardUsuario.add(lblExcluir, BorderLayout.EAST);
        add(cardUsuario);

        JButton btnGoogle = new JButton("Continuar com o Google");
        btnGoogle.setBounds(20, 140, 300, 35);
        add(btnGoogle);

        JButton btnApple = new JButton("Continuar com a Apple");
        btnApple.setBounds(20, 185, 300, 35);
        add(btnApple);

        JButton btnRemoverPonto = new JButton("Remover ponto de coleta");
        btnRemoverPonto.setForeground(Color.RED);
        btnRemoverPonto.setBounds(20, 240, 300, 35);
        add(btnRemoverPonto);

        // Confirmação de exclusão (igual ao mockup "remover ponto/conta")
        lblExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int resposta = JOptionPane.showConfirmDialog(
                    TelaRemoverConta.this,
                    "Tem certeza de que deseja excluir sua conta? Esta ação não poderá ser desfeita.",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
                );
                if (resposta == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(TelaRemoverConta.this, "Conta excluída.");
                    // aqui chamaria o DAO pra deletar do banco
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaRemoverConta().setVisible(true));
    }
}