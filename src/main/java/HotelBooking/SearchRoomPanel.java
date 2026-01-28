package HotelBooking;

import Entity.Room;
import Entity.DatabaseControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
// Trinh 
public class SearchRoomPanel extends JPanel {

    private JTextField txtFacilities;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public SearchRoomPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ===== HEADER =====
        JLabel lblHeader = new JLabel("HOTEL ROOM SEARCH", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblHeader.setForeground(new Color(10, 80, 160));
        add(lblHeader, BorderLayout.NORTH);

        // ===== SEARCH PANEL =====
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(245, 245, 250));
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);

        JLabel lblFacilities = new JLabel("Amenities:");
        lblFacilities.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridx = 0;
        c.gridy = 0;
        searchPanel.add(lblFacilities, c);

        txtFacilities = new JTextField(20);
        txtFacilities.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.gridx = 1;
        searchPanel.add(txtFacilities, c);

        btnSearch = new JButton("SEARCH");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        c.gridx = 2;
        searchPanel.add(btnSearch, c);

        add(searchPanel, BorderLayout.CENTER);

        // ===== TABLE =====
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "Room ID", "Hotel ID", "Status", "Facilities", "Description"
        });

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(220, 230, 250));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        btnSearch.addActionListener(this::searchRoom);
    }

    private void searchRoom(ActionEvent e) {
        tableModel.setRowCount(0);

        String facilities = txtFacilities.getText().trim();

        String sql = "SELECT * FROM Room WHERE statusRoom = true";
        if (!facilities.isEmpty()) {
            sql += " AND facilities LIKE '%" + facilities + "%'";
        }

        List<Room> rooms = DatabaseControl.SelectRoom(sql, "Guest");

        for (Room r : rooms) {
            tableModel.addRow(new Object[]{
                    r.getRoomID(),
                    r.getHotelID(),
                    r.isAvailable() ? "Available" : "Unavailable",
                    r.getFacilities(),
                    r.getDescription()
            });
        }
    }
}
