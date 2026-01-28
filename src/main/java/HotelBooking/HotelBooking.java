/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package HotelBooking;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Wuan
 */
public class HotelBooking {
    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("Hotel Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        
        // Create CardLayout panel manager
        JPanel cardPanel = new JPanel(new java.awt.CardLayout());
        
        // Add panels to CardLayout
        LoginPanel loginPanel = new LoginPanel(cardPanel);
        Interface interfacePanel = new Interface(cardPanel);
        
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(interfacePanel, "Main");
        
        frame.add(cardPanel);
        frame.setVisible(true);
        
        // Show login panel first
        java.awt.CardLayout cl = (java.awt.CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Login");
    }
}