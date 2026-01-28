package HotelBooking;

import Entity.DatabaseControl;
import Entity.Room;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class SearchRoomPanel extends javax.swing.JPanel {

    DefaultTableModel model;

    public SearchRoomPanel() {
        initComponents();
        initTable();
    }

    private void initTable() {
        model = (DefaultTableModel) tblRoom.getModel();
        model.setColumnIdentifiers(new String[]{
            "Room ID", "Hotel ID", "Status", "Facilities", "Description"
        });
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {

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
}
