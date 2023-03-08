package lk.ijse.fx_cust_form.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/fx_cust_form/view/customerform.fxml"));
        primaryStage.setTitle("Customer Management Form");
        primaryStage.setScene(new Scene(root, 505, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
