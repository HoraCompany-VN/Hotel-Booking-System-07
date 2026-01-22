package Entity;

import java.util.Date;

public class Booking {
    private int bookingID;
    private String userID;
    private int roomID;
    private int managerID;
    private Date checkInDate;
    private Date checkOutDate;
    private double bookingPrice;
    private Boolean paymentStatus;
    
    //Nguyen Quan
    //Constructor with ... elements
    public Booking(int bookID, String userID, int managerID, Date C_in, Date C_out, double bookingPrice){
        this.bookingID = bookID;
        this.userID = userID;
        this.managerID = managerID;
        this.checkInDate = C_in;
        this.checkOutDate = C_out;
        this.bookingPrice = bookingPrice;
        this.paymentStatus = false;
    }

    static void createBooking(){
        
    }
    static void updateBookingDate(Date checkIn, Date checkOut){
        
    }
    //Nguyen Quan
    static double TotalPrice(){
        return bookingPrice + 0.1 * bookingPrice;
    }
    //Nguyen Quan
    static void makePayment(){
        //Luc nay can tao them 1 class de lam payment lam lop ao
    }
    //Nguyen Quan
    public Boolean getPaymentStatus(){
        return paymentStatus;
    }
}
