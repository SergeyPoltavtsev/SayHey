SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mymessanger` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mymessanger` ;

-- -----------------------------------------------------
-- Table `mymessanger`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mymessanger`.`users` (
  `login` VARCHAR(15) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(25),
  `email` VARCHAR(15),
  PRIMARY KEY (`login`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- -----------------------------------------------------
-- Table `mymessanger`.`messages`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mymessanger`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(255) NOT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT now(),
  `from_users_login` VARCHAR(15) NOT NULL,
  `to_users_login` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_messages_users1` (`from_users_login` ASC) ,
  INDEX `fk_messages_users2` (`to_users_login` ASC) ,
  CONSTRAINT `fk_messages_users1`
    FOREIGN KEY (`from_users_login` )
    REFERENCES `mymessanger`.`users` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_users2`
    FOREIGN KEY (`to_users_login` )
    REFERENCES `mymessanger`.`users` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- -----------------------------------------------------
-- Table `mymessanger`.`friends`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mymessanger`.`friends` (
  `user_login` VARCHAR(15) NOT NULL,
  `friend_login` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`user_login`,`friend_login`),
  INDEX `fk_friends_users1` (`user_login` ASC) ,
  INDEX `fk_friends_users2` (`friend_login` ASC) ,
  CONSTRAINT `fk_friends_users1`
    FOREIGN KEY (`user_login` )
    REFERENCES `mymessanger`.`users` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_friends_users2`
    FOREIGN KEY (`friend_login` )
    REFERENCES `mymessanger`.`users` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Table `mymessanger`.`groupuser`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mymessanger`.`groupuser` (
  `name` VARCHAR(20) NOT NULL ,
  `users_login` VARCHAR(15) NOT NULL ,
  PRIMARY KEY (`name`,`users_login`) ,
  INDEX `fk_groupuser_users` (`users_login` ASC) ,
  CONSTRAINT `fk_groupuser_users`
    FOREIGN KEY (`users_login` )
    REFERENCES `mymessanger`.`users` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
