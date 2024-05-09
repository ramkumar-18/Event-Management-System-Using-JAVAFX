package org.example.ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class spController {

    @FXML
    private TextField name;

    @FXML
    private TextField phoneno;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField retypepassword;

    @FXML
    private Button registerButton;


    @FXML
    private void initialize() {
        // You can perform initialization here if needed.
    }

    @FXML
    private void handleRegisterButtonAction() {
        String enteredName = name.getText();
        String enteredPhone = phoneno.getText();
        String enteredEmail = email.getText();
        String enteredPassword = password.getText();
        String enteredRetypePassword = retypepassword.getText();

        if (enteredName.isEmpty() || enteredPhone.isEmpty() || enteredEmail.isEmpty() || enteredPassword.isEmpty() || enteredRetypePassword.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all the fields.");
            return;
        }

        // Check if passwords match
        if (enteredPassword.equals(enteredRetypePassword)) {
            // Passwords match, proceed with registration
            if (registerUser(enteredName, enteredPhone, enteredEmail, enteredPassword)) {
                // Registration successful, show a pop-up
                showInformationAlert("Success", "Account created successfully!");
                redirectToLoginPage();
            } else {
                // Something went wrong during registration, show an alert
                showAlert("Error", "Username already taken. Please try again.");
            }
        } else {
            // Passwords do not match, show an alert
            showAlert("Password Mismatch", "Please enter the same password in both fields.");
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

            // Close the current registration window
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean registerUser(String name, String phone, String email, String password) {
        // TODO: Implement database insertion logic
        String jdbcUrl = "jdbc:mysql://localhost:3306/ems";
        String dbUsername = "root";
        String dbPassword = "Ram@1865";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "INSERT INTO user_credentials (name, phonenum, username, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

