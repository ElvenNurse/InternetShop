CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(14,4) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('Galaxy S10', '999.99');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('Galaxy S11', '1199.99');
INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('Galaxy S12', '1499.99');
