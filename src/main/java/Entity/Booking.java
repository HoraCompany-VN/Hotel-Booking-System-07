package Entity;

import java.sql.Date;

public class Booking {
    private int bookingID;
    private String userID;
    private int roomID;
    private int managerID;
    private Date checkInDate;
    private Date checkOutDate;
    private double bookingPrice;
    private Boolean paymentStatus;
    private Boolean Approve;
    //Nguyen Quan
    //Constructor with ... elements
    public Booking(int bookingID, String userID, int roomID, int managerID, Date C_in, Date C_out, 
                    double bookingPrice, boolean paymentStatus, boolean Approve){
        this.bookingID = bookingID;
        this.userID = userID;
        this.roomID = roomID;
        this.managerID = managerID;
        this.checkInDate = C_in;
        this.checkOutDate = C_out;
        this.bookingPrice = bookingPrice;
        this.paymentStatus = false;
        this.Approve = false;
    }

    public int getBookingID() { return this.bookingID; }
    public String getUserID() { return this.userID; }
    public int getRoomID() { return this.roomID; }
    public int getManagerID() { return this.managerID; }
    public Date getCheckInDate() { return this.checkInDate; }
    public Date getCheckOutDate() { return this.checkOutDate; }
    public double getBookingPrice() { return this.bookingPrice; }
    public Boolean getApprove() { return this.Approve; }

    // Setter
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }
    public void setUserID(String userID) { this.userID = userID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    public void setManagerID(int managerID) { this.managerID = managerID; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setBookingPrice(double bookingPrice) { this.bookingPrice = bookingPrice; }
    public void setPaymentStatus(Boolean paymentStatus) { this.paymentStatus = paymentStatus; }

    public double TotalPrice(){ 
        return this.bookingPrice * 1.1; 
    }

}
