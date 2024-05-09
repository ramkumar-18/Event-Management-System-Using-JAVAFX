package org.example.ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class gpthpController {

    @FXML
    private ListView<String> eventListView;

    @FXML
    private void initialize() {
        // You can perform initialization here if needed.
        displayEventNames();
    }

    private void displayEventNames() {
        // Fetch event names from the database and populate the ListView
        List<String> eventNames = fetchEventNamesFromDatabase();
        eventListView.getItems().addAll(eventNames);

        // Set up a listener for item selection
        eventListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle event selection, e.g., redirect to "bp.fxml"
                showAlert("Selected Event", "You selected: " + newValue);
                redirectToBookingPage();
            }
        });
    }

    private List<String> fetchEventNamesFromDatabase() {
        List<String> eventNames = new ArrayList<>();

        // Use your own database connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/ems";
        String dbUsername = "root";
        String dbPassword = "Ram@1865";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT event_name FROM events";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String eventName = resultSet.getString("event_name");
                        eventNames.add(eventName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return eventNames;
    }

    private void redirectToBookingPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Booking Page");
            stage.show();

            // Close the current window (optional)
            Stage currentStage = (Stage) eventListView.getScene().getWindow();
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

    @FXML
    private void handleLogoutButtonAction() {
        // Handle logout action
        redirectToLoginPage();
    }

    private void redirectToProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pp.fxml"));
            Parent root = loader.load();

            // You may pass user information to the controller if needed
            ppController ppController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Profile Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) eventListView.getScene().getWindow();
            currentStage.close();

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
