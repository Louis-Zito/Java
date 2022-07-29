DROP DATABASE IF EXISTS GuessTheNumberDB;
CREATE DATABASE GuessTheNumberDB;
USE GuessTheNumberDB;

CREATE TABLE game (
	gameID INT PRIMARY KEY AUTO_INCREMENT,
    -- not calculations for answers = char
    answer char(4),
    isFinished BOOLEAN DEFAULT false
);

CREATE TABLE round (
	roundID INT PRIMARY KEY AUTO_INCREMENT,
    gameID INT NOT NULL,
    guessTime TIMESTAMP NOT NULL DEFAULT NOW(),
    guess CHAR(4),
    result CHAR(7),
    FOREIGN KEY fk_gameID (gameID) REFERENCES game(gameID)
    );
