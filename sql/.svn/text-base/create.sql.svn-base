SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

CREATE  TABLE IF NOT EXISTS `users` (
  `userID` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NOT NULL ,
  `passwordHash` VARCHAR(45) NOT NULL ,
  `passwordSalt` VARCHAR(45) NOT NULL ,
  `isAdmin` TINYINT(1) NOT NULL DEFAULT FALSE ,
  `isBanned` TINYINT(1) NOT NULL DEFAULT FALSE ,
  `lastLoginTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`userID`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quizzes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quizzes` ;

CREATE  TABLE IF NOT EXISTS `quizzes` (
  `quizID` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(45) NOT NULL ,
  `description` LONGTEXT NULL ,
  `creatorID` INT NULL ,
  `isRandomOrder` TINYINT(1) NOT NULL ,
  `isOnePageFormat` TINYINT(1) NOT NULL ,
  `isImmediateCorrection` TINYINT(1) NOT NULL ,
  `isPracticeModeEnabled` TINYINT(1) NOT NULL ,
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `isLive` TINYINT(1) NOT NULL DEFAULT false ,
  PRIMARY KEY (`quizID`) ,
  INDEX `quizzes_users_FOREIGN` (`creatorID` ASC) ,
  CONSTRAINT `quizzes_users_FOREIGN`
    FOREIGN KEY (`creatorID` )
    REFERENCES `users` (`userID` )
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quizResults`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quizResults` ;

CREATE  TABLE IF NOT EXISTS `quizResults` (
  `quizResultID` INT NOT NULL AUTO_INCREMENT ,
  `userID` INT NOT NULL ,
  `quizID` INT NOT NULL ,
  `score` INT NOT NULL ,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `elapsedTime` TIME NOT NULL ,
  `percentCorrect` FLOAT NOT NULL ,
  `isPractice` TINYINT(1) NOT NULL ,
  `isOutOfDate` TINYINT(1) NOT NULL DEFAULT false ,
  INDEX `users_FOREIGN` (`userID` ASC) ,
  INDEX `quizzes_FOREIGN` (`quizID` ASC) ,
  PRIMARY KEY (`quizResultID`) ,
  CONSTRAINT `users_FOREIGN`
    FOREIGN KEY (`userID` )
    REFERENCES `users` (`userID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `quizzes_FOREIGN`
    FOREIGN KEY (`quizID` )
    REFERENCES `quizzes` (`quizID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `announcements`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `announcements` ;

CREATE  TABLE IF NOT EXISTS `announcements` (
  `announcementID` INT NOT NULL AUTO_INCREMENT ,
  `title` TEXT NOT NULL ,
  `content` TEXT NOT NULL ,
  `lastEditorID` INT NULL ,
  `lastEditTime` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`announcementID`) ,
  INDEX `announcements_users_FOREIGN` (`lastEditorID` ASC) ,
  CONSTRAINT `announcements_users_FOREIGN`
    FOREIGN KEY (`lastEditorID` )
    REFERENCES `users` (`userID` )
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `friends`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `friends` ;

CREATE  TABLE IF NOT EXISTS `friends` (
  `x` INT NOT NULL ,
  `y` INT NOT NULL ,
  PRIMARY KEY (`x`, `y`) ,
  INDEX `friends.x_users_FOREIGN` (`x` ASC) ,
  INDEX `friends.y_users_FOREIGN` (`y` ASC) ,
  CONSTRAINT `friends.x_users_FOREIGN`
    FOREIGN KEY (`x` )
    REFERENCES `users` (`userID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `friends.y_users_FOREIGN`
    FOREIGN KEY (`y` )
    REFERENCES `users` (`userID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `messages` ;

CREATE  TABLE IF NOT EXISTS `messages` (
  `messageID` INT NOT NULL AUTO_INCREMENT ,
  `fromUserID` INT NOT NULL ,
  `toUserID` INT NOT NULL ,
  `isRead` TINYINT(1) NOT NULL DEFAULT false ,
  `sentTimestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `messageType` INT NOT NULL ,
  `messageContent` LONGTEXT NOT NULL ,
  PRIMARY KEY (`messageID`) ,
  INDEX `messages.fromUser_users_FOREIGN` (`fromUserID` ASC) ,
  INDEX `messages.toUser_users_FOREIGN` (`toUserID` ASC) ,
  CONSTRAINT `messages.fromUser_users_FOREIGN`
    FOREIGN KEY (`fromUserID` )
    REFERENCES `users` (`userID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `messages.toUser_users_FOREIGN`
    FOREIGN KEY (`toUserID` )
    REFERENCES `users` (`userID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `categories` ;

CREATE  TABLE IF NOT EXISTS `categories` (
  `categoryID` INT NOT NULL AUTO_INCREMENT ,
  `title` TEXT NOT NULL ,
  `description` TEXT NULL ,
  PRIMARY KEY (`categoryID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `achievementTypes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `achievementTypes` ;

CREATE  TABLE IF NOT EXISTS `achievementTypes` (
  `achievementTypeID` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `description` TEXT NOT NULL ,
  `imageIcon` TEXT NOT NULL ,
  PRIMARY KEY (`achievementTypeID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `userAchievements`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `userAchievements` ;

CREATE  TABLE IF NOT EXISTS `userAchievements` (
  `userID` INT NOT NULL ,
  `achievementTypeID` INT NOT NULL ,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  INDEX `userAchievements_users_FOREIGN` (`userID` ASC) ,
  PRIMARY KEY (`userID`, `achievementTypeID`) ,
  INDEX `userAchievements_achievementTypes_FOREIGN` (`achievementTypeID` ASC) ,
  CONSTRAINT `userAchievements_users_FOREIGN`
    FOREIGN KEY (`userID` )
    REFERENCES `users` (`userID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `userAchievements_achievementTypes_FOREIGN`
    FOREIGN KEY (`achievementTypeID` )
    REFERENCES `achievementTypes` (`achievementTypeID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quizQuestions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quizQuestions` ;

CREATE  TABLE IF NOT EXISTS `quizQuestions` (
  `quizQuestionID` INT NOT NULL AUTO_INCREMENT ,
  `quizID` INT NOT NULL ,
  `questionNumber` INT NOT NULL ,
  `questionObject` BLOB NOT NULL ,
  INDEX `quizQuestions_quizzes_FOREIGN` (`quizID` ASC) ,
  PRIMARY KEY (`quizQuestionID`) ,
  CONSTRAINT `quizQuestions_quizzes_FOREIGN`
    FOREIGN KEY (`quizID` )
    REFERENCES `quizzes` (`quizID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quizCategorization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quizCategorization` ;

CREATE  TABLE IF NOT EXISTS `quizCategorization` (
  `quizID` INT NOT NULL ,
  `categoryID` INT NOT NULL ,
  INDEX `quizCategorization_quizzes_FOREIGN` (`quizID` ASC) ,
  INDEX `quizCategorization_categories_FOREIGN` (`categoryID` ASC) ,
  PRIMARY KEY (`quizID`, `categoryID`) ,
  CONSTRAINT `quizCategorization_quizzes_FOREIGN`
    FOREIGN KEY (`quizID` )
    REFERENCES `quizzes` (`quizID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `quizCategorization_categories_FOREIGN`
    FOREIGN KEY (`categoryID` )
    REFERENCES `categories` (`categoryID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
