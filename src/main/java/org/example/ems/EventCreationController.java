package org.example.ems;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventCreationController {

    @FXML
    private TextField eventname;

    @FXML
    private TextField eventtype;

    @FXML
    private DatePicker eventdate;

    @FXML
    private TextField eventtime;

    @FXML
    private TextField eventduration;

    @FXML
    private TextField eventvenue;

    @FXML
    private TextField eventagegroup;

    @FXML
    private TextField eventattendees;

    @FXML
    private TextField eventbudjet;

    @FXML
    private TextField eventtickettype;

    @FXML
    private TextField eventticketprice;

    @FXML
    private RadioButton eventfood;

    @FXML
    private RadioButton eventparking;

    @FXML
    private Button createevent;

    private ToggleGroup foodToggleGroup;
    private ToggleGroup parkingToggleGroup;

    @FXML
    private void initialize() {
        // Initialize ToggleGroups for radio buttons
        foodToggleGroup = new ToggleGroup();
        parkingToggleGroup = new ToggleGroup();

        eventfood.setToggleGroup(foodToggleGroup);
        eventparking.setToggleGroup(parkingToggleGroup);
    }

    @FXML
    private void handleCreateEventButtonAction() {
        // Handle the action when the "Create Event" button is clicked
        saveEventDataToDatabase();
    }

    private void saveEventDataToDatabase() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "Ram@1865");

            // Prepare the SQL statement for inserting data
            String sql = "INSERT INTO events (event_name, eventtype, eventdate, eventtime, evenduration, eventvenue, agegroup, parking, expectedattendees, budjet, food, eventticketprice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the values for the SQL statement from the input fields
            statement.setString(1, eventname.getText());
            statement.setString(2, eventtype.getText());
            statement.setDate(3, java.sql.Date.valueOf(eventdate.getValue()));
            statement.setString(4, eventtime.getText());
            statement.setString(5, eventduration.getText());
            statement.setString(6, eventvenue.getText());
            statement.setString(7, eventagegroup.getText());
            statement.setBoolean(8, eventparking.isSelected());
            statement.setInt(9, Integer.parseInt(eventattendees.getText()));
            statement.setDouble(10, Double.parseDouble(eventbudjet.getText()));
            statement.setBoolean(11, eventfood.isSelected());
            statement.setDouble(12, Double.parseDouble(eventticketprice.getText()));

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Close the resources
            statement.close();
            connection.close();

            // Print the result
            if (rowsAffected > 0) {
                showAlert("Success", "Event created successfully!");
            } else {
                showAlert("Error", "Failed to create the event. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create the event. Please try again.");
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
