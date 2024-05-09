package org.example.ems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ppController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private Label phonenum;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch and set data when the controller is initialized
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        try {
            // Fetch data from the database
            String jdbcUrl = "jdbc:mysql://localhost:3306/ems";
            String dbUsername = "root";
            String dbPassword = "Ram@1865";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
                // Replace the SQL query with your actual query
                String sql = "SELECT * FROM user_credentials WHERE username = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Replace "user@example.com" with the actual email you want to fetch
                    preparedStatement.setString(1, "ram@123");

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // Set labels with data from the database
                            name.setText(resultSet.getString("name"));
                            username.setText(resultSet.getString("username"));
                            phonenum.setText(resultSet.getString("phonenum"));
                        } else {
                            showAlert("Error", "User not found in the database.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
            showAlert("Error", "Failed to fetch data from the database. Please try again.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void handleBackButtonAction() {
        // Handle back button action
        rollbackToPreviousPage();
    }

    private void rollbackToPreviousPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Event User Home Page.fxml"));
            Parent root = loader.load();

            UserHomePageController userHomePageController = loader.getController();
            // Set any necessary data in the userHomePageController if needed

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User Home Page");
            stage.show();

            // Close the current profile page window
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
