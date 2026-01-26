package Entity;

public class Room {
    private int roomID;
    private int hotelID;
    private String userID;
    public boolean status; // true = Available, false = Not Available
    public String facilities;
    public String description;

    //Trinh
    //Constructor
    public Room(int roomID, int hotelID, String userID,
                boolean status, String facilities, String description) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.userID = userID;
        this.status = status;
        this.facilities = facilities;
        this.description = description;
    }

    //Trinh
    // Hiển thị thông tin phòng 
    public void displayInfo() {
        System.out.println("Room ID: " + roomID);
        System.out.println("Hotel ID: " + hotelID);
        System.out.println("User ID: " + userID);
        System.out.println("Status: " + (status ? "Available" : "Not Available"));
        System.out.println("Facilities: " + facilities);
        System.out.println("Description: " + description);
        System.out.println("---------------------------");
    }

    // Trả về trạng thái phòng
    public boolean statusRoom() {
        return status;
    }

    // Cập nhật trạng thái phòng
    public void updateStatus(boolean status) {
        this.status = status;
    }

    // Gán khách vào phòng
    public void assignCustomer(String userID) {
        this.userID = userID;
        this.status = false; // có khách → phòng không còn trống
    }

    // Xóa khách khỏi phòng
    public void removeCustomer() {
        this.userID = null;
        this.status = true; // phòng trống lại
    }
}
