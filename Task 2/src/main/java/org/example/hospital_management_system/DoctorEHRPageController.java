package org.example.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DoctorEHRPageController {
    @FXML
    private Label patientId;
    @FXML
    private Label patientName;
    @FXML
    private Label gender;
    @FXML
    private Label bloodGroup;
    @FXML
    private Label dob;
    @FXML
    private Label contactNo;
    @FXML
    private Label dateOfVisit;
    @FXML
    private Label doctorsName;
    @FXML
    private Label diagnosis;
    @FXML
    private Label prescription;
    @FXML
    ComboBox<String> patientDropdown;
    @FXML
    private Button goToDashboardButton;
    private Map<String, Integer> patientMap = new HashMap<>();

    public void loadPatients() {
        try {
            Connection conn = DBConnection.getConnection();
            String stmt = """
                    SELECT p.patient_id, p.first_name, p.last_name
                    FROM patients p
                    JOIN appointments a ON p.patient_id = a.patient_id
                    WHERE a.doctor_id = ?;
                    """;
            PreparedStatement prepStmt = conn.prepareStatement(stmt);
            prepStmt.setInt(1, Integer.parseInt(Doctor.doctorId));

            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                String patientName = rs.getString("first_name") + " " + rs.getString("last_name");
                int patientId = rs.getInt("patient_id");
                patientDropdown.getItems().add(patientName);
                patientMap.put(patientName, patientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleViewEHR() {
        String selectedPatient = patientDropdown.getValue();
        if (selectedPatient.isEmpty()) {
            System.out.println("Please select a patient.");
            return;
        }
        int patientId = patientMap.get(selectedPatient);
        loadEHR(patientId);
    }
    public void loadEHR(int patientID){
        try {
            Connection conn = DBConnection.getConnection();
            String stmt = """
                SELECT p.*, a.*
                FROM patients p JOIN appointments a ON p.patient_id = a.patient_id
                WHERE a.doctor_id = ? and patient_id = ?;
                """;
            PreparedStatement prepStatement = conn.prepareStatement(stmt);
            prepStatement.setString(1, Doctor.doctorId);
            prepStatement.setString(2, String.valueOf(patientID));
            ResultSet result = prepStatement.executeQuery();
            result.next();

            patientId.setText("Patient Id : "+result.getString("patient_id"));
            patientName.setText("Patient's Name : "+result.getString("first_name")+" "+result.getString("last_name"));
            gender.setText("Gender : "+result.getString("gender"));
            bloodGroup.setText("Blood Group : "+result.getString("blood_type"));
            dob.setText("Date of Birth : "+result.getString("date_of_birth"));
            contactNo.setText("Contact Number : "+result.getString("contact_number"));

            dateOfVisit.setText("Date of Visit : "+result.getString("record_date"));
            doctorsName.setText("Doctor's Name : " +Doctor.doctorName);
            diagnosis.setText("Diagnosis : "+result.getString("diagnosis"));
            prescription.setText("Prescription : "+result.getString("prescription"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getClass());
            }
    }
    public void onGotoDashboardClick(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorDashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DoctorDashboardController controller = fxmlLoader.getController();
            controller.initialize();
            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage() + "\n" + e.getClass());
        }
    }
}