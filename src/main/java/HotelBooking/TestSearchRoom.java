package HotelBooking;

import javax.swing.*;

public class TestSearchRoom {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hotel Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        frame.add(new SearchRoomPanel());
        frame.setVisible(true);
    }
}
