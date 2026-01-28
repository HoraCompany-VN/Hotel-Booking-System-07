package HotelBooking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import Entity.DatabaseControl;
import Entity.Room;

public class SearchRoomPanel extends JPanel {

    private DefaultTableModel model;
    private JTable tblRoom;
    private JTextField txtFacilities;

    public SearchRoomPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel lblTitle = new JLabel("Search Available Rooms");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblTitle, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.add(new JLabel("Facilities:"));
        txtFacilities = new JTextField(20);
        searchPanel.add(txtFacilities);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(0, 102, 204));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> searchRoom());
        searchPanel.add(btnSearch);
        
        JButton btnBack = new JButton("Logout");
        btnBack.addActionListener(e -> HotelBooking.showPanel("Login"));
        searchPanel.add(btnBack);

        // Table
        String[] columns = {"Room ID", "Hotel ID", "Status", "Facilities", "Description", "Price (VND)"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblRoom = new JTable(model);
        tblRoom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRoom.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(tblRoom);

        // Bottom - Book button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 245, 245));
        
        JButton btnBook = new JButton("Book Selected Room");
        btnBook.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBook.setBackground(new Color(0, 153, 51));
        btnBook.setForeground(Color.WHITE);
        btnBook.addActionListener(e -> bookRoom());
        bottomPanel.add(btnBook);

        // Layout
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        searchRoom(); // Load on init
    }

    private void searchRoom() {
        model.setRowCount(0);
        String facilities = txtFacilities.getText().trim();
        String sql = "SELECT * FROM Room WHERE statusRoom = true";
        if (!facilities.isEmpty()) {
            sql += " AND facilities LIKE '%" + facilities + "%'";
        }

        List<Room> rooms = DatabaseControl.SelectRoom(sql, "Guest");
        for (Room r : rooms) {
            double price = 1000000 + (r.getRoomID() * 100000);
            model.addRow(new Object[]{
                r.getRoomID(),
                r.getHotelID(),
                r.status ? "Available" : "Unavailable",
                r.facilities,
                r.description,
                String.format("%,.0f", price)
            });
        }
    }

    private void bookRoom() {
        int row = tblRoom.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a room first!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomID = (int) model.getValueAt(row, 0);
        String facilities = (String) model.getValueAt(row, 3);
        String priceStr = (String) model.getValueAt(row, 5);
        double price = Double.parseDouble(priceStr.replace(",", ""));

        // Date picker
        JTextField txtIn = new JTextField(LocalDate.now().plusDays(1).toString());
        JTextField txtOut = new JTextField(LocalDate.now().plusDays(2).toString());
        JSpinner spnGuests = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));

        int result = JOptionPane.showConfirmDialog(this, new Object[]{
            "Check-in (YYYY-MM-DD):", txtIn,
            "Check-out (YYYY-MM-DD):", txtOut,
            "Guests:", spnGuests
        }, "Booking Details", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) return;

        try {
            LocalDate checkIn = LocalDate.parse(txtIn.getText());
            LocalDate checkOut = LocalDate.parse(txtOut.getText());
            int guests = (int) spnGuests.getValue();

            if (!checkOut.isAfter(checkIn)) {
                JOptionPane.showMessageDialog(this, "Check-out must be after check-in!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
            double total = price * nights;

            // Pass to BookingConfirmationPanel
            HotelBooking.bookingPanel.setBookingInfo(roomID, "Room " + roomID + " - " + facilities, "MGR001", checkIn, checkOut, guests, total);
            if (HotelBooking.getCurrentUser() != null) {
                HotelBooking.bookingPanel.prefillUser(HotelBooking.getCurrentUser());
            }
            HotelBooking.showPanel("BookingConfirmation");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
