package HotelBooking;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Interface extends JFrame{
    public Interface() {
        setTitle("Hotel Booking System - Group 07");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Draw the backround at the first look
        BackgroundPanel bgPanel = new BackgroundPanel();
        setContentPane(bgPanel);

        bgPanel.setLayout(null);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(10, 0, 5, 0));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interface().setVisible(true);
        });
    }
}
