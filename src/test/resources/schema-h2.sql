CREATE SCHEMA IF NOT EXISTS TRADE_SYSTEM;

DROP TABLE IF EXISTS `trade_system`.`order_has_goods`;
DROP TABLE IF EXISTS `trade_system`.`user_order`;
DROP TABLE IF EXISTS `trade_system`.`user_has_goods`;
DROP TABLE IF EXISTS `trade_system`.`goods`;
DROP TABLE IF EXISTS `trade_system`.`role_has_user`;
DROP TABLE IF EXISTS `trade_system`.`role`;
DROP TABLE IF EXISTS `trade_system`.`user`;
DROP TABLE IF EXISTS `trade_system`.`status`;

CREATE TABLE `trade_system`.`status`
(
    `status_id`   int(11)     NOT NULL AUTO_INCREMENT,
    `status_name` varchar(45) NOT NULL,
    PRIMARY KEY (`status_id`),
    UNIQUE KEY `status_status_uindex` (`status_name`)
);

CREATE TABLE `trade_system`.`user`
(
    `user_id`  int(11)      NOT NULL AUTO_INCREMENT,
    `login`    varchar(45)  NOT NULL,
    `password` varchar(225) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_login_uindex` (`login`)
);

CREATE TABLE `trade_system`.`role`
(
    `role_id` int(11)     NOT NULL AUTO_INCREMENT,
    `name`    varchar(45) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `role_name_uindex` (`name`)
);

CREATE TABLE `trade_system`.`role_has_user`
(
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `role_has_user___fk_role` FOREIGN KEY (`role_id`)
        REFERENCES `trade_system`.`role` (`role_id`),
    CONSTRAINT `role_has_user___fk_user` FOREIGN KEY (`user_id`)
        REFERENCES `trade_system`.`user` (`user_id`)
);

CREATE TABLE `trade_system`.`goods`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `name`        varchar(35)  NOT NULL,
    `description` varchar(225) NOT NULL,
    `price`       int(11)      NOT NULL,
    `quantity`    int(11)      NOT NULL DEFAULT '0',
    `image_name`  varchar(45),
    PRIMARY KEY (`id`),
    UNIQUE KEY `goods_name_uindex` (`name`)
);

CREATE TABLE `trade_system`.`user_has_goods`
(
    `user_id`  int(11) NOT NULL,
    `goods_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`, `goods_id`),
    CONSTRAINT `user_has_goods___fk_goods` FOREIGN KEY (`goods_id`)
        REFERENCES `trade_system`.`goods` (`id`) ON DELETE CASCADE,
    CONSTRAINT `user_has_goods___fk_user` FOREIGN KEY (`user_id`)
        REFERENCES `trade_system`.`user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `trade_system`.`user_order`
(
    `order_id`  int(11) NOT NULL AUTO_INCREMENT,
    `user_id`   int(11) NOT NULL,
    `status_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    CONSTRAINT `user_order___fk_status` FOREIGN KEY (`status_id`)
        REFERENCES `trade_system`.`status` (`status_id`),
    CONSTRAINT `user_order___fk_user` FOREIGN KEY (`user_id`)
        REFERENCES `trade_system`.`user` (`user_id`)
);

CREATE TABLE `trade_system`.`order_has_goods`
(
    `order_id` int(11) NOT NULL,
    `goods_id` int(11) NOT NULL,
    PRIMARY KEY (`order_id`, `goods_id`),
    CONSTRAINT `order_has_goods___fk_goods` FOREIGN KEY (`goods_id`)
        REFERENCES `trade_system`.`goods` (`id`) ON DELETE CASCADE,
    CONSTRAINT `order_has_goods___fk_order` FOREIGN KEY (`order_id`)
        REFERENCES `trade_system`.`user_order` (`order_id`) ON DELETE CASCADE
);
