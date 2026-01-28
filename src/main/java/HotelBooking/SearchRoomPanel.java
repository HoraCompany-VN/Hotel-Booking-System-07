package HotelBooking;

import Entity.DatabaseControl;
import Entity.Room;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class SearchRoomPanel extends javax.swing.JPanel {

    DefaultTableModel model;

    public SearchRoomPanel() {
        initComponents();

        model = (DefaultTableModel) tblRoom.getModel();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblHotel = new javax.swing.JLabel();
        txtHotel = new javax.swing.JTextField();
        lblFacilities = new javax.swing.JLabel();
        txtFacilities = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoom = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 245, 245));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitle.setText("SEARCH ROOM");

        lblHotel.setText("Hotel name:");

        lblFacilities.setText("Facilities / Description:");

        btnSearch.setText("Search");
        btnSearch.addActionListener(evt -> searchRoom());

        tblRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Room ID", "Hotel ID", "Status", "Facilities", "Description"
            }
        ));
        jScrollPane1.setViewportView(tblRoom);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHotel)
                        .addGap(10, 10, 10)
                        .addComponent(txtHotel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lblFacilities)
                        .addGap(10, 10, 10)
                        .addComponent(txtFacilities, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnSearch))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(20)
            .addComponent(lblTitle)
            .addGap(20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblHotel)
                .addComponent(txtHotel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblFacilities)
                .addComponent(txtFacilities, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSearch))
            .addGap(20)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20)
        );
    }

    private void searchRoom() {
        model.setRowCount(0);

        String facilities = txtFacilities.getText().trim();

        String sql = "SELECT * FROM Room WHERE status = true";
        if (!facilities.isEmpty()) {
            sql += " AND facilities LIKE '%" + facilities + "%'";
        }

        List<Room> rooms = DatabaseControl.SelectRoom(sql, "Guest");

        for (Room r : rooms) {
            model.addRow(new Object[]{
                r.getRoomID(),
                r.getHotelID(),
                r.status ? "Available" : "Unavailable",
                r.facilities,
                r.description
            });
        }
    }

    // Variables declaration
    private javax.swing.JButton btnSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFacilities;
    private javax.swing.JLabel lblHotel;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblRoom;
    private javax.swing.JTextField txtFacilities;
    private javax.swing.JTextField txtHotel;
}
