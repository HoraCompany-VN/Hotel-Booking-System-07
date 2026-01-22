package Entity;

public class Manager {
    private String managerID;
    private int hotelID;

    //Thao works
    //Constructor
    public Manager(String m_id, int hotelID){
        this.managerID = m_id;
        this.hotelID = hotelID;
    }
    //Manager chu yeu can xay dung ket noi voi databse truoc
    //Tao 1 cai bien DatabaseControl

    //Thao
    public void manageRoom(){
        /*
        manageRoom thi can liet ke no co nhung chuc nang nao doi voi Room vi du
        + Them
        + Xoa
        + Cap nhap

        Trong do thi can phai xay dung cac cau lenh 
        Them thi ta insert va ta build giong nhu hoi hoc sql
        Xoa thi Delete chu yeu o day la xoa phong
        Cap nhat thi co the nen cap nhat 1 hay 2 thong tin gi do thoi la du
        */
    }

    //Thao
    public void manageBookings(){
        /*
        cai booking nay no quan trong o diem la duyet yeu cau dat phong
        B1: Select Booking from * gi do do
        B2: Update -> o day cu tim cach auto duyet cho Booking bang cach
        2.1 Call method thang booking va setApprove(<true hay false>)
        2.2 Database tu update minh khong can update 
        2.3 Can xay dung 1 ham neu setApprove(false) thi phai co 
            delete Booking
        2.4 Done
        */
    }  

    //Thao
    public void manageUser(){
        /*
        Them
        Xoa
        Cap nhat
        Duyet nguoi dang ki

        Build truoc command SQL cho 3 chuc nang dau di nha con Duyet nguoi 
        dang ki chua co y tuong lam
        */
    }
}
