package ba.unsa.etf.rpr;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Zadaca2Controller {
    public TextField fldText;
    public ListView<String> lvStudents;
    public Slider sliderStudents;
    public ChoiceBox<String> choiceColor;
    public GridPane gridTipke;
    public Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    ObservableList<String> studenti1 = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        studenti1.add("Student1");
        studenti1.add("Student2");
        studenti1.add("Student3");
        studenti1.add("Student4");
        studenti1.add("Student5");
        ObservableList<String> studenti2 = FXCollections.observableArrayList();

        for (int i = 0; i < 5; i++) {
            studenti2.add(studenti1.get(i));
        }
        lvStudents.setItems(studenti2);

        choiceColor.setItems(FXCollections.observableArrayList("Default", "Crvena", "Zelena", "Plava"));
        sliderStudents.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal != null) {
                ObservableList<String> studenti = lvStudents.getItems();

                for (int i = studenti.size(); i < newVal.intValue(); i++) {
                    if(i==14) studenti.add("Student"+fldText.getText());
                    else
                    studenti.add("Student"+(i+1));
                }
                sliderStudents.setValue(newVal.intValue());
                lvStudents.setItems(studenti);
                lvStudents.refresh();
            }
        });

        choiceColor.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                        promijeniBoju(newValue)
                );

    }

    private void promijeniBoju(String newValue) {

        if (newValue.equals("Crvena")) {
            btn0.setId("crvena");
            btn1.setId("crvena");
            btn2.setId("crvena");
            btn3.setId("crvena");
            btn4.setId("crvena");
            btn5.setId("crvena");
            btn6.setId("crvena");
            btn7.setId("crvena");
            btn8.setId("crvena");
            btn9.setId("crvena");
        } else if (newValue.equals("Zelena")) {
            btn0.setId("zelena");
            btn1.setId("zelena");
            btn2.setId("zelena");
            btn3.setId("zelena");
            btn4.setId("zelena");
            btn5.setId("zelena");
            btn6.setId("zelena");
            btn7.setId("zelena");
            btn8.setId("zelena");
            btn9.setId("zelena");
        } else if (newValue.equals("Plava")) {
            btn0.setId("plava");
            btn1.setId("plava");
            btn2.setId("plava");
            btn3.setId("plava");
            btn4.setId("plava");
            btn5.setId("plava");
            btn6.setId("plava");
            btn7.setId("plava");
            btn8.setId("plava");
            btn9.setId("plava");
        } else if (newValue.equals("Default")) {
            btn0.setId("btn0");
            btn1.setId("btn1");
            btn2.setId("btn2");
            btn3.setId("btn3");
            btn4.setId("btn4");
            btn5.setId("btn5");
            btn6.setId("btn6");
            btn7.setId("btn7");
            btn8.setId("btn8");
            btn9.setId("btn9");
        }
    }


    @FXML
    private void click0(ActionEvent event) {
        fldText.setText(fldText.getText() + "0");
    }

    @FXML
    private void click1(ActionEvent event) {
        fldText.setText(fldText.getText() + "1");
    }

    @FXML
    private void click2(ActionEvent event) {
        fldText.setText(fldText.getText() + "2");
    }

    @FXML
    private void click3(ActionEvent event) {
        fldText.setText(fldText.getText() + "3");
    }

    @FXML
    private void click4(ActionEvent event) {
        fldText.setText(fldText.getText() + "4");
    }

    @FXML
    private void click5(ActionEvent event) {
        fldText.setText(fldText.getText() + "5");
    }

    @FXML
    private void click6(ActionEvent event) {
        fldText.setText(fldText.getText() + "6");
    }

    @FXML
    private void click7(ActionEvent event) {
        fldText.setText(fldText.getText() + "7");
    }

    @FXML
    private void click8(ActionEvent event) {
        fldText.setText(fldText.getText() + "8");
    }

    @FXML
    private void click9(ActionEvent event) {
        fldText.setText(fldText.getText() + "9");
    }

    public void otvoriNovi(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/novi.fxml"));

        Parent root1 = (Parent) fxmlLoader.load();
        NoviController ctrl=fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setTitle("Unos studenta");
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setOnHiding(w -> {

            String ime=ctrl.getFldIme().getText();
            System.out.println(ime);
            studenti1.add(ime+fldText.getText());
            lvStudents.getItems().add(ime+fldText.getText());
            lvStudents.refresh();
            sliderStudents.setValue(lvStudents.getItems().size());
        });
    }


}
