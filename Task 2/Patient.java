package org.example.hospital_management_system;

import java.sql.Time;
import java.util.Date;

class CurrentUser{
    static String role;
    static String username, password;
}

public class Patient {
    String patientId, patientName, contactNo;
    int age;

}

class Appointment{
    String appointmentId, patientId, doctorId;
    Date appointmentDate;
    Time appointmentTime;
    String appointmentStatus;
}
class Bill{
    String billId, patientId, services;
    double amount;
    boolean billStatus;
}
class InventoryItem{
    String itemId, itemName, supplier;
    int quantity;
}
class Staff{
    String staffId, staffName, role, contact, shift;
}
