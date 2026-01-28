package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseControl {
    static Connection conn = null;

    public static void ConnectMySQl(){

        String url = "jdbc:mysql://localhost:3306/Hotel";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        try {    
            conn = DriverManager.getConnection(url, "root", "MySQLQuan123");
            System.out.println("Database connection");
        }catch (SQLException ex) {
            // handle the error
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static List<User> SelectUsers(String query, String Actor){
        ConnectMySQl();
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

        while(rs.next()){
            users.add(new User(
            rs.getString("userID"),
            rs.getString("fullName"),
            rs.getString("email"),
            rs.getString("userPwd"),
            rs.getString("phoneNumber")
        ));
        }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return users;
    }

    public static List<Room> SelectRoom(String query, String Actor){
        ConnectMySQl();
        List<Room> rooms = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

        while(rs.next()){
            rooms.add(new Room(
            rs.getInt("roomID"),
            rs.getInt("hotelID"),
            rs.getString("userID"),
            rs.getBoolean("status"),
            rs.getString("facilities"),
            rs.getString("description")
        ));
        }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return rooms;
    }

    public static List<Booking> SelectBooking(String query, String Actor){
        ConnectMySQl();
        List<Booking> books = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

        while(rs.next()){
            books.add(new Booking
        (
            rs.getInt("bookingID"),
            rs.getString("userID"),
            rs.getInt("roomID"),
            rs.getInt("managerID"),
            rs.getDate("checkInDate"),
            rs.getDate("checkOutDate"),
            rs.getDouble("bookingPrice"),
            rs.getBoolean("paymentStatus"),
            rs.getBoolean("Approve")       
        ));
        }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return books;
    }

    public static void insertTable(String command){
        ConnectMySQl();
        try {
            //Luc nay can build lai qua trinh create table
            PreparedStatement ps = conn.prepareStatement(command);
            ps.execute();
            System.err.println("Insert successfully");
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public static void updateTable(String command){
        ConnectMySQl();
        try {
            PreparedStatement ps = conn.prepareStatement(command);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No record found.");
            }

        } catch (Exception e) {
            System.err.println("Database Error: " + e);
        }
    }

    public static void deleteTable(String command) {
        ConnectMySQl();
        try {
            PreparedStatement ps = conn.prepareStatement(command);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("No record found to delete.");
            }
        } catch (Exception e) {
            System.err.println("Database Error: " + e);
        }
    }
}