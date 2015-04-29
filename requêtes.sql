--
select table_name from user_tables;

--Display
set linesize 250
column email format a33
column dateDeNaissance format a15
column prenom format a10
column nom format a12
column pseudo format a10
--column nbPartiesJouees format a10

--Frequently Used Commands
drop table joueurs;
select * from joueurs;
UPDATE joueurs SET nbPartiesJouees=42 WHERE pseudo='nicrrkname2';

--DONE
--Joueurs : {pseudo {pk}, nom, prénom, dateDeNaissance, email, nbPartiesJouees}
CREATE TABLE Joueurs (
	pseudo VARCHAR(30),
	nom VARCHAR(50) NOT NULL,
	prenom VARCHAR(50) NOT NULL,
	dateDeNaissance DATE NOT NULL, --DATE - format YYYY-MM-DD
	email VARCHAR(80) NOT NULL,
	nbPartiesJouees INT DEFAULT 0,
	PRIMARY KEY (pseudo),
	CHECK (
		REGEXP_LIKE (pseudo,'^[A-Za-z0-9]+[A-Za-z0-9._%+-]')
		AND (REGEXP_LIKE (nom,'^[A-Za-z][A-Za-z]*$'))
		AND (REGEXP_LIKE (prenom,'^[A-Za-z][A-Za-z]*$'))
		AND (REGEXP_LIKE (email,'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'))
		AND (nbPartiesJouees >= 0)
	)
);
--!! les accents ne sont pas autorisés ! 
--Insertions valides
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('medjo', 'felix', 'John', to_date('1992-07-03', 'YYYY-MM-DD'), 'felix@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('ninja58', 'marc', 'Kyle', to_date('1993-01-12', 'YYYY-MM-DD'), 'marc@gmail.com', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('aigle78', 'jean', 'Obama', to_date('1994-09-10', 'YYYY-MM-DD'), 'jean@free.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('psd', 'jim', 'Clinton', to_date('1995-01-02', 'YYYY-MM-DD'), 'email@hotmail.fr', 0);

--Insertions invalides
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('nica28me', 'oje55dme', 'ikh484ao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojem@alo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('nica128me', 'oje55dme', 'ikhao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojemalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('ni3ca28me', 'ojedme', 'ikh484ao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojemalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo) VALUES ('kevdu08');

--------------------------------------------------------------------------------
--DONE
--Parties : {iDPartie {pk}, début, finie}
CREATE TABLE Parties (
	iDPartie INT,
	debut DATE DEFAULT CURRENT_DATE,
	finie NUMBER(1) DEFAULT 0,
	PRIMARY KEY (iDPartie),
	CHECK ((iDPartie >= 0) AND (finie in (0,1)))
);

--Trigger qui vérifie que l'attribut "debut" a pour valeur le jour actuel
CREATE OR REPLACE TRIGGER trg_check_dates
BEFORE INSERT ON Parties
FOR EACH ROW
BEGIN
	IF( to_date(:new.debut, 'YYYY-MM-DD') <> to_date(CURRENT_DATE, 'YYYY-MM-DD') )
	THEN
		RAISE_APPLICATION_ERROR( -20001, 
		'Invalid debut: debut must be set to the current date - value = ' || 
		to_char( to_date(:new.debut, 'YYYY-MM-DD') ) || '. Instead of ' || to_char(to_date(CURRENT_DATE, 'YYYY-MM-DD')));
		END IF;
END;
/

--Frequently Used Commands
drop table Parties;
drop trigger trg_check_dates;
select * from Parties;

--Insertions valides
INSERT INTO Parties(iDPartie) VALUES (0);
INSERT INTO Parties(iDPartie) VALUES (1);
INSERT INTO Parties(iDPartie, finie) VALUES (2, 0);

--Insertions invalides
INSERT INTO Parties(iDPartie, debut) VALUES (1, to_date('2014-04-23', 'YYYY-MM-DD'));
INSERT INTO Parties(iDPartie, finie) VALUES (3, 2);

--------------------------------------------------------------------------------
--DONE
--Vainqueurs : {iDPartie{fk,pk}, pseudo{fk} }
CREATE TABLE Vainqueurs (
	iDPartie INT,
	pseudo VARCHAR(30),
	PRIMARY KEY (iDPartie),
    FOREIGN KEY (iDPartie) REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo) REFERENCES Joueurs (pseudo)
);

--Frequently Used Commands
drop table Vainqueurs;
select * from Vainqueurs;

--Insertions valides
INSERT INTO Vainqueurs(iDPartie) VALUES (0);
INSERT INTO Vainqueurs(iDPartie, finie) VALUES (2, 0);

--------------------------------------------------------------------------------
--DONE
--Participants : {joueur1{fk}, joueur2{fk}, iDPartie{pk, fk}, (old : iDPartie{fk, pk})
CREATE TABLE Participants (
	iDPartie INT,
	joueur1 VARCHAR(30) NOT NULL,
	joueur2 VARCHAR(30) NOT NULL,
	PRIMARY KEY (iDPartie),
    FOREIGN KEY (iDPartie) REFERENCES Parties (iDPartie),
    FOREIGN KEY (joueur1) REFERENCES Joueurs (pseudo),
    FOREIGN KEY (joueur2) REFERENCES Joueurs (pseudo),
    CHECK (
    		joueur1 <> joueur2
    	)
);

--Frequently Used Commands
drop table Participants;
select * from Participants;

--Insertions valides
INSERT INTO Participants(iDPartie, joueur1, joueur2) VALUES (0, 'ninja58', 'medjo');

--Insertions invalides
INSERT INTO Participants(iDPartie, joueur1, joueur2) VALUES (172, 'ninja58', 'ninja58');

--------------------------------------------------------------------------------

--Actions : {iDPartie {fk, pk}, pseudo{pk, fk}, iDBateau{fk}, nTour {pk}, nAction{pk}, x, y, type (tir, dep), direction (rg, rd, av, ar)}
CREATE TABLE Actions (
	iDPartie INT,
	pseudo VARCHAR(30),
	iDBateau INT,
	nTour INT DEFAULT 0 ,
	nAction INT DEFAULT 0,
	x INT,
	y INT,
	type VARCHAR(3) NOT NULL,
	direction VARCHAR(2),
	PRIMARY KEY (iDPartie, pseudo, nTour, nAction),
    FOREIGN KEY (iDPartie, pseudo, iDBateau) REFERENCES Bateaux (iDPartie, pseudo, iDBateau),
	CHECK (
		nTour >= 0
		AND nAction >= 0
		AND	(x BETWEEN 0 AND 9)
    	AND (y BETWEEN 0 AND 9)
    	AND (type IN ('tir', 'dep'))
    	AND (direction IN ('rg', 'rd', 'av', 'ar'))
	)
);

--Trigger qui vérifie que l'attribut 
CREATE OR REPLACE TRIGGER trg_check_coherence
BEFORE INSERT OR UPDATE ON Actions
FOR EACH ROW
BEGIN
	IF(:new.type = 'tir')
	THEN
		IF(:new.x IS NULL)
		THEN
			RAISE_APPLICATION_ERROR( -20002, 'Invalid x: x must be set.');
		ELSIF(:new.y IS NULL)
		THEN
			RAISE_APPLICATION_ERROR( -20002, 'Invalid y: y must be set.');
		END IF;
	END IF;
	IF(:new.type = 'dep')
	THEN
		IF(:new.x IS NOT NULL)
		THEN
			RAISE_APPLICATION_ERROR( -20002, 'Invalid x: x must not be set.');
		ELSIF(:new.y IS NOT NULL)
		THEN
			RAISE_APPLICATION_ERROR( -20002, 'Invalid y: y must not be set.');
		END IF;
	END IF;
END;
/

--Frequently Used Commands
drop table Actions;
drop trigger trg_check_coherence;
select * from Actions;

--Insertions valides
INSERT INTO Actions(iDPartie, pseudo, iDBateau, nTour, nAction,	x, y, type) VALUES (1064, 'Mordokkai', 56, 0, 0, 0, 0, 'tir');
INSERT INTO Actions(iDPartie, pseudo, iDBateau, nTour, nAction, type) VALUES (1168, 'Mordokkai', 1, 0, 0, 'dep');

--Insertions invalides
INSERT INTO Actions(iDPartie, pseudo, iDBateau, nTour, nAction,y, type) VALUES (1062, 'ninja58', 56, 0, 0,0, 'tir');
INSERT INTO Actions(iDPartie, pseudo, iDBateau, nTour, nAction, type, x) VALUES (1168, 'Mordokkai', 0, 0, 0, 'dep', 1);
INSERT INTO Actions(iDPartie, pseudo, iDBateau, nTour, nAction, type, y) VALUES (1168, 'Mordokkai', 0, 0, 0, 'dep', 1);
INSERT INTO Actions(iDPartie, pseudo, iDBateau, nTour, nAction, type, x, y) VALUES (1168, 'Mordokkai', 0, 0, 0, 'dep', 1, 2);

--------------------------------------------------------------------------------
--Display
set linesize 250
column pseudo format a13
column orientation format a1
column orientationI format a1
--column idPartie format a5
--column iDBateau format a5
--column etat format a1
--column taille format a1
--column x format a9
--column y format a2
--column xI format a2All Scripts
--column yI format a2

--Bateaux : {iDPartie {pk,fk}, pseudo{pk, fk}, iDBateau{pk}, état, taille, x, y, orientation, xI, yI, orientationI(n,s,e,o)}
CREATE TABLE Bateaux (
	iDPartie INT,
	pseudo VARCHAR(30),
	iDBateau INT,
	etat INT NOT NULL,
	taille INT NOT NULL,
	xI INT NOT NULL,
	yI INT NOT NULL,
	orientationI CHAR NOT NULL,
	x INT,
	y INT,
	orientation CHAR,
	PRIMARY KEY (iDPartie, pseudo, iDBateau),
    FOREIGN KEY (iDPartie) REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo) REFERENCES Joueurs (pseudo),
    CHECK(
    	(iDBateau BETWEEN 1 AND 3)
    	AND (x BETWEEN 0 AND 9)
    	AND (y BETWEEN 0 AND 9)
    	AND (xI BETWEEN 0 AND 9)
    	AND (yI BETWEEN 0 AND 9)
    	AND (etat BETWEEN 0 AND taille)
    	AND (taille IN (2,3))
    	AND (
    	((orientation = 'n' ) AND (y + taille - 1 <= 9))
    	OR ((orientation = 's' ) AND (y - taille + 1 >= 0))
    	OR ((orientation = 'e' ) AND (x + taille - 1 <= 9))
    	OR ((orientation = 'o' ) AND (x - taille + 1 >= 0))
    	)
    )
);

select max(idpartie) from parties;

CREATE OR REPLACE TRIGGER trg_init_bateaux
BEFORE INSERT OR UPDATE ON Bateaux
FOR EACH ROW
DECLARE cnt integer;
BEGIN
	IF(:new.orientation IS NULL)
	THEN
		IF(:old.orientation IS NULL)
		THEN
			:new.orientation := :new.orientationI;
		ELSE
			:new.orientation := :old.orientation;
		END IF;
	END IF;

	IF(:new.x IS NULL)
	THEN
		IF(:old.x IS NULL)
		THEN
			:new.x := :new.xI;
		ELSE
			:new.x := :old.x;
		END IF;
	END IF;
	
	IF(:new.y IS NULL)
	THEN
		IF(:old.y IS NULL)
		THEN
			:new.y := :new.yI;
		ELSE
			:new.y := :old.y;
		END IF;
	END IF;
	
	SELECT COUNT(iDBateau) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) );
	IF(cnt > 2)
	THEN
		RAISE_APPLICATION_ERROR( -20003, 'One player cannot have more than 3 boats.');
	END IF;
	SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND (y = :new.y));
	IF(cnt > 0)
	THEN
		SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND (x = :new.x));
		IF(cnt > 0)
		THEN
			RAISE_APPLICATION_ERROR( -20003, 'The new boat is colliding with an existing one.');
		END IF;
		SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND ((:new.orientation = 'e') OR (:new.orientation = 'o' )) AND (orientation = :new.orientation) AND ( ((x - :new.x > 0) AND ABS(x - :new.x) < :new.taille ) OR ((x - :new.x < 0) AND ABS(x - :new.x) < taille) ));
		IF(cnt > 0)
		THEN
			RAISE_APPLICATION_ERROR( -20003, 'The new boat is colliding with an existing one.');
		END IF;
		
		SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND (((orientation = 'o') AND (:new.orientation = 'e') AND (x - :new.x < taille + :new.taille - 1)) OR ((orientation = 'e') AND (:new.orientation = 'o') AND (:new.x - x < taille + :new.taille - 1))));
		IF(cnt > 0)
		THEN
			RAISE_APPLICATION_ERROR( -20003, 'The new boat is colliding with an existing one.');
		END IF;
	ELSE
		SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND (x = :new.x));
	IF(cnt > 0)
		THEN
			SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND ((:new.orientation = 'e') OR (:new.orientation = 'o' )) AND (orientation = :new.orientation) AND ( ((y - :new.y > 0) AND ABS(y - :new.y) < :new.taille ) OR ((y - :new.y < 0) AND ABS(y - :new.y) < taille) ));
			IF(cnt > 0)
			THEN
				RAISE_APPLICATION_ERROR( -20003, 'The new boat is colliding with an existing one.');
			END IF;
			SELECT COUNT(*) INTO cnt FROM Bateaux WHERE ((iDPartie = :new.iDPartie) AND (pseudo = :new.pseudo) AND (iDBateau <> :new.iDBateau) AND (((orientation = 'n') AND (:new.orientation = 's') AND (y - :new.y < taille + :new.taille - 1)) OR ((orientation = 's') AND (:new.orientation = 'n') AND (:new.y - y < taille + :new.taille - 1))));
			IF(cnt > 0)
			THEN
				RAISE_APPLICATION_ERROR( -20003, 'The new boat is colliding with an existing one.');
			END IF;
		ELSE
			SELECT COUNT(*) INTO cnt FROM Bateaux WHERE (
			(iDPartie = :new.iDPartie) 
			AND (pseudo = :new.pseudo) 
			AND (iDBateau <> :new.iDBateau) 
			AND (
			
			((orientation = 'n') AND (:new.orientation = 'o') AND (:new.y BETWEEN y AND y + taille - 1) AND (x BETWEEN :new.x - :new.taille + 1 AND :new.x))
			OR ((orientation = 's') AND (:new.orientation = 'o') AND (:new.y BETWEEN y - taille + 1 AND y) AND (x BETWEEN :new.x - :new.taille + 1 AND :new.x))
			OR ((orientation = 'n') AND (:new.orientation = 'e') AND (:new.y BETWEEN y AND y + taille - 1) AND (x BETWEEN :new.x AND :new.x + :new.taille - 1))
			OR ((orientation = 's') AND (:new.orientation = 'e') AND (:new.y BETWEEN y - taille + 1 AND y) AND (x BETWEEN :new.x AND :new.x + :new.taille - 1))
			OR ((orientation = 'o') AND (:new.orientation = 'n') AND (y BETWEEN :new.y AND :new.y + :new.taille - 1) AND (:new.x BETWEEN x - taille + 1 AND x))
			OR ((orientation = 'e') AND (:new.orientation = 'n') AND (y BETWEEN :new.y AND :new.y + :new.taille - 1) AND (:new.x BETWEEN x AND x + taille - 1))
			OR ((orientation = 'o') AND (:new.orientation = 's') AND (y BETWEEN :new.y - :new.taille + 1 AND :new.y ) AND (:new.x BETWEEN x - taille + 1 AND x))
			OR ((orientation = 'e') AND (:new.orientation = 's') AND (y BETWEEN :new.y - :new.taille + 1 AND :new.y ) AND (:new.x BETWEEN x AND x + taille - 1 ))
			
			));
			IF(cnt > 0)
			THEN
				RAISE_APPLICATION_ERROR( -20003, 'The new boat is colliding with an existing one.');
			END IF;
		END IF;
	END IF;
