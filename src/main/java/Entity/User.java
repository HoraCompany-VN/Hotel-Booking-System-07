package Entity;
import java.util.List;

public class User { //custormer has same meaning with usser
    private String userID;
    private String fullName;
    private String email;
    private String userPwd;
    private String phoneNumber;

    //Minh Hoa
    //Constructor with .... elements
    public User(){ 
        
    }

    //Minh Hoa
    static void updateProfile(){

    }

    static List<Booking> viewBookings(){
        /*
        Khuc nay la viewBooking thi lam sao de thay dc nhung phong minh dat?
        Ta can phai truy cap vao database
        Ham connect da duoc xay dung hay tim cach select
        Khi select duoc ket qua tao 1 bien List<Booking> truyen danh sach do vao bien nay
        Luc nay ta return lai thoi
        */
      return null;
    }

    //Minh Hoa
    static boolean makeBooking(){
        /*
        O day cung can truy cap vao db 1 phan
        sau khi truy cap ta se dung ham update cai ngay thang checkIn va checkOut
        update xong thi return gia tri da tao duoc hay chua
        */
        return true;
    }

    //Minh Hoa
    static boolean cancleBooking(int BookingID){
        /*
        Giong tren kia
        */
        return true;
    }
}
