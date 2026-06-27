package ecocoleta.s;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {

    private final Color cor;
    private final int raio;

    public RoundedBorder(Color cor, int raio) {
        this.cor = cor;
        this.raio = raio;
    }

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
        return new Insets(10, 15, 10, 15);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(10, 15, 10, 15);
        return insets;
    }
}