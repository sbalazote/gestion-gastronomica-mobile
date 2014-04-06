START TRANSACTION;

CREATE SCHEMA `diner`;

CREATE TABLE `diner`.`table` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(100) NOT NULL,
	`active` bit(1) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`state` varchar(50) NOT NULL,
	`date` datetime NOT NULL,
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
	KEY `fk_product_category_idx` (`category_id`),
	CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`product` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(100) NOT NULL,
	`price` decimal(6,2) NOT NULL,
	`active` bit(1) NOT NULL,
	`subcategory_id` int(8) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_product_subcategory_idx` (`subcategory_id`),
	CONSTRAINT `fk_product_subcategory` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_detail` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`comment` varchar(100) NOT NULL,
	`amount` int(4) NOT NULL,
	`order_id` int(8) NOT NULL,
	`product_id` int(8) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_order_detail_order_idx` (`order_id`),
	CONSTRAINT `fk_order_detail_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_order_detail_product_idx` (`product_id`),
	CONSTRAINT `fk_order_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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