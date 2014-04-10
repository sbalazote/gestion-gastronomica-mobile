START TRANSACTION;

CREATE SCHEMA `diner`;

CREATE TABLE `diner`.`waiter` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`table_state` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`table` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`state_id` int(2) NOT NULL,
	`waiter_id` int(8) DEFAULT NULL,
	`active` bit(1) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_table_table_state_idx` (`state_id`),
	CONSTRAINT `fk_table_table_state` FOREIGN KEY (`state_id`) REFERENCES `table_state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_table_waiter_idx` (`waiter_id`),
	CONSTRAINT `fk_table_waiter` FOREIGN KEY (`waiter_id`) REFERENCES `waiter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`customer_amount` int(2) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`category` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(100) NOT NULL,
	`active` bit(1) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`subcategory` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(100) NOT NULL,
	`active` bit(1) NOT NULL,
	`category_id` int(8) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_subcategory_category_idx` (`category_id`),
	CONSTRAINT `fk_subcategory_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`product` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(100) NOT NULL,
	`price` decimal(6,2) NOT NULL,
	`celiac_allowed` bit(1) NOT NULL,
	`active` bit(1) NOT NULL,
	`subcategory_id` int(8) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_product_subcategory_idx` (`subcategory_id`),
	CONSTRAINT `fk_product_subcategory` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_detail_state` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_detail` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`amount` int(4) NOT NULL,
	`comment` varchar(100) NOT NULL,
	`order_id` int(8) NOT NULL,
	`product_id` int(8) NOT NULL,
	`state_id` int(2) NOT NULL,
	`request_date` datetime NOT NULL,
	`preparation_date` datetime NOT NULL,
	`prepared_date` datetime NOT NULL,
	`delivered_date` datetime NOT NULL,
	`closed_date` datetime NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_order_detail_order_idx` (`order_id`),
	CONSTRAINT `fk_order_detail_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_order_detail_product_idx` (`product_id`),
	CONSTRAINT `fk_order_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_order_detail_order_detail_state_idx` (`state_id`),
	CONSTRAINT `fk_order_detail_order_detail_state` FOREIGN KEY (`state_id`) REFERENCES `order_detail_state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_table` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`order_id` int(8) NOT NULL,
	`table_id` int(8) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_order_table_order_idx` (`order_id`),
	CONSTRAINT `fk_order_table_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_order_table_table_idx` (`table_id`),
	CONSTRAINT `fk_order_table_table` FOREIGN KEY (`table_id`) REFERENCES `table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;