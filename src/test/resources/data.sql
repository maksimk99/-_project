INSERT INTO `trade_system`.`user` (`login`, `password`)
VALUES ('maksimk99', '$2a$10$kOS.xs2/E5lAusXW/oOo1eiqUz4lgAsX23M28UfN7cK5eC8bjF6Em');

INSERT INTO `trade_system`.`role` (`name`) VALUES ('USER');
INSERT INTO `trade_system`.`role` (`name`) VALUES ('ADMIN');

INSERT INTO `trade_system`.`role_has_user` (`role_id`, `user_id`) VALUES (1, 1);
INSERT INTO `trade_system`.`role_has_user` (`role_id`, `user_id`) VALUES (2, 1);

INSERT INTO `trade_system`.`status` (`status_name`) VALUES ('in progress');
INSERT INTO `trade_system`.`status` (`status_name`) VALUES ('sent to post office');
INSERT INTO `trade_system`.`status` (`status_name`) VALUES ('delivered by mail');
INSERT INTO `trade_system`.`status` (`status_name`) VALUES ('delivered by courier');

INSERT INTO `trade_system`.`goods` (`name`, `description`, `quantity`, `price`) VALUES ('chair', 'good chair', 3, 32);
INSERT INTO `trade_system`.`goods` (`name`, `description`, `quantity`, `price`) VALUES ('bicycle', 'realy fast', 4, 360);
INSERT INTO `trade_system`.`goods` (`name`, `description`, `quantity`, `price`) VALUES ('jeans', 'good quality', 2, 55);

INSERT INTO `trade_system`.`user_has_goods` (`goods_id`, `user_id`) VALUES (1, 1);
INSERT INTO `trade_system`.`user_has_goods` (`goods_id`, `user_id`) VALUES (3, 1);

INSERT INTO `trade_system`.`user_order` (`status_id`, `user_id`) VALUES (1, 1);
INSERT INTO `trade_system`.`user_order` (`status_id`, `user_id`) VALUES (2, 1);

INSERT INTO `trade_system`.`order_has_goods` (`order_id`, `goods_id`) VALUES (1, 1);
INSERT INTO `trade_system`.`order_has_goods` (`order_id`, `goods_id`) VALUES (1, 2);
INSERT INTO `trade_system`.`order_has_goods` (`order_id`, `goods_id`) VALUES (2, 1);
INSERT INTO `trade_system`.`order_has_goods` (`order_id`, `goods_id`) VALUES (2, 2);
INSERT INTO `trade_system`.`order_has_goods` (`order_id`, `goods_id`) VALUES (2, 3);
