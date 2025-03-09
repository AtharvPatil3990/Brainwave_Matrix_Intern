package org.example.hospital_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorAppointmentPageController {
    @FXML
    Button backButton;
    @FXML
    Button refreshButton;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> colAppointmentID;
    @FXML private TableColumn<Appointment, String> colPatientName;
    @FXML private TableColumn<Appointment, String> colAppointmentDate;
    @FXML private TableColumn<Appointment, String> colStatus;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public void initialize() {
        // Link columns with Appointment class properties
        colAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadAppointmentsFromDB();
    }

    private void loadAppointmentsFromDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = """
                SELECT a.appointment_id, p.first_name, p.last_name, a.appointment_date, a.status
                FROM appointments a
                JOIN patients p ON a.patient_id = p.patient_id
                WHERE a.doctor_id = ? ORDER BY a.appointment_date DESC;
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(Doctor.doctorId));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("appointment_id");
                String patientName = rs.getString("first_name") + " " + rs.getString("last_name");
                String date = rs.getTimestamp("appointment_date").toString();
                String status = rs.getString("status");

                appointmentList.add(new Appointment(id, patientName, date, status));
            }

            appointmentTable.setItems(appointmentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onBackButtonClick(ActionEvent event){
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorDashboard.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                DoctorDashboardController dashboardController = fxmlLoader.getController();
                dashboardController.initialize();
                stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"\n"+e.getClass());
        }
    }
    public void onRefreshButtonClick(ActionEvent event){
        loadAppointmentsFromDB();
    }
}

class Appointment {
    private final int appointmentId;
    private final String patientName;
    private final String appointmentDate;
    private final String status;

    public Appointment(int appointmentId, String patientName, String appointmentDate, String status) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    public int getAppointmentId() { return appointmentId; }
    public String getPatientName() { return patientName; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getStatus() { return status; }
}
