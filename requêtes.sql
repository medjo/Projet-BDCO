--Display
set linesize 250
column email format a35
column dateDeNaissance format a15
column prenom format a10
column nom format a10
column pseudo format a10
--column nbPartiesJouees format a10

--Frequently Used Commands
drop table joueurs;
select * from joueurs;

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
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('medjo', 'felix', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('ninja58', 'ojeme', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 5);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('aigle78', 'ojeme', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('mf edjo', 'ojeme', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 0);

--Insertions invalides
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('nica28me', 'oje55dme', 'ikh484ao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojem@alo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('nica128me', 'oje55dme', 'ikhao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojemalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('ni3ca28me', 'ojedme', 'ikh484ao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojemalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo) VALUES ('kevdu08');

--------------------------------------------------------------------------------

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
BEFORE INSERT OR UPDATE ON Parties
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
select * from Parties;

--Insertions valides
INSERT INTO Parties(iDPartie) VALUES (0);
INSERT INTO Parties(iDPartie, finie) VALUES (2, 0);

--Insertions invalides
INSERT INTO Parties(iDPartie, debut) VALUES (1, to_date('2014-04-23', 'YYYY-MM-DD'));
INSERT INTO Parties(iDPartie, finie) VALUES (3, 2);

--------------------------------------------------------------------------------

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
INSERT INTO Participants(iDPartie, numJoueur, pseudo) VALUES (0, 1, 'medjo');

--Insertions invalides
INSERT INTO Participants(iDPartie, numJoueur, pseudo) VALUES (0, 2, 'medjo');--invalide si 

--------------------------------------------------------------------------------

--Actions : {iDPartie {fk, pk}, pseudo{pk, fk}, iDBateau{fk}, nTour {pk}, nAction{pk}, action (Tir ou déplacement), x, y, type (rotation, translation), direction}
CREATE TABLE Actions (
	iDPartie INT,
	pseudo VARCHAR(30),
	iDBateau INT,
	nTour INT,
	nAction INT,
	action VARCHAR(11),
	x INT,
	y INT,
	type VARCHAR(11),
	direction CHAR,
	PRIMARY KEY (iDPartie, pseudo, nTour, nAction),
    FOREIGN KEY (iDPartie, pseudo, iDBateau) REFERENCES Bateaux (iDPartie, pseudo, iDBateau),
	CHECK (
		(x BETWEEN 0 AND 9)
    	AND (y BETWEEN 0 AND 9)
	)
);
--------------------------------------------------------------------------------

--Bateaux : {iDPartie {pk,fk}, pseudo{pk, fk}, iDBateau{pk}, état, taille, x, y, orientation, xI, yI, orientationI}
CREATE TABLE Bateaux (
	iDPartie INT,
	pseudo VARCHAR(30),
	iDBateau INT,
	etat INT,
	taille INT,
	x INT,
	y INT,
	orientation CHAR,
	xI INT,
	yI INT,
	orientationI CHAR,
	PRIMARY KEY (iDPartie, pseudo, iDBateau),
    FOREIGN KEY (iDPartie) REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo) REFERENCES Joueurs (pseudo),
    CHECK(
    	(x BETWEEN 0 AND 9)
    	AND (y BETWEEN 0 AND 9)
    	AND (xI BETWEEN 0 AND 9)
    	AND (yI BETWEEN 0 AND 9)
    	AND (etat BETWEEN 0 AND taille)
    	AND (taille IN (2,3))
    )
);

--Frequently Used Commands
drop table Bateaux;
select * from Bateaux;

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
