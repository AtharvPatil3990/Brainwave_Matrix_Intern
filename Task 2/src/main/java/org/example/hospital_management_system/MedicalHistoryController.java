package org.example.hospital_management_system;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MedicalHistoryController {
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
    private Button goToDashboardButton;

    public void setMedicalHistoryData() {
        try {
            Connection conn = DBConnection.getConnection();
            String stmt = """
                    SELECT e.*, d.first_name as docfname, d.last_name AS doclname
                    FROM ehr e
                    LEFT JOIN doctors d ON e.doctor_id = d.doctor_id
                    WHERE e.patient_id = ?
                    ORDER BY e.record_date DESC
                    LIMIT 1;
                    """;
            PreparedStatement prepStatement = conn.prepareStatement(stmt);
            prepStatement.setString(1, Patient.patientId);
            ResultSet result = prepStatement.executeQuery();
            result.next();

            patientId.setText("Patient Id : "+Patient.patientId);
            patientName.setText("Patient's Name : "+Patient.name);
            gender.setText("Gender : "+Patient.gender);
            bloodGroup.setText("Blood Group : "+Patient.bloodType);
            dob.setText("Date of Birth : "+Patient.dob);
            contactNo.setText("Contact Number : "+Patient.contactNo);

            dateOfVisit.setText("Date of Visit : "+result.getString("record_date"));
            doctorsName.setText("Doctor's Name : Dr. " + result.getString("docfname") + " "+result.getString("doclname"));
            diagnosis.setText("Diagnosis : "+result.getString("diagnosis"));
            prescription.setText("Prescription : "+result.getString("prescription"));
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getClass());
        }
    }
    public void onGotoDashboardClick(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientDashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            PatientDashboardController controller = fxmlLoader.getController();
            controller.setWelcomeLabel();
            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage() + "\n" + e.getClass());
        }
    }

}
