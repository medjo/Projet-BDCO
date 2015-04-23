--Display
set linesize 250
column email format a50
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
	nom VARCHAR(50),
	prenom VARCHAR(50),
	dateDeNaissance DATE, --DATE - format YYYY-MM-DD
	email VARCHAR(80),
	nbPartiesJouees INT default 0,
	PRIMARY KEY (pseudo),
	CHECK (
		REGEXP_LIKE (pseudo,'^[A-Za-z0-9]+[A-Za-z0-9._%+-]')
		AND (REGEXP_LIKE (nom,'[A-Za-z]+'))
		AND (REGEXP_LIKE (prenom,'[A-Za-z]+'))
		AND (REGEXP_LIKE (email,'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'))
		AND (nbPartiesJouees >= 0)
	)
);

--Insertions valides
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('medjo', 'ojeme', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 0);
INSERT INTO Joueurs(pseudo) VALUES ('kevdu08');
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('ninja58', 'ojeme', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 5);
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('aigle78', 'ojeme', 'ikhalo', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojeme.ikhalo@phelma.grenoble-inp.fr', 0);


--Insertions invalides
INSERT INTO Joueurs(pseudo, nom, prenom, dateDeNaissance, email, nbPartiesJouees) VALUES ('nicrrkname', 'oje55dme', 'ikhao', to_date('1993-06-05', 'YYYY-MM-DD'), 'ojem@alo@phelma.grenoble-inp.fr', 0);

--------------------------------------------------------------------------------

--Parties : {iDPartie {pk}, début, finie}
CREATE TABLE Parties (
	iDPartie INT,
	debut DATE default CURRENT_DATE,
	finie NUMBER(1) default 0,
	PRIMARY KEY (iDPartie),
	CHECK ((iDPartie >= 0) AND (finie in (0,1)))
);

insert 


--Vainqueurs : {iDPartie{fk,pk}, pseudo{fk} }
CREATE TABLE Vainqueurs (
	iDPartie INT,
	pseudo VARCHAR(30),
	PRIMARY KEY (iDPartie),
    FOREIGN KEY (iDPartie)
    REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo),
    REFERENCES Joueurs (pseudo),
);

--EnAttente : {pseudo{pk, fk}, iDPartie{pk, fk}}
CREATE TABLE EnAttente (
	iDPartie INT,
	pseudo VARCHAR(30),
	PRIMARY KEY (iDPartie, pseudo),
    FOREIGN KEY (iDPartie)
    REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo),
    REFERENCES Joueurs (pseudo),
);

--Participants : {pseudo{pk, fk}, iDPartie{pk, fk}}
CREATE TABLE Participants (
	iDPartie INT,
	pseudo VARCHAR(30),
	PRIMARY KEY (iDPartie, pseudo),
    FOREIGN KEY (iDPartie)
    REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo),
    REFERENCES Joueurs (pseudo),
);

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
    FOREIGN KEY (iDPartie)
    REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo),
    REFERENCES Joueurs (pseudo),
    FOREIGN KEY (iDBateau),
    REFERENCES Bateaux (iDBateau),
    CHECK()
);

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
    FOREIGN KEY (iDPartie)
    REFERENCES Parties (iDPartie),
    FOREIGN KEY (pseudo),
    REFERENCES Joueurs (pseudo),
    CHECK()
);

--Adresses : {pseudo{fk}, iDAdresse{pk}, numRue, nomRue, CP, Ville}
CREATE TABLE Joueurs (
	pseudo VARCHAR(30),
	iDAdresse INT,
	numRue INT,
	nomRue VARCHAR(50),
	CP INT,
	ville VARCHAR(50),
	PRIMARY KEY (iDAdresse),
	FOREIGN KEY (pseudo),
    REFERENCES Joueurs (pseudo),
	CHECK ()
);

