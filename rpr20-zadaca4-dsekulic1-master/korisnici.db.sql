create table if not exists korisnik(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    ime varchar(50),
    prezime varchar(50),
    email varchar(50),
    username varchar(50),
    password varchar(50),
    slika varchar(100)
);

insert into korisnik values(1, 'Vedran', 'Ljubović', 'vljubovic@etf.unsa.ba', 'vedranlj', 'test', '/img/face-smile.png');
insert into korisnik values(2, 'Amra', 'Delić', 'adelic@etf.unsa.ba', 'amrad', 'test', '/img/face-smile.png');
insert into korisnik values(3, 'Tarik', 'Sijerčić', 'tsijercic1@etf.unsa.ba', 'tariks', 'test', '/img/face-smile.png');
insert into korisnik values(4, 'Rijad', 'Fejzić', 'rfejzic1@etf.unsa.ba', 'rijadf', 'test', '/img/face-smile.png');
