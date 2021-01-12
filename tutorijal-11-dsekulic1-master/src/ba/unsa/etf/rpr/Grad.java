package ba.unsa.etf.rpr;

public class Grad {
    private Integer id;
    private Integer broj_stanovnika;
    private String naziv;
    private Drzava drzava;

    public Grad() {
    }

    public Grad(Integer id,  String naziv,Integer broj_stanovnika, Drzava drzava) {
        this.id = id;
        this.broj_stanovnika = broj_stanovnika;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getBrojStanovnika() {
        return broj_stanovnika;
    }

    public void setBrojStanovnika(int broj_stanovnika) {
        this.broj_stanovnika = broj_stanovnika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return naziv;
    }
}