END;
/







--Frequently Used Commands
drop table Bateaux;
drop trigger trg_init_bateaux;
select * from Bateaux;

--Insertions valides
INSERT INTO Bateaux(iDPartie, pseudo, iDBateau, etat, taille, x, y, orientation) VALUES (11, 'Mordokkai', 1, 0, 3, 0, 0,'e');
INSERT INTO Bateaux(iDPartie, pseudo, iDBateau, etat, taille, x, y, orientation) VALUES (11, 'Mordokkai', 2, 0, 3, 0, 0,'e');
--Insertions invalides
INSERT INTO Bateaux(iDPartie, pseudo, iDBateau, etat, taille, x, y, orientation) VALUES (11, 'Mordokkai', 2, 0, 3, 4, 3,'e');

--------------------------------------------------------------------------------
--Display
set linesize 250
column ville format a15
column nomRue format a10
column pseudo format a10
--column CP format a10
--column numRue format a10

--Adresses : {pseudo{fk}, numRue, nomRue, CP, Ville}
CREATE TABLE Adresses (
	pseudo VARCHAR(30),
	numRue INT NOT NULL,
	nomRue VARCHAR(50)  NOT NULL,
	CP INT  NOT NULL,
	ville VARCHAR(50)  NOT NULL,
	PRIMARY KEY (pseudo),
	FOREIGN KEY (pseudo) REFERENCES Joueurs (pseudo),
	CHECK(
		numRue > 0
		AND REGEXP_LIKE (nomRue,'^[A-Za-z0-9]+[A-Za-z0-9._%+-]')
		AND CP BETWEEN 0 AND 1000000
		AND REGEXP_LIKE (ville,'^[A-Za-z0-9]+[A-Za-z0-9._%+-]')
		)
);

--Frequently Used Commands
drop table Adresses;
select * from Adresses;

--Insertions valides
INSERT INTO Adresses(pseudo, numRue, nomRue, CP, ville) VALUES ('ninja64', 1220, 'residences', 38400, 'St Martin d''Heres');

-------------------------------------------------------------------------------
--Supprime toutes les tables
drop table joueurs;
drop table Parties;
drop table Vainqueurs;
drop table Participants;
drop table Bateaux;
drop table Adresses;

select * from participants where idpartie=1062;

--Efface toutes les tables
--------------------------------------------------------------------------------
delete from joueurs;
delete from Parties;
delete from Vainqueurs;
delete from Participants;
delete from Actions;
delete from Bateaux;
delete from Adresses;

--Trash
update bateaux set etat = etat-1 where idpartie=134 ;

