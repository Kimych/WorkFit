CREATE TABLE IF NOT EXISTS `workfit`.`day_type` (
  `DAY_TYPE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `DATE_DAY` TIMESTAMP NOT NULL,
  `TYPE_DAY` INT(11) NULL DEFAULT NULL,
  `DESCRITION` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`DAY_TYPE_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci