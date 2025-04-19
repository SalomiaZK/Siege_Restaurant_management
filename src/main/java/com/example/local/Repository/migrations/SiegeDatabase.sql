create database "local";

create table best_sales(
    best_sales_id varchar(20) primary key ,
    best_sales_date timestamp
);


create  table sales_element(
    sales_element_id varchar(20) primary key ,
    sale_point text,
    sold_quantity int,
    total_amount float,
    best_sales_id varchar(20)
);


create table dish(
                     dish_id varchar(20) primary key  not null ,
                     dish_name varchar(100),
                     dish_price int
);



create table ingredient(
                           ingredient_id varchar(20) primary key not null ,
                           ingredient_name varchar(100),
                           ingredient_modification_date TIMESTAMP,
                           ingredient_unity char(1) check ( ingredient_unity in ('U', 'G', 'L') )

);

create table ing_price(
                          price_id varchar(20) primary key not null ,
                          price_date TIMESTAMP,
                          unit_price int,
                          ingredient_id varchar(20),
                          CONSTRAINT fk_ingredient_price foreign key (ingredient_id) references ingredient(ingredient_id)

);

create table dish_to_ingredient(
                                   ingredient_id varchar(20),
                                   dish_id varchar(20),
                                   CONSTRAINT fk_dish foreign key (dish_id) references dish(dish_id),
                                   constraint  fk_ingredient foreign key  (ingredient_id) references ingredient(ingredient_id),
                                   ingredient_quantity float,
                                   PRIMARY KEY (ingredient_id,dish_id)

);
-- a view to get ingredients name, id , unity, price,
create VIEW get_ingredients as
SELECT DISTINCT ON (ingredient.ingredient_id)
        ingredient.ingredient_id,
        ingredient_name,
        ingredient_unity,
        price_date,
        price as unit_price
        FROM ingredient
        JOIN ing_price ON ing_price.ingredient_id = ingredient.ingredient_id
        ORDER BY ingredient.ingredient_id, price_date DESC;



create table transaction(
                            transaction_id varchar(20) primary key not null ,
                            transaction_date timestamp,
                            transaction_type varchar(20) check ( transaction_type in ('ADDITION', 'SUBSTRACTION')),
                            transaction_used_quantity float,
                            transaction_unity char(1) check ( transaction_unity in ('U', 'G', 'L') ),
                            ingredient_id varchar(20),
                            CONSTRAINT fk_transaction foreign key (ingredient_id) references ingredient(ingredient_id)
);


create table  "order"(
                         order_id varchar(20) primary key ,
                         order_date timestamp
);

create table order_to_dish(
                              ordered_id varchar(10) primary key  , -- ordered dish
                              order_id varchar(20),
                              dish_id varchar(20),
                              order_quantity integer,
                              constraint fk_dish_dish foreign key (dish_id) references dish(dish_id),
                              constraint fk_order_order foreign key (order_id) references "order"(order_id)
);

create table status(
                       status_begining_date timestamp,
                       status int check ( status >=1 and status<=5 ),
                       ordered_id varchar(20),
                       primary key (ordered_id, status) ,
                       constraint fk_status_ordered_Dish foreign key (ordered_id) references order_to_dish(ordered_id)
);