create database "local";

create table best_sales(
    best_sales_id varchar(20) primary key ,
    best_sales_date timestamp,
);


create  table sales_element(
    sales_element_id varchar(20) primary key ,
    sale_point text,
    sold_quantity int,
    total_amount float,
    best_sales_id varchar(20),
);