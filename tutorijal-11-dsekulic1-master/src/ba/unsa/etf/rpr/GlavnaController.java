package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GlavnaController implements Initializable {
    // Metoda za potrebe testova, vraća bazu u polazno stanje
    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
    }
    private GeografijaDAO geografijaDAO;
    public TableView<Grad> tableViewGradovi;
    public TableColumn<Grad, Integer> colGradId;
    public TableColumn<Grad, String> colGradNaziv;
    public TableColumn<Grad, Integer> colGradStanovnika;
    public TableColumn<Grad, Drzava> colGradDrzava;

    @FXML
    public void otvoriProzorDrzava(ActionEvent actionEvent) {
        //geografijaDAO = GeografijaDAO.getInstance();
        //resetujBazu();
        try {
            geografijaDAO = GeografijaDAO.getInstance();
            DrzavaController ctrl = new DrzavaController(null, GeografijaDAO.getInstance().gradovi());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
            fxmlLoader.setController(ctrl);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Država");
            stage.setScene(new Scene(root1));
            stage.show();
           // resetujBazu();
            stage.setOnHiding(event -> {
                Drzava drzava = ctrl.getDrzava();
                if (drzava != null) {
                    geografijaDAO.dodajDrzavu(drzava);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void otvoriProzorGrad(ActionEvent actionEvent) {
        try {
            geografijaDAO = GeografijaDAO.getInstance();
            GradController ctrl = new GradController(null, GeografijaDAO.getInstance().drzave());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
            fxmlLoader.setController(ctrl);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Grad");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setOnHiding(event -> {
                Grad grad = ctrl.getGrad();
                if (grad != null) {
                    geografijaDAO.dodajGrad(grad);
                    tableViewGradovi.getItems().add(grad);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void izmijeniGrad()  throws IOException {
        geografijaDAO = GeografijaDAO.getInstance();

        if (tableViewGradovi.getSelectionModel().getSelectedItem() != null) {
            GradController ctrl = new GradController(null, GeografijaDAO.getInstance().drzave());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
            fxmlLoader.setController(ctrl);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            ctrl.fieldNaziv.setText(tableViewGradovi.getSelectionModel().getSelectedItem().getNaziv());
            ctrl.fieldBrojStanovnika.setText(tableViewGradovi.getSelectionModel().getSelectedItem().getBrojStanovnika().toString());
           ctrl.choiceDrzava.getSelectionModel().select(tableViewGradovi.getSelectionModel().getSelectedItem().getDrzava());

            stage.setTitle("Grad");
            //stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

            stage.setScene(new Scene(root1));
            stage.show();

            stage.setOnHiding(event -> {
                Grad grad = ctrl.getGrad();
                if (grad != null) {
                    grad.setId(tableViewGradovi.getSelectionModel().getSelectedItem().getId());
                    geografijaDAO.izmijeniGrad(grad);
                    tableViewGradovi.getSelectionModel().getSelectedItem().setNaziv(grad.getNaziv());
                    tableViewGradovi.getSelectionModel().getSelectedItem().setBrojStanovnika(grad.getBrojStanovnika());
                    tableViewGradovi.getSelectionModel().getSelectedItem().setDrzava(grad.getDrzava());
                    tableViewGradovi.refresh();
                }
            });
        }
    }
    public void obrisiGrad() throws IOException {
        Grad grad = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (grad != null) {
            geografijaDAO = GeografijaDAO.getInstance();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Zelite li obrisati ovaj grad?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
               geografijaDAO.obrisiGrad(grad.getId());
                tableViewGradovi.getItems().remove(grad);
                tableViewGradovi.refresh();
            }

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        geografijaDAO=GeografijaDAO.getInstance();
        colGradId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGradNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        colGradStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
        colGradDrzava.setCellValueFactory(new PropertyValueFactory<>("drzava"));
        tableViewGradovi.getItems().setAll(GeografijaDAO.getInstance().gradovi());
        GeografijaDAO.removeInstance();
    }
}
