package org.example.hospital_management_system;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillsPageController {
    @FXML
    private Label patientName;
    @FXML
    private Label patientId;
    @FXML
    private Label totalBillLabel;
    @FXML
    private Label billStatusLabel;
    @FXML
    private Button gotoPatientDashboard;

    @FXML private TableView<BillItem> billTable;
    @FXML private TableColumn<BillItem, String> serviceColumn;
    @FXML private TableColumn<BillItem, Double> amountColumn;
    private ObservableList<BillItem> billItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Link columns to model properties
        int bill_id = 0;
        try{
            Connection conn = DBConnection.getConnection();
            String stmt = "Select bill_id from billing where patient_id = ?";
            PreparedStatement prepStmt = conn.prepareStatement(stmt);
            prepStmt.setString(1,Patient.patientId);
            ResultSet result = prepStmt.executeQuery();
            result.next();
            bill_id = result.getInt("bill_id");
            if(result.wasNull()){
                totalBillLabel.setText("Total Amount : " + 0.00+" Rs.");
                billStatusLabel.setText("");
            }
            totalBillLabel.setText("Total Amount : " + result.getDouble("total_amount")+" Rs.");
            billStatusLabel.setText("Bill Status : " + result.getString("bill_status"));
        }catch(Exception e){
            System.out.println(e.getMessage()+"\n"+e.getClass());
        }
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        billTable.setItems(billItems);

        patientId.setText("Patient ID : "+Patient.patientId);
        patientName.setText("Patient Name : "+Patient.name);

        loadBillItems(bill_id);
    }

    // Load bill items from database
    public void loadBillItems(int billID) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT service_name, amount FROM bill_items WHERE bill_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, billID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.wasNull())
                    break;
                String serviceName = rs.getString("service_name");
                double amount = rs.getDouble("amount");
                billItems.add(new BillItem(serviceName, amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onGotoDashboardClick(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientDashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            PatientDashboardController patientCont = fxmlLoader.getController();
            patientCont.setWelcomeLabel();
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"\n"+e.getClass());
        }
    }
}
class BillItem {
    private final SimpleStringProperty serviceName;
    private final SimpleDoubleProperty amount;

    public BillItem(String serviceName, double amount) {
        this.serviceName = new SimpleStringProperty(serviceName);
        this.amount = new SimpleDoubleProperty(amount);
    }
    public String getServiceName() {
        return serviceName.get();
    }
    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }
    public double getAmount() {
        return amount.get();
    }
    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}