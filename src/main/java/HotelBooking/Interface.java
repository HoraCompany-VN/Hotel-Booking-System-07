package HotelBooking;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Interface extends JFrame{

    public Interface() {

        this.setTitle("Hotel Booking System - Group 07");
        this.setSize(1366, 768);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Draw the backround at the first look
        BackgroundPanel bgPanel = new BackgroundPanel();
        bgPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Havana Hotel HCM city", JLabel.CENTER);
        label.setForeground(Color.WHITE); 
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));

        JLabel label2 = new JLabel("Doremon", JLabel.CENTER);
        label2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50));
        label2.setForeground(Color.white);


        this.setContentPane(bgPanel);
        this.add(label, BorderLayout.CENTER);
        this.add(label2, BorderLayout.LINE_END);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interface().setVisible(true);
        });
    }
}
