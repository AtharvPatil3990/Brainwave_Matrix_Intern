package org.example.hospital_management_system;

class CurrentUser{
    static String role;
    static String username, password, userid, table;
}

public class Patient {
    static String patientId, name, contactNo, gender,dob,address,bloodType, userId, email;
    public static void removeData(){
        patientId = "null";
        name = "null";
        contactNo = "null";
        gender = "null";
        dob = "null";
        address = "null";
        bloodType = "null";
        userId = "null";
    }
}

class Doctor{
    static String doctorId, doctorName, userId;
}
class Staff{
    String staffId, staffName, role, contact, shift;
}
