create database Plant;

use Plant;

===========

create table Supplier(
                         Supplier_id varchar(50) primary key,
                         Supplier_name varchar(50),
                         Address varchar(50),
                         Contact int(10),
                         Quantity int(10),
                         Price decimal(10,2),
                         Product varchar(50),
                         Date date,
                         NIC varchar(15)
);

===========

create table User(
                     User_id varchar(50) primary key,
                     User_name varchar(50),
                     Date date,
                     Password varchar(8)
);

=============

create table Employee(
                         Emplloyee_id varchar(50) primary key,
                         Employee_name varchar(50),
                         Address varchar(50),
                         contact varchar(10),
                         Date date,
                         Salary decimal(10,2),
                         Working_hours varchar(10),
                         Attendance varchar(50),
                         Position varchar(50),
                         User_id varchar(50),
                         foreign key(User_id) references User(User_id) On update cascade on delete cascade
);

===============

create table Customer(
                         Customer_id varchar(50) primary key,
                         Customer_name varchar(50),
                         Contact int(10),
                         Address varchar(50),
                         Nic varchar(15),
                         Date date
);

==============

create table Orders(
                       Order_id varchar(50) primary key,
                       Date date,
                       Price decimal(10,2),
                       Customer_id varchar(50),
                       User_id varchar(50),
                       foreign key(Customer_id) references Customer(Customer_id) On update cascade on delete cascade,
                       foreign key(User_id) references User(User_id) On update cascade on delete cascade
);

=================

create table Items(
                      Item_id varchar(50) primary key,
                      Item_name varchar(50),
                      Quantity int(50),
                      Price decimal(10,2),
                      Description varchar(50),
                      Date date
);

==============

create table Order_Item(
                           Order_id varchar(50),
                           Item_id varchar(50),
                           Quantity int(50),
                           Price decimal(10,2),
                           foreign key(Order_id) references Orders(Order_id) On update cascade on delete cascade,
                           foreign key(Item_id) references Items(Item_id) On update cascade on delete cascade
);

=============

create table Item_Supplier(
                              Supplier_id varchar(50),
                              Item_id varchar(50),
                              Product_name varchar(50),
                              Price decimal(10,2),
                              Quantity int(50),
                              Date date,
                              foreign key(Supplier_id) references Supplier(Supplier_id) On update cascade on delete cascade,
                              foreign key(Item_id) references Items(Item_id) On update cascade on delete cascade
);
=====================

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