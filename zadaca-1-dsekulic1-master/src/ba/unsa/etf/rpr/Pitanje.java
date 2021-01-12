package ba.unsa.etf.rpr;

import java.util.*;
import java.util.stream.Collectors;

public class Pitanje {
    private String tekst;
    private double brojPoena;
    private Map<String, Odgovor> odgovori = new HashMap<>();

    public Pitanje(String tekst, double brojPoena) {
        this.tekst = tekst;
        this.brojPoena = brojPoena;
    }


    public void setBrojPoena(double brojPoena) {
        this.brojPoena = brojPoena;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public double getBrojPoena() {
        return brojPoena;
    }

    public void dodajOdgovor(String kljuc, String tekst, boolean tacno) {
        if (!odgovori.containsKey(kljuc)) {
            odgovori.put(kljuc, new Odgovor(tekst, tacno));
        } else throw new IllegalArgumentException("Id odgovora mora biti jedinstven");
    }

    public void obrisiOdgovor(String kljuc) {
        if (odgovori.containsKey(kljuc)) {
            odgovori.remove(kljuc);
        } else throw new IllegalArgumentException("Odgovor s ovim id-em ne postoji");
    }

    public Map<String, Odgovor> getOdgovori() {
        return odgovori;
    }

    public List<Odgovor> dajListuOdgovora() {
        //System.out.println("aaa: "+ odgovori.size());
        List<Odgovor> odg = new ArrayList<>();
        for (Odgovor o : odgovori.values()) {
            odg.add(o);
        }
        return odgovori.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    public String toString2() {
        String s = "";
        s += tekst + "(" + brojPoena + "b)";
        for (Map.Entry<String, Odgovor> entry : odgovori.entrySet()) {
            String key = entry.getKey();
            Odgovor odg = entry.getValue();
            s += "\n\t" + key + ": " + odg.getTekstOdgovora();
            if (odg.isTacno()) {
                s += "(T)";
            }
        }

        return s;
    }

    @Override
    public String toString() {
        String s = "";
        s += tekst + "(" + brojPoena + "b)";
        for (Map.Entry<String, Odgovor> entry : odgovori.entrySet()) {
            String key = entry.getKey();
            Odgovor odg = entry.getValue();
            s += "\n\t" + key + ": " + odg.getTekstOdgovora();
        }
        return s;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pitanje pitanje = (Pitanje) o;
        return Double.compare(pitanje.brojPoena, brojPoena) == 0 &&
                Objects.equals(tekst.toUpperCase(), pitanje.tekst.toUpperCase()) &&
                Objects.equals(odgovori, pitanje.odgovori);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tekst, brojPoena, odgovori);
    }

    public int UkupnoTacni() {
        int brT = 0;
        for (Map.Entry<String, Odgovor> entry : odgovori.entrySet()) {
            Odgovor odg = entry.getValue();
            if (odg.isTacno()) brT++;
        }
        return brT;
    }

    public double izracunajPoene(List<String> odg, SistemBodovanja sistem) {
        //map<String, Odgovor>
        if (odg.size() == 0) return 0;
        List<String> temp = new ArrayList<>();
        for (String o : odg) {
            if (temp.contains(o)) throw new IllegalArgumentException("Postoje duplikati među odabranim odgovorima");
            else temp.add(o);
            if (!odgovori.containsKey(o)) throw new IllegalArgumentException("Odabran je nepostojeći odgovor");
        }
        int brojTacnih = 0, brojNetacnih = 0;
        for (String o : odg) {
            if (odgovori.get(o).isTacno()) {
                brojTacnih++;
            } else {
                brojNetacnih++;
            }
        }
        switch (sistem) {
            case BINARNO:
                if (brojNetacnih > 0 || brojTacnih < UkupnoTacni()) return 0;
                return brojPoena;
            case PARCIJALNO:
                if (brojNetacnih > 0) return 0;
                if(brojTacnih==UkupnoTacni()) return brojPoena;
                return (brojPoena/odgovori.size())*brojTacnih;
                //return (int) ((brojPoena * brojTacnih) / UkupnoTacni());
            case PARCIJALNO_SA_NEGATIVNIM:
                if (brojNetacnih > 0) return -0.5 * brojPoena;
                if(brojTacnih==UkupnoTacni()) return brojPoena;
                return (brojPoena/odgovori.size())*brojTacnih;
               // return (int) (brojPoena * brojTacnih) / UkupnoTacni();

        }
        return 0;
    }
}
