BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Grad" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"naziv"	TEXT,
	"broj_stanovnika"	INTEGER,
	"drzava"	INTEGER,
	FOREIGN KEY("drzava") REFERENCES "Drzava"("ID")
);
CREATE TABLE IF NOT EXISTS "Drzava" (
	"ID"	INTEGER,
	"naziv"	TEXT,
	"glavni_grad"	INTEGER,
	PRIMARY KEY("ID"),
	FOREIGN KEY("glavni_grad") REFERENCES "Grad"("id")
);
INSERT INTO "Grad" VALUES (1,'London',8825000,1);
INSERT INTO "Grad" VALUES (2,'Pariz',2206488,2);
INSERT INTO "Grad" VALUES (3,'Beč',1899055,3);
INSERT INTO "Grad" VALUES (4,'Manchester',545500,1);
INSERT INTO "Grad" VALUES (5,'Graz',280200,3);
INSERT INTO "Drzava" VALUES (1,'Velika Britanija',1);
INSERT INTO "Drzava" VALUES (2,'Francuska',2);
INSERT INTO "Drzava" VALUES (3,'Austrija',3);
COMMIT;
