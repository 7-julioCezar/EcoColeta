package ecocoleta.s;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedTextField extends JTextField {

    public RoundedTextField() {
        setBorder(new CompoundBorder(
                new RoundedBorder(new Color(220, 220, 220), 15),
                new EmptyBorder(8, 12, 8, 12)));
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(Color.WHITE);
    }

    public RoundedTextField(String placeholder) {
        this();
        setToolTipText(placeholder);
    }
}