package Entity;

import java.sql.Date;
import java.util.List;

//Minh Hoa
public class User {
    private String userID;
    private String fullName;
    private String email;
    private String userPwd;
    private String phoneNumber;

    public User(String userID, String fullName, String email, String userPwd, String phoneNumber) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.userPwd = userPwd;
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() { return userID; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getUserPwd() { return userPwd; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setUserPwd(String userPwd) { this.userPwd = userPwd; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String register() {
        String sql = "INSERT INTO Customer(CustomerID, fullName, email, userPwd, phoneNumber) VALUES('" +
                     this.userID + "','" + this.fullName + "','" + this.email + "','" + this.userPwd + "','" + this.phoneNumber + "')";
        return sql;
    }

    public static boolean updateProfile(String customerID, String fullName, String email, String userPwd, String phoneNumber) {
        StringBuilder sql = new StringBuilder("UPDATE Customer SET ");
        boolean hasField = false;

        if (fullName != null && !fullName.isEmpty()) {
            sql.append("fullName='").append(fullName).append("'");
            hasField = true;
        }
        if (email != null && !email.isEmpty()) {
            if (hasField) sql.append(",");
            sql.append("email='").append(email).append("'");
            hasField = true;
        }
        if (userPwd != null && !userPwd.isEmpty()) {
            if (hasField) sql.append(",");
            sql.append("userPwd='").append(userPwd).append("'");
            hasField = true;
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            if (hasField) sql.append(",");
            sql.append("phoneNumber='").append(phoneNumber).append("'");
            hasField = true;
        }

        if (!hasField) return false;

        sql.append(" WHERE CustomerID='").append(customerID).append("'");
        DatabaseControl.updateTable(sql.toString());
        return true;
    }

    public List<Booking> viewBookings() {
        String query = "SELECT * FROM Booking WHERE CustomerID='" + this.userID + "'";
        return DatabaseControl.SelectBooking(query, "User");
    }

    public static boolean makeBooking(String customerID, int roomID, String managerID, 
                                       Date checkInDate, Date checkOutDate, double bookingPrice) {
        String sql = "INSERT INTO Booking(CustomerID,roomID,managerID,checkInDate,checkOutDate,bookingPrice,paymentStatus) VALUES('" +
                     customerID + "'," + roomID + ",'" + managerID + "','" + checkInDate + "','" + checkOutDate + "'," + bookingPrice + ",false)";
        DatabaseControl.insertTable(sql);
        return true;
    }

    public static boolean cancelBooking(int bookingID) {
        String sql = "DELETE FROM Booking WHERE BookingID=" + bookingID;
        DatabaseControl.insertTable(sql);
        return true;
    }

    public static boolean isUser(String phonenumber, String password){
    try {
        String sql = "SELECT * FROM Customer WHERE phoneNumber = '" 
                   + phonenumber + "' AND userPwd = '" + password + "'";

        List<User> users = DatabaseControl.SelectUsers(sql, "Customer");

        return users != null && !users.isEmpty();

    } catch (Exception e) {
        System.err.println("Error: " + e);
        return false;
    }
    }
}
