CREATE TABLE IF NOT EXISTS `Profile` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `lastName` VARCHAR(100) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email` (`email` ASC)
);

CREATE TABLE IF NOT EXISTS `Project` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(100) NOT NULL,
     `description` TEXT NOT NULL,
     `start_date` DATE NOT NULL,
     `end_date` DATE NOT NULL,
     `profile_id` INT NOT NULL,
     `budget` DOUBLE NOT NULL,
     `total_est_hours` INT NOT NULL,
     `actual_price` DOUBLE NOT NULL,
     `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
     PRIMARY KEY (`id`),
     FOREIGN KEY (`profile_id`)
     REFERENCES `Profile` (`id`)
);

CREATE TABLE IF NOT EXISTS `SubProject` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `assigned_to` VARCHAR(255) NOT NULL,
    `project_id` INT NOT NULL,
    `actual_price` DOUBLE NOT NULL,
    `budget` DOUBLE NOT NULL,
    `total_est_hours` INT NOT NULL,
    `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`project_id`)
    REFERENCES `Project` (`id`)
);

CREATE TABLE IF NOT EXISTS `Task` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `assigned_to` VARCHAR(255) NOT NULL,
    `sub_project_id` INT NOT NULL,
    `price` DOUBLE NOT NULL DEFAULT 0,
    `total_est_hours` INT NOT NULL,
    `location` VARCHAR(255) NOT NULL,
    `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`sub_project_id`)
    REFERENCES `SubProject` (`id`)

);


