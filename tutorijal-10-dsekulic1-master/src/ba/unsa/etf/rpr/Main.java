package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {

    public static void main(String[] args) {
launch(args);

        GeografijaDAO geografijaDAO=GeografijaDAO.getInstance();
       // geografijaDAO.gradovi();
        System.out.println( ispisiGradove());
      //  glavniGrad();
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
//    public static void glavniGrad(){
//        GeografijaDAO geografijaDAO=GeografijaDAO.getInstance();
//        String drzava;
//        System.out.println("Unesite državu:");
//        Scanner myObj = new Scanner(System.in);
//        drzava = myObj.nextLine();
//        Grad grad=geografijaDAO.glavniGrad(drzava);
//        if (grad == null) System.out.println("Nepostojeća država"+"\n");
//        else System.out.println("Glavni grad države "+drzava +" je "+grad.getNaziv()+"\n");
//    }

    @Override
    public void start(Stage stage) throws Exception {
        GlavnaController ctrl=new GlavnaController();

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        loader.setController(ctrl);
        Parent root=loader.load();
        stage.setTitle("Gradovi svijeta");
        stage.setScene(new Scene(root,USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setMinWidth(140);
        stage.show();
    }
}
