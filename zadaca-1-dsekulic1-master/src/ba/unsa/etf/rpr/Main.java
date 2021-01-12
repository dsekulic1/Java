package ba.unsa.etf.rpr;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Kviz kviz=new Kviz("Poznavanje Bosne i Hercegovine kao naše domovine",SistemBodovanja.PARCIJALNO_SA_NEGATIVNIM);
        List<Pitanje> tanjaPi=new ArrayList<>();

        Pitanje p1=new Pitanje("Glavni grad Bosne i Hercegovine je:",4);
        p1.dodajOdgovor("a","Zenica",false);
        p1.dodajOdgovor("b","Sarajevo",true);
        p1.dodajOdgovor("c","Mostar",false);
        p1.dodajOdgovor("d","Tuzla",false);

        Pitanje p2=new Pitanje("Sarajevo pripada kantonu?",3);
        p2.dodajOdgovor("a","Kanton Sarajevo",true);
        p2.dodajOdgovor("b","Unsko-sanski",false);
        p2.dodajOdgovor("c","Mostar-duvanjski",false);
        p2.dodajOdgovor("d","Tuzlanski",false);
        tanjaPi.add(p1);
        tanjaPi.add(p2);

        kviz.setPitanja(tanjaPi);
        igrajKviz(kviz);
    }
    public static void igrajKviz(Kviz kviz){
        System.out.println("---------Početak kviza---------\nSlijede Vaša pitanja:\n");
        int brojPitanja=1;
        for(Pitanje p:kviz.getPitanja()){
            System.out.println(brojPitanja+". "+p.toString()+"\n");
            brojPitanja++;
        }
        System.out.println("Tačne odgovore unesite u sljedećem formatu: 1 - a. U slučaju višestrukih odgovora 1 - a,b,c." +
                "\n(Ukoliko za neko pitanje ne znate odgovor ili niste sigurni unesite -1 ili 0!\n");

        Scanner ulaz=new Scanner(System.in);
        System.out.println("Vaši odgovori:\n");
        ArrayList <String> odg=new ArrayList<>();
        List<Double> poeni=new ArrayList<>();
        for(int i=0;i<kviz.getPitanja().size();i++){
            System.out.println(i+1+" - ");
            String odgovor=ulaz.nextLine();
            String[] niz =odgovor.split(",");
            for(int j=0;j< niz.length;j++){
                if(niz[j].equals("0") || niz[j].equals("-1")) {
                    break;
                }
                odg.add(niz[j]);
            }
            poeni.add(kviz.getPitanja().get(i).izracunajPoene(odg,kviz.getSistemBodovanja()));
            odg.clear();
        }
        double rez=0;
        System.out.println("Vaš rezultat po pitanjima je: \n");
        int i=0;
        for(Double poen:poeni){
            rez+=poen;
            System.out.println(i+1+" - "+poen +" b\n");
            i++;
        }
        System.out.println("Vaš konačan rezultat je: "+rez+" b");
        System.out.println("Da li želite pogledati tačne odgovore na pitanja (Y/N)?\n");
        if(ulaz.nextLine().toUpperCase().equals("Y")){
            for (Pitanje p:kviz.getPitanja()){
                System.out.println(p.getTekst());
                for (Map.Entry<String, Odgovor> entry : p.getOdgovori().entrySet()) {
                    String key = entry.getKey();
                    Odgovor odgovor = entry.getValue();
                    if(odgovor.isTacno()) System.out.println(key+": "+odgovor.getTekstOdgovora()+"\n");
                }
            }
        }
        System.out.println("Hvala na učešću!!\nDoviđenja!");
    }
}
