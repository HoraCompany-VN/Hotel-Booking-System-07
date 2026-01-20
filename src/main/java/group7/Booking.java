package group7;

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
    public Booking(int bookID){
        this.bookingID = bookID;
    }

    static void createBooking(){
        
    }
    static void updateBookingDate(Date checkIn, Date checkOut){
        
    }
    //Nguyen Quan
    static double TotalPrice(){
        return 0.0;
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
