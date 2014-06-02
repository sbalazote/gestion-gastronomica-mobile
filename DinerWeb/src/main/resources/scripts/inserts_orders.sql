START TRANSACTION;

insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date) values (1, 3, 3, 1, 200, 0, "2014-05-20");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (1, 2, "", 1, 7, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (2, 1, "", 1, 3, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (3, 3, "", 1, 2, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (4, 7, "", 1, 33, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (1, 1, 1);


/*-Insertando Orden numero 2*/
insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date) values (2, 3, 3, 1, 350, 0, "2014-05-23");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (5, 2, "", 2, 3, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (6, 1, "", 2, 55, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (7, 3, "", 2, 34, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (8, 7, "", 2, 2, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (2, 1, 1);

/*-Insertando Orden numero 3*/
insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date)  values (3, 3, 3, 1, 1000, 0, "2014-05-26");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (9, 5, "", 3, 22, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (10, 2, "", 3, 31, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (11, 11, "", 3, 77, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (12, 12, "", 3, 33, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_table` (id, order_id, table_id) values (3, 1, 1);

/*-Insertando Orden numero 4*/
insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date)  values (4, 3, 3, 1, 50, 0, "2014-05-27");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (13, 3, "", 4, 25, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (14, 4, "", 4, 45, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (15, 6, "", 4, 12, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (16, 8, "", 4, 33, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (17, 2, "", 4, 21, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (18, 6, "", 4, 1, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (19, 8, "", 4, 43, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (4, 1, 1);

/*-Insertando Orden numero 5*/
insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date)  values (5, 3, 3, 1, 700, 0, "2014-05-28");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (20, 12, "", 5, 23, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (21, 1, "", 5, 32, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (22, 4, "", 5, 33, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (23, 2, "", 5, 77, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (24, 7, "", 5, 63, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (25, 2, "", 5, 88, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");

insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (26, 1, "", 5, 11, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (5, 1, 1);

commit;