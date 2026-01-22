package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DatabaseControl {
    static Connection conn = null;

    static void ConnectMySQl(){

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

    static void insertTable(){
        ConnectMySQl();
        List<String> User = Arrays.asList("");
        try {
            //Luc nay can build lai qua trinh create table
            PreparedStatement ps = conn.prepareStatement("");
        } catch (Exception e) {
        }
    }
}