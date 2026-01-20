
use Hotel;
-- drop table Guest;
-- drop table Customer;
-- drop table Hotel; 
-- drop table Manager;
-- drop table Booking;
-- drop table Room;

create table Hotel(
	idHotel int PRIMARY KEY,
    name_hotel varchar(50),
    address varchar(100),
    phoneNo char(10)
);

create table Guest(
	guestID int PRIMARY KEY,
    accessDate date
);

create table Customer(
	 CustomerID char(5) PRIMARY KEY,
     fullName varchar(100),
     email varchar(50),
     userPwd varchar(50),
     phoneNumber char(10)
);

create table Manager(
	managerID varchar(3) PRIMARY KEY,
    idHotel int,
    FOREIGN KEY(idHotel) references Hotel(idHotel)
);

create table Room(
	roomID int PRIMARY KEY,
    idHotel int,
    CustomerID char(5),
    statusRoom Boolean,
    facilities text,
    description_room text,
    
	FOREIGN KEY(idHotel) references Hotel(idHotel),
    FOREIGN KEY(CustomerID) references Customer(CustomerID)
);

create table Booking(
	BookingID int PRIMARY KEY, 
    CustomerID char(5),
    roomID int,
    managerID varchar(3),
    checkInDate date,
    checkOutDate date,
    bookingPrice double,
    paymentStatus boolean,
    
	FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY(roomID) REFERENCES Room(roomID),
    FOREIGN KEY(managerID) REFERENCES Manager(managerID)
);


