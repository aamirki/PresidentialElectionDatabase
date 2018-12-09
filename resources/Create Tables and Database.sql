-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema presidentialElection
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `presidentialElection` ;

-- -----------------------------------------------------
-- Schema presidentialElection
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `presidentialElection` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `presidentialElection` ;

-- -----------------------------------------------------
-- Table `presidentialElection`.`State`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `presidentialElection`.`State` ;

CREATE TABLE IF NOT EXISTS `presidentialElection`.`State` (
  `state_id` INT NOT NULL,
  `state_name` VARCHAR(30) NOT NULL,
  `region` ENUM('SOUTH', 'NEW ENGLAND', 'MID-ATLANTIC', 'MIDWEST', 'GREAT PLAINS', 'WEST', 'OTHER') NULL,
  PRIMARY KEY (`state_id`),
  UNIQUE INDEX `state_name_UNIQUE` (`state_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `presidentialElection`.`County`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `presidentialElection`.`County` ;

CREATE TABLE IF NOT EXISTS `presidentialElection`.`County` (
  `county_id` INT NOT NULL,
  `state_id` INT NOT NULL,
  `county_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`county_id`),
  UNIQUE INDEX `county_id_UNIQUE` (`county_id` ASC),
  CONSTRAINT `fk_table1_State`
    FOREIGN KEY (`state_id`)
    REFERENCES `presidentialElection`.`State` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `presidentialElection`.`State Election Results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `presidentialElection`.`State Election Results` ;

CREATE TABLE IF NOT EXISTS `presidentialElection`.`State Election Results` (
  `election_id` INT NOT NULL AUTO_INCREMENT,
  `election_year` INT NOT NULL,
  `state_id` INT NOT NULL,
  `republican_votes` INT NOT NULL,
  `democratic_votes` INT NOT NULL,
  `third_party_votes` INT NULL,
  PRIMARY KEY (`election_id`),
  UNIQUE INDEX `election_id_UNIQUE` (`election_id` ASC),
  CONSTRAINT `fk_State Election Results_State2`
    FOREIGN KEY (`state_id`)
    REFERENCES `presidentialElection`.`State` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `presidentialElection`.`State Election Results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `presidentialElection`.`State Election Results` ;

CREATE TABLE IF NOT EXISTS `presidentialElection`.`State Election Results` (
  `election_id` INT NOT NULL AUTO_INCREMENT,
  `election_year` INT NOT NULL,
  `state_id` INT NOT NULL,
  `republican_votes` INT NOT NULL,
  `democratic_votes` INT NOT NULL,
  `third_party_votes` INT NULL,
  PRIMARY KEY (`election_id`),
  UNIQUE INDEX `election_id_UNIQUE` (`election_id` ASC),
  CONSTRAINT `fk_State Election Results_State2`
    FOREIGN KEY (`state_id`)
    REFERENCES `presidentialElection`.`State` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `presidentialElection`.`County Election Results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `presidentialElection`.`County Election Results` ;

CREATE TABLE IF NOT EXISTS `presidentialElection`.`County Election Results` (
  `election_id` INT NOT NULL AUTO_INCREMENT,
  `election_year` INT NOT NULL,
  `county_id` INT NOT NULL,
  `republican_votes` INT NOT NULL,
  `democratic_votes` INT NOT NULL,
  `third_party_votes` INT NULL,
  PRIMARY KEY (`election_id`),
  INDEX `fk_County Election Results_County1_idx` (`county_id` ASC),
  UNIQUE INDEX `election_id_UNIQUE` (`election_id` ASC),
  CONSTRAINT `fk_County Election Results_County1`
    FOREIGN KEY (`county_id`)
    REFERENCES `presidentialElection`.`County` (`county_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `presidentialElection`.`State Demographics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `presidentialElection`.`State Demographics` ;

CREATE TABLE IF NOT EXISTS `presidentialElection`.`State Demographics` (
  `demographic_id` INT NOT NULL AUTO_INCREMENT,
  `census_year` INT NOT NULL,
  `state_id` INT NOT NULL,
  `white_percentage` FLOAT NULL,
  `black_percentage` FLOAT NULL,
  `asian_percentage` FLOAT NULL,
  `native_american_percentage` FLOAT NULL,
  `other_race_percentage` FLOAT NULL,
  `hispanic_percentage` FLOAT NULL,
  `non_hispanic_percentage` FLOAT NULL,
  INDEX `fk_State Demographics_State1_idx` (`state_id` ASC),
  PRIMARY KEY (`demographic_id`),
  UNIQUE INDEX `demographic_id_UNIQUE` (`demographic_id` ASC),
  CONSTRAINT `fk_State Demographics_State1`
    FOREIGN KEY (`state_id`)
    REFERENCES `presidentialElection`.`State` (`state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
