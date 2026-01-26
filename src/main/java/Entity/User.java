package Entity;

import java.sql.Date;
import java.util.List;

public class User {
    private String userID;
    private String fullName;
    private String email;
    private String userPwd;
    private String phoneNumber;

    //Minh Hoa
    //Constructor
    public User(String userID, String fullName, String email,
                String userPwd, String phoneNumber) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.userPwd = userPwd;
        this.phoneNumber = phoneNumber;
    }

    //Minh Hoa - Getters
    public String getUserID() { return userID; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getUserPwd() { return userPwd; }
    public String getPhoneNumber() { return phoneNumber; }

    //Minh Hoa - Setters
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setUserPwd(String userPwd) { this.userPwd = userPwd; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    //Minh Hoa
    public static boolean updateProfile(String userID, String fullName,
                                         String email, String userPwd,
                                         String phoneNumber) {
        /*
        Cap nhat thong tin nguoi dung trong database
        Chi cap nhat cac truong khong null
        Su dung DatabaseControl.insertTable() de thuc thi SQL UPDATE
        */
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
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

        sql.append(" WHERE userID='").append(userID).append("'");
        DatabaseControl.insertTable(sql.toString());
        return true;
    }

    //Minh Hoa
    public static List<Booking> viewBookings(String userID) {
        /*
        Lay danh sach booking cua user tu database
        Su dung DatabaseControl.SelectBooking() de truy van
        */
        String query = "SELECT * FROM bookings WHERE userID='" + userID + "'";
        return DatabaseControl.SelectBooking(query, "User");
    }

    //Minh Hoa
    public static boolean makeBooking(String userID, int roomID, int managerID,
                                       Date checkInDate, Date checkOutDate,
                                       double bookingPrice) {
        /*
        Tao booking moi trong database
        paymentStatus va Approve mac dinh la false
        */
        String sql = "INSERT INTO bookings(userID,roomID,managerID,checkInDate," +
                     "checkOutDate,bookingPrice,paymentStatus,Approve) VALUES('" +
                     userID + "'," + roomID + "," + managerID + ",'" +
                     checkInDate + "','" + checkOutDate + "'," + bookingPrice + ",false,false)";
        DatabaseControl.insertTable(sql);
        return true;
    }

    //Minh Hoa
    public static boolean cancelBooking(int bookingID) {
        /*
        Huy booking theo bookingID
        Xoa record khoi database
        */
        String sql = "DELETE FROM bookings WHERE bookingID=" + bookingID;
        DatabaseControl.insertTable(sql);
        return true;
    }
}
