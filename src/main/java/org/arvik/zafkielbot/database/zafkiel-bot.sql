CREATE DATABASE IF NOT EXISTS `zafkiel-bot`;
USE `zafkiel-bot`;

DROP TABLE IF EXISTS `usermusic`;
CREATE TABLE IF NOT EXISTS `usermusic`(
    `userId` varchar (24) NOT NULL,
    `music` varchar(255) NOT NULL,
    `playtime` varchar (11) DEFAULT NULL,
    PRIMARY KEY (`userId`)
);