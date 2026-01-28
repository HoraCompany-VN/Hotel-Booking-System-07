package Entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Guest {
    private int guestID;
    public Guest(int ID) {
        this.guestID = ID;
    }

    public String accessDate(){
        // Source - https://stackoverflow.com/a/6953926
        // Posted by Liebster Kamerad, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-01-28, License - CC BY-SA 4.0

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }
}
