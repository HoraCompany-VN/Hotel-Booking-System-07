package HotelBooking;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HotelBooking {
    
    // Shared references for navigation between panels
    public static JPanel cardPanel;
    public static BookingConfirmationPanel bookingPanel;
    public static SearchRoomPanel searchRoomPanel;
    public static Entity.User currentUser;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hotel Booking System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1080, 1080);
            frame.setLocationRelativeTo(null);
            
            // Create CardLayout panel manager
            cardPanel = new JPanel(new CardLayout());
            
            // Create all panels
            Interface Interface = new Interface(cardPanel);
            LoginPanel loginPanel = new LoginPanel(cardPanel);
            RegisterPanel registerPanel = new RegisterPanel();
            searchRoomPanel = new SearchRoomPanel();
            bookingPanel = new BookingConfirmationPanel(cardPanel);
            
            // Add panels to CardLayout
            cardPanel.add(Interface, "Interface");
            cardPanel.add(loginPanel, "Login");
            cardPanel.add(registerPanel, "Register");
            cardPanel.add(searchRoomPanel, "SearchRoom");
            cardPanel.add(bookingPanel, "BookingConfirmation");
            
            frame.add(cardPanel);
            frame.setVisible(true);
            
            // Show login panel first
            showPanel("Interface");
        });
    }
    
    public static void showPanel(String panelName) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, panelName);
    }
    
    public static void setCurrentUser(Entity.User user) {
        currentUser = user;
        if (user != null && bookingPanel != null) {
            bookingPanel.prefillUser(user);
        }
    }
    
    public static Entity.User getCurrentUser() {
        return currentUser;
    }
}