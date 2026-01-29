CREATE DATABASE IF NOT EXISTS Hotel;

USE Hotel;

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
	managerID varchar(5) PRIMARY KEY,
    idHotel int,
    fullName varchar(100),
    email varchar(50),
    managerPwd varchar(50),
    phoneNumber char(10),
    FOREIGN KEY(idHotel) references Hotel(idHotel)
);

create table Room(
	roomID int PRIMARY KEY auto_increment,
    idHotel int,
    CustomerID char(5),
    statusRoom Boolean,
    facilities text,
    description_room text,
    
	FOREIGN KEY(idHotel) references Hotel(idHotel),
    FOREIGN KEY(CustomerID) references Customer(CustomerID)
);

create table Booking(
	BookingID int PRIMARY KEY auto_increment, 
    CustomerID char(5),
    roomID int,
    managerID varchar(5),
    checkInDate date,
    checkOutDate date,
    bookingPrice double,
    paymentStatus boolean,
    
	FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY(roomID) REFERENCES Room(roomID),
    FOREIGN KEY(managerID) REFERENCES Manager(managerID)
);

-- ========================================
-- TEST DATA
-- ========================================

-- Hotels
INSERT INTO Hotel VALUES
(1, 'Grand Hotel', '123 Main Street, District 1', '0901234567'),
(2, 'Luxury Resort', '456 Beach Road, District 7', '0912345678');

-- Managers (Phone: 0111111111, Pwd: admin123)
INSERT INTO Manager VALUES
('MGR01', 1, 'Manager One', 'mgr1@hotel.com', 'admin123', '0111111111'),
('MGR02', 2, 'Manager Two', 'mgr2@hotel.com', 'admin123', '0222222222');

-- Customers (Phone: 0123456789, Pwd: 123456)
INSERT INTO Customer VALUES
('10000', 'Test User', 'test@gmail.com', '123456', '0123456789'),
('10001', 'John Doe', 'john@gmail.com', 'password', '0987654321'),
('10002', 'Jane Smith', 'jane@gmail.com', 'jane123', '0909090909');

-- Rooms
INSERT INTO Room (roomID, idHotel, CustomerID, statusRoom, facilities, description_room) VALUES
(1, 1, NULL, TRUE, 'WiFi, AC, TV, Minibar', 'Deluxe Room with city view'),
(2, 1, NULL, TRUE, 'WiFi, AC, TV', 'Standard Room'),
(3, 1, NULL, TRUE, 'WiFi, AC, TV, Minibar, Jacuzzi', 'Suite Room with balcony'),
(4, 2, NULL, TRUE, 'WiFi, AC, TV, Pool Access', 'Beach View Room'),
(5, 2, NULL, TRUE, 'WiFi, AC, TV, Minibar, Kitchen', 'Family Suite'),
(6, 2, NULL, FALSE, 'WiFi, AC', 'Economy Room - Under Maintenance');
