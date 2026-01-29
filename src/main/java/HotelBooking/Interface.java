package HotelBooking;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Interface extends JFrame {
    public int Width = 1080;
    public int Height = 1080;
    private JPanel cardPanel;

    public Interface(JPanel cardPanel){
        this.cardPanel = cardPanel;
        JFrame window = new JFrame("Hotel Booking System");
        window.setSize(Width, Height);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());
        
        // Home panel with background
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Havana Airport Hotel");
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36)); 
        label.setForeground(java.awt.Color.WHITE);

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
        //đã sửa
        homePanel.add(label, BorderLayout.CENTER);
        
        // Book Now button
        javax.swing.JButton btnBookNow = new javax.swing.JButton("Book Now");
        btnBookNow.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        btnBookNow.setBackground(new java.awt.Color(0, 102, 204));
        btnBookNow.setForeground(java.awt.Color.WHITE);
        btnBookNow.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Login");
        });
        homePanel.add(btnBookNow, BorderLayout.SOUTH);
        
        // Add Login Panel
        LoginPanel loginPanel = new LoginPanel(mainPanel);

        // Add panels to CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(loginPanel, "Login");
        
        window.add(mainPanel);
        window.setVisible(true);

        window.addMouseListener(new java.awt.event.MouseAdapter() {});
    }
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
