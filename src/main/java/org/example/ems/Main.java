package org.example.ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lp.fxml"));
        Parent root = loader.load();

        // Set up the stage
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
