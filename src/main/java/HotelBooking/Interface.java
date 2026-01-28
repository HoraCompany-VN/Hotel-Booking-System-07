package HotelBooking;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
;

public class Interface extends JFrame {
    public int Width = 1080;
    public int Height = 1080;
    public Interface(){

        JFrame window = new JFrame("Hotel Booking System");
        window.setSize(Width, Height);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Havana Airport Hotel");
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36)); 
        label.setForeground(java.awt.Color.WHITE);
        window.add(label, BorderLayout.CENTER); 

        ImageIcon anhNen = new ImageIcon("database\\Picture\\im_interface.jpg");
        anhNen = resizeIcon(anhNen, Width, Height);
        label.setIcon(anhNen);
        label.setHorizontalTextPosition(JLabel.CENTER); 
        label.setVerticalTextPosition(JLabel.CENTER);
        String html = "<html>" +
            "<div style='padding-left: 250px;'>" + // Đẩy toàn bộ nội dung sang phải 250px
                "<h1 style='text-indent: -50px;'>Havana Airport Hotel From Vietnam - Ho Chi Minh City</h1>" + // Dòng này dịch thêm 50px sang phải
                
                "<div style='margin-top: 400px; text-indent: -90px;'>" + // Khoảng trống và dịch dòng dưới sang trái
                    "25/17 Cuu Long, Tan Binh, HCMC, Vietnam<br>" +
                    "Contact Number: 0381234567" +
                "</div>" +
            "</div>" +
        "</html>";

        label.setText(html);
        window.setVisible(true);

        window.addMouseListener(new java.awt.event.MouseAdapter() {});
    }
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
