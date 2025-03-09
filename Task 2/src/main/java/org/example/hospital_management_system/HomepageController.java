package org.example.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HomepageController{
    @FXML
    Button exitButton;
    @FXML
    Button doctorLoginButton;
    @FXML
    Button patientLoginButton;

    public void onExitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program?");
        alert.setContentText("Are you sure you want to Exit?");
        alert.showAndWait();
    }
    @FXML
    public void onPatientClick(ActionEvent event)  {
        CurrentUser.role = "patient";
        CurrentUser.table = "patients";
//        switchToLoginPage(event);
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"Error occurred!");
        }
    }
    @FXML
    public void onDoctorClick(ActionEvent event) {
        CurrentUser.role = "doctor";
        CurrentUser.table = "doctors";
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"Error occurred!");
        }
    }

    @FXML
    public void onAdminClick(ActionEvent event) {
        CurrentUser.role = "admin";
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"Error occurred!");
        }
    }
    @FXML
    public void onReceptionistClick(ActionEvent event) {
        CurrentUser.role = "staff";
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"Error occurred!");
        }
    }
}