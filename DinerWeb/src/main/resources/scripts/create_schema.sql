START TRANSACTION;

CREATE SCHEMA `diner`;

CREATE TABLE `diner`.`user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`table_state` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`table` (
  `id` int(8) NOT NULL,
  `state_id` int(8) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `locked` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_table_table_state_idx` (`state_id`),
  KEY `fk_table_user_idx` (`user_id`),
  CONSTRAINT `fk_table_table_state` FOREIGN KEY (`state_id`) REFERENCES `table_state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`table_union` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `original_table_id` int(11) NOT NULL,
  `attached_table_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_table_union_table1_idx` (`original_table_id`),
  KEY `fk_table_union_table2_idx` (`attached_table_id`),
  CONSTRAINT `fk_table_union_table1` FOREIGN KEY (`original_table_id`) REFERENCES `table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_union_table2` FOREIGN KEY (`attached_table_id`) REFERENCES `table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

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
	`estimated_time` int(8),
	`celiac_allowed` bit(1) NOT NULL,
	`kitchen` bit(1) NOT NULL,
	`active` bit(1) NOT NULL,
	`subcategory_id` int(8) NOT NULL,
	`stock` bit(1) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `fk_product_subcategory_idx` (`subcategory_id`),
	CONSTRAINT `fk_product_subcategory` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`payment_media` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `percentage` DOUBLE NOT NULL,
  `starting_date` date NOT NULL,
  `expiration_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_state` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`customer_amount` int(8) NOT NULL,
	`state_id` int(8) NOT NULL,
	`payment_media_id` int(8),
	`total` decimal(6,2) DEFAULT NULL,
	`change` decimal(6,2) DEFAULT NULL,
	`billing_date` date DEFAULT NULL,
	`coupon_id` int(8),
	PRIMARY KEY (`id`),
	KEY `fk_order_order_state_idx` (`state_id`),
	CONSTRAINT `fk_order_order_state` FOREIGN KEY (`state_id`) REFERENCES `order_state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_order_payment_media_idx` (`payment_media_id`),
	CONSTRAINT `fk_order_payment_media` FOREIGN KEY (`payment_media_id`) REFERENCES `payment_media` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `fk_order_coupon_idx` (`coupon_id`),
	CONSTRAINT `fk_order_coupon` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_detail_state` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`description` varchar(30) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`order_detail` (
	`id` int(8) NOT NULL AUTO_INCREMENT,
	`amount` int(8) NOT NULL,
	`comment` varchar(100) DEFAULT NULL,
	`order_id` int(8) NOT NULL,
	`product_id` int(8) NOT NULL,
	`state_id` int(8) NOT NULL,
	`request_date` datetime DEFAULT NULL,
	`preparation_start_date` datetime DEFAULT NULL,
	`preparation_end_date` datetime DEFAULT NULL,
	`delivery_date` datetime DEFAULT NULL,
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

CREATE TABLE `diner`.`parameter` (
	`id` INT NOT NULL AUTO_INCREMENT,
 	`restaurant_name` VARCHAR(50) NULL,
 	`dinner_service_price` DOUBLE NULL,
  	`dinner_service_active` BIT NULL,
	`address` VARCHAR(50) NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`device` (
  `id` VARCHAR(20) NOT NULL,
  `user_id` INT NULL,
  `registration_id` VARCHAR(200) NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mobile_user_idx` (`user_id`),
  CONSTRAINT `fk_mobile_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `diner`.`user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_user_idx` (`user_id`),
  KEY `fk_user_role_role_idx` (`role_id`),
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;