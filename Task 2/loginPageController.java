package org.example.hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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
        if (CurrentUser.username.isEmpty() || CurrentUser.password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Username and Password cannot be empty.");
            return;
        }

        if (authenticateUser(CurrentUser.username, CurrentUser.password, CurrentUser.role)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + CurrentUser.username + "!");
            // Navigate to the user's dashboard (patient, doctor, admin, etc.)
            // Example: switchToDashboard();
        }
//        else {
//            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
//        }


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
            System.out.println("Username: "+username+"\nPass: "+password+"\nRolel:"+role);
            result = prepStmt.executeQuery();
            result.next();
            if (result.getString("username").equals(username) && result.getString("password_hash").equals(password)){
                System.out.println("Patient found");
                return true;
            }
            return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage() + "\n" + e.getClass());
            showAlert(Alert.AlertType.WARNING,"No result found",e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getClass());
        }

        return false;
    }
}
