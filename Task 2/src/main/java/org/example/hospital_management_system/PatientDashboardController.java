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

public class PatientDashboardController {
    @FXML
    Label welcomeLabel;
    @FXML
    Button bookAppButton;
    @FXML
    Button viewMedHist;
    @FXML
    Button billsButton;

    public void setWelcomeLabel(){
        welcomeLabel.setText("Welcome, " + Patient.name);
    }
    @FXML
    public void onBookAppointmentClick(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookAppointmentPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            BookAppointmentPageController bookAppointment = fxmlLoader.getController();
            bookAppointment.setDoctorComboBox();
            bookAppointment.setPredefinedData(Patient.name, Patient.userId);

            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage()+"\n"+e.getClass());
        }
    }
    @FXML
    public void onViewMedHistClick(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MedicalHistory.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            MedicalHistoryController controller = fxmlLoader.getController();
            controller.setMedicalHistoryData();
            stage.show();
        }catch(Exception e){
            System.out.println(e.getClass()+"\n"+e.getMessage());
        }
    }
    @FXML
    public void onBillsClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BillsPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            BillsPageController controller = fxmlLoader.getController();
            controller.initialize();
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getClass() + "\n" + e.getMessage());
        }
    }
    @FXML
    public void onLogoutClick(ActionEvent event){
        Patient.removeData();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            System.out.println(e.getClass()+"\n"+e.getMessage());
        }
    }
}
