package HotelBooking;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    private Image background;

    public BackgroundPanel() {
        this.background = Toolkit.getDefaultToolkit().createImage("database\\Picture\\im_interface.jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                background.getWidth(this),
                background.getHeight(this)
        );
    }
}
