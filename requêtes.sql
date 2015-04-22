--Joueurs : {pseudo {pk}, nom, prénom, dateDeNaissance, email, nbPartiesJouees}
CREATE TABLE Joueurs (
	pseudo VARCHAR(30),
	nom VARCHAR(50),
	prenom VARCHAR(50),
	dateDeNaissance TIMESTAMP,
	email VARCHAR(80)
	nbPartiesJouees INT default 0,
	PRIMARY KEY (pseudo),
	CHECK (REGEXP_LIKE (email,'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$') AND (nbPartiesJouees >= 0))
);

--Parties : {iDPartie {pk}, début, finie}
CREATE TABLE Parties (
	iDPartie INT,
	debut TIMESTAMP,
	finie BOOLEAN default FALSE,
	PRIMARY KEY (iDPartie),
	CHECK ()
);


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

