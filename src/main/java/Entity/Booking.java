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

    public void setApprove(Boolean Allow){
        this.Approve = Allow;
    }
    public void updateBookingDate(Date checkIn, Date checkOut){
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }
    //Nguyen Quan
    public double TotalPrice(){
        return bookingPrice + 0.1 * bookingPrice;
    }
    //Nguyen Quan
    public void makePayment(){
        //Luc nay can tao them 1 class de lam payment lam lop ao
    }
    //Nguyen Quan
    public Boolean getPaymentStatus(){
        return paymentStatus;
    }
}
