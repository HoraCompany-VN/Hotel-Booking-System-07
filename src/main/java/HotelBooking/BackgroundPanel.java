package HotelBooking;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    private Image background;

    public BackgroundPanel() {
        // load ảnh nền
        this.background = Toolkit.getDefaultToolkit().createImage("database\\Picture\\im_interface.jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (background != null) {
            int w = background.getWidth(this);
            int h = background.getHeight(this);
            if (w > 0 && h > 0) {
                return new Dimension(w, h);
            }
        }
        // fallback nếu ảnh chưa load được
        return new Dimension(1920, 1080);
    }
}