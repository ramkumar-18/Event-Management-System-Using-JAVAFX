package org.example.ems;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class bpController implements Initializable {

    @FXML
    private Label ename;

    @FXML
    private Label edate;

    @FXML
    private Label etime;

    @FXML
    private Label eprice;

    @FXML
    private TextField nooftickets;

    @FXML
    private TextField scategory;

    @FXML
    private Label total;

    @FXML
    private RadioButton netBanking;

    @FXML
    private RadioButton upi;

    @FXML
    private RadioButton onlinePayment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch and set data when the controller is initialized
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        try {
            // Fetch data from the database
            String jdbcUrl = "jdbc:mysql://localhost:3306/ems";
            String username = "root";
            String password = "Ram@1865";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // Replace the SQL query with your actual query
                String sql = "SELECT * FROM events WHERE eventid = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, 1); // Replace with the actual event_id

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // Set labels with data from the database
                            ename.setText(resultSet.getString("event_name"));
                            edate.setText(resultSet.getString("eventdate"));
                            etime.setText(resultSet.getString("eventtime"));
                            eprice.setText(resultSet.getString("eventticketprice"));

                            // Calculate total price
                            int numOfTickets = Integer.parseInt(nooftickets.getText());
                            double pricePerTicket = resultSet.getDouble("eventticketprice"); // Replace with your actual price
                            double totalPrice = numOfTickets * pricePerTicket;

                            // Display total price in an alert box
                            showAlert("Total Price", "Total Price for " + numOfTickets + " tickets: $" + totalPrice);
                        } else {
                            showAlert("Error", "Event not found in the database.");
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
}
