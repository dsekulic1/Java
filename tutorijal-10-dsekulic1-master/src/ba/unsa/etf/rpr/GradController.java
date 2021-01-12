package ba.unsa.etf.rpr;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GradController implements Initializable {
    @FXML
    public TextField fieldNaziv;
    @FXML
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava;
    @FXML
    private javafx.scene.control.Button btnCancel;
    private ArrayList<Drzava> drzave;
    private GeografijaDAO geografijaDAO;
    private Grad grad = null;

    public GradController(GeografijaDAO geo, ArrayList<Drzava> d) {
        this.geografijaDAO = geo;
        this.drzave = d;
    }

    public boolean isStringInt(String s) {
        try {
            if (Integer.parseInt(s) < 0) return false;
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void setEmptyTextFields() {
        fieldNaziv.setText("");
        fieldBrojStanovnika.setText("");
    }

    @FXML
    public void validiraj(ActionEvent actionEvent) {
        boolean ispravno = true;
        if (!fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            ispravno = false;
        }
        if (!fieldBrojStanovnika.getText().trim().isEmpty() && isStringInt(fieldBrojStanovnika.getText())) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            ispravno = false;
        }
        if (ispravno) {
            grad = new Grad(null, fieldNaziv.getText(), Integer.parseInt(fieldBrojStanovnika.getText()), choiceDrzava.getSelectionModel().getSelectedItem());
            zatvori();
        }
        // setEmptyTextFields();
    }

    public Grad getGrad() {
        return grad;
    }

    @FXML
    public void zatvori() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceDrzava.getItems().addAll(drzave);
    }
}
