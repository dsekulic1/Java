package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//       Zadaca2Model model = new Zadaca2Model();
//       model.napuni();
//       Zadaca2Controller ctrl = new Zadaca2Controller(model);
//
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/zadaca2.fxml"));
        Parent root = loader.load();
        Stage newStage=new Stage();
        newStage.setResizable(true);
        newStage.setTitle("Studenti");
        newStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        newStage.show();
//        //primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);

    }
}
