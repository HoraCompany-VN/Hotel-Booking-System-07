package HotelBooking;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Entity.Hotel;

public class Interface {
    Hotel myHotel = new Hotel(123, "OK", "");
    public Interface(){

        javax.swing.SwingUtilities.invokeLater(() -> {
        // Tạo frame chứa BackgroundPanel
        javax.swing.JFrame frame = new javax.swing.JFrame("Hotel Booking System");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        BackgroundPanel bgnal = new BackgroundPanel();
        bgnal.setLayout(new java.awt.BorderLayout());
        
        // Đặt panel làm content pane
        frame.setContentPane(bgnal);
        LoginPanel login = new LoginPanel();
        frame.add(login);
        frame.setVisible(true);
        });
    }
}
