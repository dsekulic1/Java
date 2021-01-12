package ba.unsa.etf.rpr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeografijaDAO {
    private static GeografijaDAO instance = null;
    private Connection connection;
    private PreparedStatement Upit = null;
    private PreparedStatement daj_glavne = null;
    private PreparedStatement daj_drzave = null;
    private PreparedStatement drzave = null;
    private PreparedStatement obrisi_gradove = null;
    private PreparedStatement obrisi_drzave = null;
    private PreparedStatement dodaj_grad = null;
    private PreparedStatement max_Gradid = null;
    private PreparedStatement max_Drzavaid = null;
    private PreparedStatement dodaj_drzavu = null;
    private PreparedStatement update;
    private PreparedStatement gradovi = null;
    private PreparedStatement daj_Gradove=null;
    private PreparedStatement obrisi_grad = null;

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka… nastavljam sa praznom bazom");
        }
    }

    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
    }

    public GeografijaDAO() {
        if (instance == null) {
            try {
                //jdbc driver koji se povezuje na bazu, za druge baze ide drugi
                connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
                try {
                    daj_glavne = connection.prepareStatement("SELECT g.id,g.naziv,g.broj_stanovnika,d.id,d.naziv FROM grad g,drzava d where d.glavni_grad=g.id and d.naziv=?");
                } catch (SQLException e) {
                    regenerisiBazu();
                    try {
                        daj_glavne = connection.prepareStatement("SELECT g.id,g.naziv,g.broj_stanovnika,d.id,d.naziv FROM grad g,drzava d where d.glavni_grad=g.id and d.naziv=?");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                gradovi = connection.prepareStatement("Select * FROM grad ");
                drzave = connection.prepareStatement("Select id,naziv,glavni_grad FROM drzava ");
                daj_drzave = connection.prepareStatement("Select d.id,d.naziv,d.glavni_grad FROM drzava d where d.naziv=?");
                obrisi_gradove = connection.prepareStatement("delete from grad where drzava=?");
                obrisi_grad = connection.prepareStatement("delete from grad where id=?");
                obrisi_drzave = connection.prepareStatement("delete from drzava  where naziv=?");
                dodaj_grad = connection.prepareStatement("insert into grad values (?,?,?,?)");
                max_Gradid = connection.prepareStatement("select max(id)+1 from grad");
                max_Drzavaid = connection.prepareStatement("select max(id)+1 from drzava");
                dodaj_drzavu = connection.prepareStatement("insert into drzava values (?,?,?)");
                daj_Gradove = connection.prepareStatement("Select id,naziv,broj_stanovnika,drzava from grad where naziv=?");
                update = connection.prepareStatement("UPDATE Grad SET naziv=? ,broj_stanovnika=?,drzava=? WHERE id=?");
            } catch (SQLException greska) {
                greska.printStackTrace();
            }
        }
    }

    public static GeografijaDAO getInstance() {
        //Ako mi je neko zatrazio konekciju s bazom otidji napravi i vrati
        if (instance == null) instance = new GeografijaDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            try {
                instance.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> rez = new ArrayList<>();
        try {
            Upit = connection.prepareStatement(
                    "SELECT g.id ,g.naziv,g.broj_stanovnika,g.drzava,d.id,d.naziv,d.glavni_grad,g2.id,g2.naziv,g2.broj_stanovnika FROM grad g,drzava d,grad g2 WHERE g.drzava=d.id and d.glavni_grad=g2.id ORDER BY g.broj_stanovnika desc");

            ResultSet rezultat = Upit.executeQuery();

            while (rezultat.next()) {
                Grad g = new Grad(rezultat.getInt(1), rezultat.getString(2), rezultat.getInt(3), null);
                Drzava d = new Drzava(rezultat.getInt(5), rezultat.getString(6), null);
                Grad glavni = new Grad(rezultat.getInt(7), rezultat.getString(8), rezultat.getInt(9), d);
                d.setGlavniGrad(glavni);
                g.setDrzava(d);
                rez.add(g);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rez;
    }

    public ArrayList<Drzava> drzave() {
        ArrayList<Drzava> rez = new ArrayList<>();
        try {

            ResultSet rezultat = drzave.executeQuery();

            while (rezultat.next()) {
                Drzava d = new Drzava(rezultat.getInt(1), rezultat.getString(2), null);
                rez.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rez;
    }

    Grad glavniGrad(String drzava) {
        try {
            daj_glavne.setString(1, drzava);
            ResultSet rez = daj_glavne.executeQuery();
            if (!rez.next()) return null;
            else {
                Grad g = new Grad(rez.getInt(1), rez.getString(2), rez.getInt(3), null);
                Drzava d = new Drzava(rez.getInt(4), rez.getString(5), null);
                d.setGlavniGrad(g);
                g.setDrzava(d);
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    void obrisiDrzavu(String drzava) {
        try {
            daj_drzave.setString(1, drzava);
            ResultSet rez = daj_drzave.executeQuery();
            while (rez.next()) {
                obrisi_gradove.setInt(1, rez.getInt(1));
                obrisi_gradove.executeUpdate();
                obrisi_drzave.setString(1, drzava);
                obrisi_drzave.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void dodajGrad(Grad grad) {
        try {
            // resetujBazu();
            ResultSet rez = max_Gradid.executeQuery();
            int id = 1;
            if (rez.next()) id = rez.getInt(1);
            grad.setId(id);
            dodaj_grad.setInt(1, id);
            dodaj_grad.setString(2, grad.getNaziv());
            dodaj_grad.setInt(3, grad.getBrojStanovnika());
            if (grad.getDrzava() != null)
                dodaj_grad.setInt(4, grad.getDrzava().getId());
            else
                dodaj_grad.setInt(4, 0);
            dodaj_grad.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Drzava nadjiDrzavu(String drzava) {
        try {
            daj_drzave.setString(1, drzava);
            ResultSet rez = daj_drzave.executeQuery();
            if (!rez.next()) return null;
            else {
                Drzava d = new Drzava(rez.getInt(1), rez.getString(2), glavniGrad(drzava));
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void dodajDrzavu(Drzava drzava)  {
        try {
            // resetujBazu();
            //ovo
            // connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            //max_Drzavaid = connection.prepareStatement("select max(id)+1 from drzava");
            //max_Gradid = connection.prepareStatement("select max(id)+1 from grad");
            //***
            ResultSet rez = max_Drzavaid.executeQuery();
            ResultSet rez1 = max_Gradid.executeQuery();
            int id = 1;
            if (rez.next()) id = rez.getInt(1);
            int id_gg = 1;
            if (rez1.next()) id_gg = rez1.getInt(1);
            //ovo
            //  dodaj_drzavu = connection.prepareStatement("insert into drzava values (?,?,?)");
            //**
            drzava.setId(id);
            dodaj_drzavu.setInt(1, id);
            dodaj_drzavu.setString(2, drzava.getNaziv());
            dodaj_drzavu.setInt(3, id_gg);
            dodaj_drzavu.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void izmijeniGrad(Grad grad) {
        try {
            update.setString(1, grad.getNaziv());
            update.setInt(2, grad.getBrojStanovnika());
            update.setInt(3, grad.getDrzava().getId());
            update.setInt(4, grad.getId());
            update.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Grad nadjiGrad(String grad) {
        try {
            daj_Gradove.setString(1, grad);
            ResultSet rez = daj_Gradove.executeQuery();
            if (!rez.next()) return null;
            else {
                Grad g = new Grad(rez.getInt(1), rez.getString(2), rez.getInt(3),nadjiDrzavu(rez.getString(4)));
                return g;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    void obrisiGrad(int id) {
        try {

                obrisi_grad.setInt(1, id);
                obrisi_grad.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
