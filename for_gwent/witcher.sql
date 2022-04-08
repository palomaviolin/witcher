CREATE DATABASE gwent;

USE gwent;

CREATE TABLE decks(
cod_deck SMALLINT UNSIGNED,
name VARCHAR(60) NOT NULL,
skill VARCHAR(300) NOT NULL,
PRIMARY KEY(cod_deck)
)ENGINE=InnoDB;

CREATE TABLE cards(
cod_card SMALLINT UNSIGNED,
name VARCHAR(60) NOT NULL,
type VARCHAR(60) NOT NULL,
strength TINYINT UNSIGNED DEFAULT 0 NOT NULL,
cod_deck SMALLINT UNSIGNED,
PRIMARY KEY(cod_card),
FOREIGN KEY(cod_deck) REFERENCES decks(cod_deck) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB;

INSERT INTO decks VALUES
(1, 'Monsters', 'Keep a random unit card after each round.'),
(2, 'Nilfgaard', 'Win any round that ends a tie.'),
(3, 'North', 'Gives an extra card when you win a round.'),
(4, "Scoia'tael", 'Decides who takes the first turn.'),
(5, 'Skellige', 'Two random cards from the graveyard are placed on the battlefield at the start of the third round.'),
(6, 'neutral cards', 'N/A (not a faction per se, but can be used with any of the other decks.)');

INSERT INTO cards VALUES
(1, 'Leshen', 'bow', 10, 1),
(2, 'Nekker', 'sword', 2, 1),
(3, 'Arachas', 'sword', 4, 1),
(4, 'Letho of Gulet', 'sword', 10, 2),
(5, 'Albrich', 'bow', 2, 2),
(6, 'Fringilla Vigo', 'bow', 6, 2),
(7, 'Vernon Roche', 'sword', 10, 3),
(8, 'Sigismund Dijkstra', 'sword', 4, 3),
(9, 'Catapult', 'catapult', 8, 3),
(10, 'Filavandrel aén Fidháil', 'sword/bow', 6, 4),
(11, 'Dol Blathana Archer', 'bow', 4, 4),
(12, 'Havekar Smuggler', 'sword', 5, 4),
(13, 'Berserker', 'sword', 4, 5),
(14, 'Cerys', 'sword', 10, 5),
(15, 'Olaf', 'sword/bow', 12, 5),
(16, 'Clear weather', 'weather', DEFAULT, 6),
(17, 'Biting Frost', 'weather', DEFAULT, 6),
(18, 'Impenetrable Fog', 'weather', DEFAULT, 6),
(19, 'Torrential Rain', 'weather', DEFAULT, 6),
(20, "Commander's Horn", 'special', DEFAULT, 6),
(21, 'Zoltan Chivay', 'sword', 5, 6),
(22, 'Mardroeme', 'special', DEFAULT, 5);

ALTER TABLE cards ADD image LONGBLOB AFTER cod_deck;

SHOW TABLES;



SELECT * FROM decks;

SELECT * FROM cards;

SELECT cod_card, name, image, cod_deck FROM cards ORDER BY cod_deck;

UPDATE cards SET name='Torrential Rain' WHERE cod_card=19;

DELETE FROM decks WHERE cod_deck=7;

DELETE FROM decks WHERE cod_deck=8;

DELETE FROM cards WHERE cod_card=19;

INSERT INTO cards (cod_card, name, type, strength, cod_deck)
VALUES (19, 'Torrential Rain', 'weather', DEFAULT, 6);