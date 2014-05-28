START TRANSACTION;

insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date) values (1, 3, 3, 1, 200, 0, "2014-05-20");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (1, 4, "", 1, 1, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (1, 1, 1);

insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date) values (2, 3, 3, 1, 350, 0, "2014-05-23");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (2, 4, "", 2, 1, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (2, 1, 1);

insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date)  values (3, 3, 3, 1, 1000, 0, "2014-05-26");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (3, 4, "", 3, 1, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (3, 1, 1);

insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date)  values (4, 3, 3, 1, 50, 0, "2014-05-27");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (4, 4, "", 4, 1, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (4, 1, 1);

insert into `diner`.`order` (id, customer_amount, state_id, payment_media_id, total, `change`, billing_date)  values (5, 3, 3, 1, 700, 0, "2014-05-28");
insert into `diner`.`order_detail` (id, amount, `comment`, order_id, product_id, state_id, request_date, preparation_start_date, preparation_end_date, delivery_date ) 
values (5, 4, "", 5, 1, 5, "2014-05-28 20:20", "2014-05-28 20:20","2014-05-28 20:20","2014-05-28 20:20");
insert into `diner`.`order_table` (id, order_id, table_id) values (5, 1, 1);

commit;