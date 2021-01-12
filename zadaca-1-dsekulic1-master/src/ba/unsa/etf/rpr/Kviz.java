package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kviz {
    private List<Pitanje> pitanja = new ArrayList<>();
    private String naziv;
    private SistemBodovanja sistemBodovanja;

    public Kviz(String naziv, SistemBodovanja sistemBodovanja) {
        this.naziv = naziv;
        this.sistemBodovanja = sistemBodovanja;
    }


    public void dodajPitanje(Pitanje pitanje) {
        if (!pitanja.contains(pitanje))
            pitanja.add(pitanje);
        else throw new IllegalArgumentException("Ne možete dodati pitanje sa tekstom koji već postoji");
    }

    public SistemBodovanja getSistemBodovanja() {
        return sistemBodovanja;
    }

    public List<Pitanje> getPitanja() {
        return pitanja;
    }


    public String getNaziv() {
        return naziv;
    }


    public void setSistemBodovanja(SistemBodovanja sistemBodovanja) {
        this.sistemBodovanja = sistemBodovanja;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Kviz \"" + getNaziv() + "\" boduje se " + sistemBodovanja.getValue().toLowerCase() + ". Pitanja:\n";
        int i = 1;
        for (Pitanje p : pitanja) {
            if (i > 1)
                s += "\n\n";
            s += i + ". " + p.toString2();
            i++;
        }
        return s;
    }

    public RezultatKviza predajKviz(Map<Pitanje, ArrayList<String>> zaokruzeniOdgovori) {
        RezultatKviza rz = new RezultatKviza(this);
        Map<Pitanje, Double> bodovi = new HashMap<>();
        for (Pitanje p : this.pitanja) {
            if (!bodovi.containsKey(p)) bodovi.put(p, 0.);
        }
        double total = 0;
        for (Map.Entry<Pitanje, ArrayList<String>> entry : zaokruzeniOdgovori.entrySet()) {
            Pitanje pitanje = entry.getKey();
            ArrayList<String> odgovori = entry.getValue();
            if (odgovori.size() != 0) {
                double bod = pitanje.izracunajPoene(odgovori, sistemBodovanja);
                bodovi.replace(pitanje, bod);
                total += bod;
            }
        }
        rz.setBodovi(bodovi);
        rz.setTotal(total);
        return rz;
    }

    public void setPitanja(List<Pitanje> pitanja) {
        this.pitanja = pitanja;
    }
}
