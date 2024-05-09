package org.example.ems;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class lpController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbutton;

    @FXML
    private Button signuplink;

    @FXML
    private void initialize() {
        // You can perform initialization here if needed.
    }

    @FXML
    private void handleLoginButtonAction() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            showAlert("Empty Fields", "Please fill in both Username and Password.");
            return;
        }

        if (isPasswordCorrect(enteredUsername, enteredPassword)) {
            if (enteredUsername.endsWith("@srp.in")) {
                // Redirect to Event Organiser Home Page
                redirectToEventOrganiserHomePage();
            } else {
                // Redirect to Event User Home Page
                redirectToEventUserHomePage();
            }
        } else {
            // Show alert for incorrect password
            showAlert("Incorrect Password", "Enter Valid Password");
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up Page");
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) loginbutton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isPasswordCorrect(String username, String enteredPassword) {
        // TODO: Implement database check for the correct password
        // Use your own database connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/ems";
        String dbUsername = "root";
        String dbPassword = "Ram@1865";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT password FROM user_credentials WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        return enteredPassword.equals(storedPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void redirectToEventUserHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Event User Home Page.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event User Home Page");
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) loginbutton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToEventOrganiserHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Event Homepage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Organiser Home Page");
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) loginbutton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
