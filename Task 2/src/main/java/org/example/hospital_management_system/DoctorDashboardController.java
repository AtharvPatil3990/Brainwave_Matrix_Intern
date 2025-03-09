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

public class DoctorDashboardController {
    @FXML
    Label welcomeLabel;
    @FXML
    Button viewAppointmentButton;
    @FXML
    Button viewPatientEHRButton;
    @FXML
    Button logoutButton;

    public void initialize(){
        welcomeLabel.setText("Welcome, "+Doctor.doctorName);
    }
    @FXML
    public void onViewAppointmentClick(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorAppointmentPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DoctorAppointmentPageController controller = fxmlLoader.getController();
            controller.initialize();
            stage.show();
        }catch(Exception e){
            System.out.println(e.getClass()+"\n"+e.getMessage());
        }
    }
    @FXML
    public void onViewPatientEHRClick(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorEHRPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DoctorEHRPageController controller = fxmlLoader.getController();
            controller.loadPatients();
            stage.show();
        }catch(Exception e){
            System.out.println(e.getClass()+"\n"+e.getMessage());
        }
    }
    @FXML
    public void onLogoutClick(ActionEvent event){
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
