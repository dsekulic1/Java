package ba.unsa.etf.rpr;

public class Drzava {
    private int id;
    private String naziv;
    private Grad glavni_Grad;


    public Drzava(int id, String naziv, Grad glavni_Grad) {
        this.id = id;
        this.naziv = naziv;
        this.glavni_Grad = glavni_Grad;
    }

    public Drzava() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Grad getGlavniGrad() {
        return glavni_Grad;
    }

    public void setGlavniGrad(Grad glavni_Grad) {
        this.glavni_Grad = glavni_Grad;
    }
}
