package org.example.ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserHomePageController {

    @FXML
    private Button book1;

    @FXML
    private Button book2;

    @FXML
    private Button book3;

    @FXML
    private Button book4;

    @FXML
    private Button logoutButton; // Add this for the logout button

    @FXML
    private void initialize() {
        // You can perform initialization here if needed.
    }

    @FXML
    private void handleBookButtonAction(javafx.event.ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            String eventId = clickedButton.getId(); // This will be "book1", "book2", etc.

            // You can use the eventId to identify which event is being booked
            // For now, let's show an alert with the event ID
            showAlert("Book Event", "Book button clicked for Event: " + eventId);

            // Redirect to bp.fxml
            redirectToBookingPage();
        }
    }


    private void redirectToBookingPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Booking Page");
            stage.show();

            // Close the current user home page window (optional)
            Stage currentStage = (Stage) book1.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleLogoutButtonAction() {
        // Handle logout action
        redirectToLoginPage();
    }

    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

            // Close the current user home page window
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfileButtonAction() {
        // Handle profile button action
        redirectToProfilePage();
    }

    private void redirectToProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pp.fxml"));
            Parent root = loader.load();

            ppController ppController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Profile Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
