SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

ALTER TABLE `workfit`.`authorization` 
DROP FOREIGN KEY `fk_authorization_worker1`;

ALTER TABLE `workfit`.`authorization_to_role` 
DROP FOREIGN KEY `fk_autorization_to_role_authorization1`,
DROP FOREIGN KEY `fk_autorization_to_role_role1`;

ALTER TABLE `workfit`.`authorization` 
DROP COLUMN `WORKER_ID`,
ADD COLUMN `WORKER_ID` INT(11) NULL DEFAULT NULL AFTER `EMAIL`,
ADD INDEX `fk_authorization_worker1_idx` (`WORKER_ID` ASC),
DROP INDEX `fk_authorization_worker1_idx` ;

ALTER TABLE `workfit`.`authorization_to_role` 
DROP COLUMN `ROLE_ID`,
DROP COLUMN `AUTHORIZATION_ID`,
ADD COLUMN `AUTHORIZATION_ID` INT(11) NOT NULL FIRST,
ADD COLUMN `ROLE_ID` INT(11) NOT NULL AFTER `AUTHORIZATION_ID`,
ADD INDEX `fk_autorization_to_role_authorization1_idx` (`AUTHORIZATION_ID` ASC),
ADD INDEX `fk_autorization_to_role_role1_idx` (`ROLE_ID` ASC),
ADD PRIMARY KEY (`AUTHORIZATION_ID`, `ROLE_ID`),
DROP INDEX `fk_autorization_to_role_role1_idx` ,
DROP INDEX `fk_autorization_to_role_authorization1_idx` ;

ALTER TABLE `workfit`.`authorization` 
ADD CONSTRAINT `fk_authorization_worker1`
  FOREIGN KEY (`WORKER_ID`)
  REFERENCES `workfit`.`worker` (`WORKER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `workfit`.`authorization_to_role` 
ADD CONSTRAINT `fk_autorization_to_role_authorization1`
  FOREIGN KEY (`AUTHORIZATION_ID`)
  REFERENCES `workfit`.`authorization` (`AUTHORIZATION_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_autorization_to_role_role1`
  FOREIGN KEY (`ROLE_ID`)
  REFERENCES `workfit`.`role` (`ROLE_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
