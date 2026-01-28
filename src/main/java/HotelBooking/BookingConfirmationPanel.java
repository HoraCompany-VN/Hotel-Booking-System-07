package HotelBooking;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Entity.Payment;
import Entity.User;

public class BookingConfirmationPanel extends JPanel {
    
    private JTextField txtName, txtPhone, txtEmail;
    private JLabel lblRoom, lblCheckIn, lblCheckOut, lblGuests, lblPrice;
    private JRadioButton rbPayNow, rbPayLater;
    private int roomID = -1;
    private String managerID, customerID, roomType;
    private double price;
    private LocalDate checkIn, checkOut;
    private JPanel parent;

    public BookingConfirmationPanel(JPanel parent) {
        this.parent = parent;
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        add(createForm(), BorderLayout.CENTER);
        add(createSummary(), BorderLayout.EAST);
    }
    
    // No-arg constructor for Interface.java compatibility
    public BookingConfirmationPanel() {
        this(null);
    }

    private JPanel createForm() {
        JPanel p = box();
        p.setBorder(new CompoundBorder(new LineBorder(new Color(230,230,230)), new EmptyBorder(25,25,25,25)));
        
        add(p, lbl("Contact us to make a reservation.", 16, true));
        add(p, lbl("Add contact information to receive booking confirmation.", 12, false));
        add(p, Box.createVerticalStrut(20));
        add(p, lbl("Full name *", 13, false));
        txtName = txt(350); add(p, txtName);
        add(p, lbl("Mobile phone * (+84)", 13, false));
        txtPhone = txt(200); add(p, txtPhone);
        add(p, lbl("Email *", 13, false));
        txtEmail = txt(350); add(p, txtEmail);
        return p;
    }

    private JPanel createSummary() {
        JPanel p = box();
        p.setBorder(new CompoundBorder(new LineBorder(new Color(230,230,230)), new EmptyBorder(20,20,20,20)));
        p.setPreferredSize(new Dimension(300, 450));

        lblRoom = lbl("Select a room first", 14, true);
        lblCheckIn = lbl("-", 12, true);
        lblCheckOut = lbl("-", 12, true);
        lblGuests = lbl("-", 12, false);
        lblPrice = lbl("-", 14, true);
        lblPrice.setForeground(new Color(0, 102, 204));

        rbPayNow = new JRadioButton("Pay now"); rbPayNow.setBackground(Color.WHITE); rbPayNow.setSelected(true);
        rbPayLater = new JRadioButton("Pay at hotel"); rbPayLater.setBackground(Color.WHITE);
        ButtonGroup bg = new ButtonGroup(); bg.add(rbPayNow); bg.add(rbPayLater);

        JButton btnOK = btn("Confirm Booking", new Color(0,102,204), e -> confirm());
        JButton btnBack = btn("Back", null, e -> goBack());

        add(p, lblRoom);
        add(p, lbl("Only 2 rooms left.", 11, false, new Color(200,50,50)));
        add(p, Box.createVerticalStrut(15));
        add(p, lbl("Check-in:", 11, false)); add(p, lblCheckIn);
        add(p, lbl("Check-out:", 11, false)); add(p, lblCheckOut);
        add(p, Box.createVerticalStrut(10));
        add(p, lblGuests);
        add(p, lbl("✓ Free cancellation", 11, false, new Color(0,128,0)));
        add(p, Box.createVerticalStrut(15));
        add(p, new JSeparator());
        add(p, Box.createVerticalStrut(10));
        add(p, lblPrice);
        add(p, Box.createVerticalStrut(15));
        add(p, lbl("Choose when you want to pay:", 13, true));
        add(p, rbPayNow); add(p, rbPayLater);
        add(p, Box.createVerticalStrut(20));
        add(p, btnOK); add(p, Box.createVerticalStrut(8)); add(p, btnBack);
        return p;
    }

    private void confirm() {
        if (roomID < 0) { msg("Please select a room first!"); goBack(); return; }
        if (txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            msg("Please fill all fields!"); return;
        }
        User.makeBooking(customerID != null ? customerID : "GUEST", roomID, managerID != null ? managerID : "MGR001",
            Date.valueOf(checkIn), Date.valueOf(checkOut), price);
        
        if (rbPayNow.isSelected()) pay(); else success("Pay at Hotel");
    }

    private void pay() {
        int ch = JOptionPane.showOptionDialog(this, "Payment method:", "Pay", 0, 3, null, new String[]{"Card","QR"}, "Card");
        if (ch == 0) {
            String card = JOptionPane.showInputDialog(this, "Card Number:");
            if (card != null && Payment.CardPayment.isValid(card)) {
                new Payment.CardPayment(0, price, txtName.getText(), card, "12/26", "123").processPayment();
                success("Card");
            } else if (card != null) msg("Invalid card!");
        } else if (ch == 1) {
            JOptionPane.showMessageDialog(this, "QR: " + new Payment.QRPayment(0, price).getQRContent());
            success("QR");
        }
    }

    private void success(String method) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MMM d, yyyy");
        msg("Booking Confirmed!\n\nRoom: " + roomType + "\nGuest: " + txtName.getText() + 
            "\n" + checkIn.format(f) + " → " + checkOut.format(f) + "\nPayment: " + method);
        txtName.setText(""); txtPhone.setText(""); txtEmail.setText(""); roomID = -1; goBack();
    }

    private void goBack() { if (parent != null) ((CardLayout)parent.getLayout()).show(parent, "SearchRoom"); }
    private void msg(String s) { JOptionPane.showMessageDialog(this, s); }

    // UI Helpers
    private JPanel box() { JPanel p = new JPanel(); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); p.setBackground(Color.WHITE); return p; }
    private JLabel lbl(String t, int sz, boolean b) { return lbl(t, sz, b, null); }
    private JLabel lbl(String t, int sz, boolean b, Color c) { JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", b?1:0, sz)); l.setAlignmentX(LEFT_ALIGNMENT); if(c!=null)l.setForeground(c); return l; }
    private JTextField txt(int w) { JTextField t = new JTextField(); t.setMaximumSize(new Dimension(w, 32)); t.setAlignmentX(LEFT_ALIGNMENT); return t; }
    private JButton btn(String t, Color bg, java.awt.event.ActionListener a) { JButton b = new JButton(t); if(bg!=null){b.setBackground(bg);b.setForeground(Color.WHITE);} b.setMaximumSize(new Dimension(260, 38)); b.setAlignmentX(LEFT_ALIGNMENT); b.addActionListener(a); return b; }
    private void add(JPanel p, Component c) { p.add(c); p.add(Box.createVerticalStrut(5)); }

    // Public API
    public void setBookingInfo(int roomID, String roomType, String managerID, LocalDate in, LocalDate out, int guests, double price) {
        this.roomID = roomID; this.roomType = roomType; this.managerID = managerID;
        this.checkIn = in; this.checkOut = out; this.price = price;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy");
        lblRoom.setText("(1x) " + roomType);
        lblCheckIn.setText(in.format(f)); lblCheckOut.setText(out.format(f));
        lblGuests.setText(guests + " guests");
        lblPrice.setText(String.format("Total: %,.0f VND", price * 1.1));
    }
    public void setCustomerID(String id) { customerID = id; }
    public void prefillUser(User u) { if(u!=null){ txtName.setText(u.getFullName()); txtPhone.setText(u.getPhoneNumber()); txtEmail.setText(u.getEmail()); customerID=u.getUserID(); }}
}
