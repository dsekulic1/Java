package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NoviController {
    public TextField fldIme;
    public ProgressBar progressBar;
    @FXML
    public void initialize() {
        progressBar.setProgress(0);
        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if(newIme!=null) {
                progressBar.setProgress(newIme.length()/10.);
                if (newIme.length() <10) {
                    progressBar.getStyleClass().removeAll("zeleniProgress");
                    progressBar.getStyleClass().add("crveniProgress");
                }
                else {
                    progressBar.getStyleClass().removeAll("crveniProgress");
                    progressBar.getStyleClass().add("zeleniProgress");
                }
            }
        });
    }
    public void cancel(ActionEvent actionEvent) {
        Stage st1=(Stage)fldIme.getScene().getWindow();
        st1.close();
    }

    public void ok(ActionEvent actionEvent) {
        if(fldIme.getText().length()<10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravno ime");
            alert.setHeaderText("Neispravno ime studenta");
            alert.setContentText(String.format("Ime studenta treba biti najmanje 10 karaktera dugačko"));
            alert.showAndWait();
        }
        else{
           Stage st1=(Stage)fldIme.getScene().getWindow();
            st1.close();
        }
    }

    public TextField getFldIme() {
        return fldIme;
    }
}
