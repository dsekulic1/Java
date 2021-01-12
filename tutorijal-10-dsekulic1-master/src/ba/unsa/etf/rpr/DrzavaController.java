package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DrzavaController implements Initializable {
    @FXML
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public JColorChooser choiceDrzava;
    private GeografijaDAO geografijaDAO;
    private ArrayList<Grad> gradovi;
    private Drzava drzava = null;
    public ChoiceBox<Grad> choiceGrad;

    public DrzavaController(GeografijaDAO geo, ArrayList<Grad> gradovi) {
        this.geografijaDAO = geo;
        this.gradovi = gradovi;
    }

    public DrzavaController() {
    }

    public void validiraj(ActionEvent actionEvent) {
        if (!fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
            if (choiceGrad.getSelectionModel().getSelectedItem() == null) {
                drzava = new Drzava(null, fieldNaziv.getText(), choiceGrad.getItems().get(0));
            } else {
                drzava = new Drzava(null, fieldNaziv.getText(), choiceGrad.getSelectionModel().getSelectedItem());
            }
            zatvori();
        }
        else {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
        }

    }
    @FXML
    private javafx.scene.control.Button btnCancel;

    @FXML
    private void zatvori(){
        GeografijaDAO.getInstance();
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    public Drzava getDrzava() {
        return drzava;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceGrad.getItems().addAll(gradovi);
    }
}
