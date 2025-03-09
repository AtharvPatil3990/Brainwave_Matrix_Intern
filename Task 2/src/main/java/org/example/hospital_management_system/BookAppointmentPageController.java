package org.example.hospital_management_system;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
public class BookAppointmentPageController {
    @FXML
    private ComboBox<String> doctorComboBox;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private Button submtButton;
    @FXML
    private TextField patientName;
    @FXML
    private TextField patientId;
    private HashMap<String, Integer> doctorMap = new HashMap<>();
    public void setPredefinedData(String username, String userId){
        patientName.setText(username);
        patientId.setText(userId);
    }
    public void setDoctorComboBox(){
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT doctor_name FROM doctors";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doctorComboBox.getItems().add(rs.getString("first_name")+" "+rs.getString("last_name"));
                doctorMap.put(rs.getString("doctorName"), rs.getInt("doctorId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onSubmitClick(ActionEvent event){
        LocalDate date = appointmentDatePicker.getValue();
        String selectedDoctor = doctorComboBox.getValue();
        int doctorId = doctorMap.get(selectedDoctor);
        if (selectedDoctor != null) {
            try {
                Connection conn = DBConnection.getConnection();
                String insertQuery = "INSERT INTO appointments  VALUES (?, ?, ?, 'scheduled')";
                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setInt(1, Integer.parseInt(Patient.patientId));
                stmt.setInt(2, doctorId);
                stmt.setString(3, String.valueOf(date));

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    showAlert("Success", "Appointment booked successfully!");
                } else {
                    showAlert("Error", "Failed to book appointment.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred: " + e.getMessage());
            }
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }
}
