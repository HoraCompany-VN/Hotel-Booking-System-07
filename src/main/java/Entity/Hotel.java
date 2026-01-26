package Entity;

public class Hotel {
    private int hotelID;

    public String name;
    public String numberPhone;

     //Trinh
    //Constructor
    public Hotel(int hotelID, String nameHotel, String phone) {
        this.hotelID = hotelID;
        this.name = nameHotel;
        this.numberPhone = phone;
    }

    // Lấy danh sách phòng còn trống của khách sạn
    public static List<Room> getAvailableRoom(int hotelID) {

        /*
         Room kết nối với database thông qua DatabaseControl
         Quan tâm tới Room.status = true (Available)
         */

        String sql = "SELECT * FROM Room WHERE idHotel = "
                   + hotelID + " AND statusRoom = true";

        // Gọi DatabaseControl để lấy danh sách phòng
        return DatabaseControl.SelectRoom(sql, "User");
    }

    // (Optional) dùng để test
    public void displayInfo() {
        System.out.println("Hotel ID: " + hotelID);
        System.out.println("Hotel Name: " + name);
        System.out.println("Phone: " + numberPhone);
    }
}
