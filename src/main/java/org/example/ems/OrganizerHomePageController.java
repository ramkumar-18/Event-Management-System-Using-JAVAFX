package org.example.ems;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class OrganizerHomePageController {

    @FXML
    private Button newevent;

    @FXML
    private MenuItem logout;

    @FXML
    private void initialize() {
        // You can perform initialization here if needed.
    }

    @FXML
    private void handleNewEventButtonAction() {
        // Handle the action when the "New Event" button is clicked
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Event Creation.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Creation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutMenuItemAction() {
        // Handle the action when the "Log Out" menu item is clicked
        redirectToLoginPage();
    }

    private void redirectToLoginPage() {
        // Implement the logic to redirect to the login page (lp.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

            // Close the current organizer home page window
            Stage currentStage = (Stage) newevent.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
