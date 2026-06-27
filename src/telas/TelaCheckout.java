package telas;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;

public class TelaCheckout extends JFrame {

    public TelaCheckout() {
        setTitle("EcoColeta - Checkout");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnVoltar = new JButton("← Voltar ao início");
        btnVoltar.setBounds(20, 20, 200, 30);
        add(btnVoltar);

        JPanel mapa = new JPanel();
        mapa.setBackground(new Color(230, 235, 230));
        mapa.setBounds(0, 60, 360, 500);
        add(mapa);

        btnVoltar.addActionListener(e -> {
            new TelaHome().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaCheckout().setVisible(true));
    }
}