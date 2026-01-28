package Entity;

import java.util.List;

//Thao works
public class Manager {
    private String managerID;
    private int idHotel;

    public Manager(String managerID, int idHotel) {
        this.managerID = managerID;
        this.idHotel = idHotel;
    }

    public String getManagerID() { return managerID; }
    public int getIdHotel() { return idHotel; }

    // ROOM MANAGEMENT
    public boolean addRoom(String facilities, String description) {
        String sql = "INSERT INTO Room(idHotel,statusRoom,facilities,description_room) VALUES(" +
                     idHotel + ",true,'" + facilities + "','" + description + "')";
        DatabaseControl.insertTable(sql);
        return true;
    }

    public boolean deleteRoom(int roomID) {
        String sql = "DELETE FROM Room WHERE roomID=" + roomID + " AND idHotel=" + idHotel;
        DatabaseControl.deleteTable(sql);
        return true;
    }

    public boolean updateRoom(int roomID, String facilities, String description) {
        String sql = "UPDATE Room SET facilities='" + facilities + "',description_room='" + description + 
                     "' WHERE roomID=" + roomID + " AND idHotel=" + idHotel;
        DatabaseControl.updateTable(sql);
        return true;
    }

    public boolean updateRoomStatus(int roomID, boolean status) {
        String sql = "UPDATE Room SET statusRoom=" + status + " WHERE roomID=" + roomID;
        DatabaseControl.updateTable(sql);
        return true;
    }

    public List<Room> viewRooms() {
        String sql = "SELECT * FROM Room WHERE idHotel=" + idHotel;
        return DatabaseControl.SelectRoom(sql, "Manager");
    }

    // BOOKING MANAGEMENT
    public List<Booking> viewBookings() {
        String sql = "SELECT * FROM Booking WHERE managerID='" + managerID + "'";
        return DatabaseControl.SelectBooking(sql, "Manager");
    }

    public List<Booking> viewPendingBookings() {
        String sql = "SELECT * FROM Booking WHERE managerID='" + managerID + "' AND paymentStatus=false";
        return DatabaseControl.SelectBooking(sql, "Manager");
    }

    public boolean confirmBooking(int bookingID) {
        String sql = "UPDATE Booking SET paymentStatus=true WHERE BookingID=" + bookingID;
        DatabaseControl.updateTable(sql);
        return true;
    }

    public boolean cancelBooking(int bookingID) {
        String sql = "DELETE FROM Booking WHERE BookingID=" + bookingID;
        DatabaseControl.deleteTable(sql);
        return true;
    }

    // CUSTOMER MANAGEMENT
    public List<User> viewCustomers() {
        String sql = "SELECT * FROM Customer";
        return DatabaseControl.SelectUsers(sql, "Manager");
    }

    public boolean addCustomer(String customerID, String fullName, String email, String pwd, String phone) {
        String sql = "INSERT INTO Customer(CustomerID,fullName,email,userPwd,phoneNumber) VALUES('" +
                     customerID + "','" + fullName + "','" + email + "','" + pwd + "','" + phone + "')";
        DatabaseControl.insertTable(sql);
        return true;
    }

    public boolean deleteCustomer(String customerID) {
        String sql = "DELETE FROM Customer WHERE CustomerID='" + customerID + "'";
        DatabaseControl.deleteTable(sql);
        return true;
    }

    public boolean updateCustomer(String customerID, String fullName, String email, String phone) {
        String sql = "UPDATE Customer SET fullName='" + fullName + "',email='" + email + 
                     "',phoneNumber='" + phone + "' WHERE CustomerID='" + customerID + "'";
        DatabaseControl.updateTable(sql);
        return true;
    }
}
