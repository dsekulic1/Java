package ba.unsa.etf.rpr;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GeografijaDAO geografijaDAO=GeografijaDAO.getInstance();
       // geografijaDAO.gradovi();
        System.out.println( ispisiGradove());
        glavniGrad();
//        System.out.println("Gradovi su:\n" + ispisiGradove());
//        glavniGrad();
    }
//"London (Velika Britanija) - 8825000\n" +
    public static String ispisiGradove() {
        GeografijaDAO geografijaDAO=GeografijaDAO.getInstance();
        ArrayList<Grad> grad=geografijaDAO.gradovi();
        String rezultat="";
        for(Grad g:grad){
            rezultat+=g.getNaziv()+" ("+g.getDrzava().getNaziv()+") - "+g.getBrojStanovnika()+"\n";
        }
        return rezultat;
    }
    public static void glavniGrad(){
        GeografijaDAO geografijaDAO=GeografijaDAO.getInstance();
        String drzava;
        System.out.println("Unesite državu:");
        Scanner myObj = new Scanner(System.in);
        drzava = myObj.nextLine();
        Grad grad=geografijaDAO.glavniGrad(drzava);
        if (grad == null) System.out.println("Nepostojeća država"+"\n");
        else System.out.println("Glavni grad države "+drzava +" je "+grad.getNaziv()+"\n");
    }

}
