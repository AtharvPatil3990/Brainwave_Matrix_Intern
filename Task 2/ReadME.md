🏥 Hospital Management System – Java Application  

This Hospital Management System is a Java-based application designed to manage hospital operations, including patient registration, appointment scheduling, electronic health records (EHR), billing and invoicing (excluding payment).
The system uses JavaFX for the user interface and MySQL for the database.

📋 Table of Contents
1. Features
2. Project Structure
3. Prerequisites
4. Installation & Setup
5. Database Schema
6. Usage Instructions
7. User Roles and Access
8. Future Enhancements
9. Troubleshooting
10. Author

 🌟 Features
- Appointment Management
    - Book appointments with doctors
    - View upcoming and past appointments
- Electronic Health Records (EHR)
    - Doctors can update patient medical history
    - Patients can view their medical records
- Billing Management
    - Generate and view invoices (without payment integration)
- Inventory Management
    - Track medical supplies and equipment
- Role-Based Access
    - Patient, Doctor dashboards with customized permissions

 📂 Project Structure

HospitalManagementSystem/
├── src/
│   ├── controllers/               // JavaFX Controllers
│   │       ├── HomePageController.java
│   │       ├── LoginController.java
│   │       ├── PatientDashboardController.java
│   │       ├── DoctorDashboardController.java
│   │       └── DashboardController.java
│   │
│   ├── models/                    // Java Models for Database Entities
│   │       ├── User.java
│   │       ├── Patient.java
│   │       ├── Appointment.java
│   │       └── Bill.java
│   │
│   ├── database/                  // Database Connectivity
│   │       └── DBConnection.java
│   │
│   ├── views/                     // FXML Files for UI Layouts
│   │       ├── home_page.fxml
│   │       ├── login_page.fxml
│   │       ├── patient_dashboard.fxml
│   │       ├── doctor_dashboard.fxml
│   │       └── _dashboard.fxml
│   │
│   └── Main.java                  // Application Entry Point
│
└── README.md                      // Project Documentation


🛠️ Prerequisites
Ensure you have the following installed:
- Java 17 (or newer)
- JavaFX SDK (properly configured in your IDE)
- MySQL Server (running locally or remotely)
- IDE: IntelliJ IDEA, Eclipse, or any Java-compatible IDE


 🚀 Installation & Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/hospital-management-system.git
   cd hospital-management-system
   ```

2. Set up the Database:
   - Ensure MySQL is running.
   - Execute the provided schema in MySQL:
     ```bash
     mysql -u your_user -p your_database < hospital_schema.sql
     ```

3. Configure Database Connection:
   Update your `DBConnection.java` with your MySQL credentials:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
   private static final String USER = "your_user";
   private static final String PASSWORD = "your_password";
   ```

4. Run the Application:
   Compile and run the `Main.java`:
   ```bash
   javac Main.java
   java Main
   ```

🗃️ Database Schema
  Users Table
```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('patient', 'doctor', '') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(50) UNIQUE NOT NULL
);
```
# Patients Table
```sql
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender ENUM('male', 'female', 'other'),
    date_of_birth DATE,
    contact_number VARCHAR(15),
    address TEXT,
    blood_type ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'),
    medical_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

# Appointments Table
```sql
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATETIME NOT NULL,
    status ENUM('scheduled', 'completed', 'cancelled') DEFAULT 'scheduled',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);
```


📊 User Roles and Access
|------------------------------------------------------------------|
| Role         | Permissions                                       |
|------------------|-----------------------------------------------|
| Patient      | Book appointments, view EHR, check bills          |
| Doctor       | View patients, update EHR, view schedules         |
-------------------------------------------------------------------|


📖 Usage Instructions
1. Home Page: Choose your role (Patient, Doctor, ).  
2. Login: Enter your credentials.  
3. Dashboard: Navigate through the available options for your role:
   - Patients: Book appointments, view bills.
   - Doctors: Access patient data, update EHR.


🚧 Future Enhancements
- Implement search and filter for patient records.
- Add report generation (monthly patient visits, revenue, etc.).
- User Profile Management for updating patient/doctor information.
- Enhance security with password hashing and session management.
- Patient regestration and staff managing.


🛠️ Troubleshooting
1. Connection Issues:  
   Ensure your MySQL server is running and your credentials are correct in `DBConnection.java`.
   
2. UI Not Loading:  
   Verify that the `FXML` paths are correct in the `Main.java` and controllers.

3. Errors in Data Insertion:  
   Check the database schema for ENUM values and ensure all fields are properly populated.


👤 Author
- Atharv Ajit Patil
- Email: atharvapatil3990@gmal.com
- GitHub: AtharvPatil3990
