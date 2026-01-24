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
     public void displayInfo() {
    }
    //Trinh
    public boolean statusRoom() {
        return status;
    }
    //Trinh
    public void updateStatus(boolean status) {
    }
    //Trinh
    public void assignCustomer(String userID) {
        //Phan nay co the chua can thiet boi vi co ham khoi tao lam roi
    }
    //Trinh
    public void removeCustomer() {
    }
}
