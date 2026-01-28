package HotelBooking;

import java.awt.BorderLayout;

import Entity.Hotel;

public class Interface {
    Hotel myHotel = new Hotel(123, "OK", "0383893208");
    public Interface(){
        javax.swing.SwingUtilities.invokeLater(() -> {
        // Tạo frame chứa BackgroundPanel
        javax.swing.JFrame frame = new javax.swing.JFrame("Hotel Booking System");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        BackgroundPanel bgnal = new BackgroundPanel();
        bgnal.setLayout(new java.awt.BorderLayout());

        // Đặt panel làm content pane
        frame.setContentPane(bgnal);
        //Add Login Page vao moi
        LoginPanel login = new LoginPanel();
        bgnal.add(login, BorderLayout.CENTER);

        // Nếu BackgroundPanel có preferred size hợp lệ thì dùng pack(), nếu không thì setSize()
        if (bgnal.getPreferredSize() != null && bgnal.getPreferredSize().width > 0) {
            frame.pack();
        } else {
            frame.setSize(1280, 720); // kích thước dự phòng
        }

        frame.setLocationRelativeTo(null); // căn giữa màn hình
        frame.setVisible(true);
        });
    }
}
