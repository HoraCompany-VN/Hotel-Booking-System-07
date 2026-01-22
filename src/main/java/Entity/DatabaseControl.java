package group7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseControl {
    public static void main(String[] args) {
        
    }

    static void ConnectMySQl(){

        Connection conn = null;
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

    static void createTable(){
        ConnectMySQl();
        try {
            //Luc nay
        } catch (Exception e) {
        }
    }
}