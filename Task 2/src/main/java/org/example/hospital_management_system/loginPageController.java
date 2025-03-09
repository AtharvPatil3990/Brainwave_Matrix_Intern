package org.example.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class loginPageController {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;

    static Connection conn;
    static ResultSet result;

    public void onLoginButtonClick(ActionEvent event) throws SQLException {
        CurrentUser.username = usernameField.getText();
        CurrentUser.password = passwordField.getText();
        try {
            if (CurrentUser.username.isEmpty() || CurrentUser.password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Username and Password cannot be empty.");
                return;
            }

            else if (authenticateUser(CurrentUser.username, CurrentUser.password, CurrentUser.role)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + CurrentUser.username + "!");
                // Navigate to the user's dashboard (patient, doctor, admin, etc.)
                // Example: switchToDashboard();
                setUserData();
                if(CurrentUser.role.equals("patient")){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientDashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    PatientDashboardController patientCont = fxmlLoader.getController();
                    patientCont.setWelcomeLabel();
                    stage.show();
                }
                else if(CurrentUser.role.equals("doctor")){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DoctorDashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    DoctorDashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.initialize();
                    stage.show();
                }
            }
            else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.\nPlease try again.");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void setUserData(){
        try{
            conn = DBConnection.getConnection();
            String stmt = "Select * from "+CurrentUser.table+" where user_id = ?";
            PreparedStatement prepStmt = conn.prepareStatement(stmt);
            System.out.println(stmt+" "+CurrentUser.userid);
            prepStmt.setInt(1,Integer.parseInt(CurrentUser.userid));
            System.out.println("Before getting result");
            ResultSet result = prepStmt.executeQuery();
            System.out.println("Before getting result 2");
            result.next();
            System.out.println("After getting result");
            if(CurrentUser.role.equals("patient")) {
                Patient.userId = result.getString("user_id");
                Patient.patientId = result.getString("patient_id");
                Patient.bloodType = result.getString("blood_type");
                Patient.dob = result.getString("date_of_birth");
                Patient.name = result.getString("first_name") + " " + result.getString("last_name");
                Patient.address = result.getString("address");
                Patient.contactNo = result.getString("contact_number");
                Patient.gender = result.getString("gender");

                System.out.println(Patient.userId);
                System.out.println(Patient.name);
            }
            else if (CurrentUser.role.equals("doctor")) {
                Doctor.doctorId = result.getNString("doctor_id");
                Doctor.doctorName = "Dr. "+result.getString("first_name") + result.getString("last_name");
                Doctor.userId = CurrentUser.userid;
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"\n"+e.getClass());
        }
    }
    private static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static boolean authenticateUser(String username, String password, String role) {
        try {
            conn = DBConnection.getConnection();
            String stmt = "Select * from users where username = ? and password_hash = ? and role = ?";
            PreparedStatement prepStmt = conn.prepareStatement(stmt);

            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            prepStmt.setString(3, role);
//            System.out.println("Username: "+username+"\nPass: "+password+"\nRole:"+role);
            result = prepStmt.executeQuery();
            result.next();
            if (result.getString("username").equals(username) && result.getString("password_hash").equals(password)){
                CurrentUser.userid = result.getString("user_id");
                return true;
            }
        }
        catch (Exception e) {
            System.out.println("Authenticate user block");
            System.out.println(e.getMessage() + "\n" + e.getClass());
        }
        return false;
    }
}
