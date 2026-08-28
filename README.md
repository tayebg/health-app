# E-Health / Tabib - Medical & Health Management System

A Java desktop application built with Swing for patient vital tracking, doctor consultations, and medical administrative management. Supports both **PostgreSQL** and **MySQL** databases with automatic table provisioning and offline fallback mode.

---

## Features

- **Role-Based Access Control**:
  - **Patients**: Record daily/weekly health metrics (Temperature, Blood Pressure, Weight) and view historical trend charts.
  - **Doctors**: View assigned patients, inspect patient vital histories, and monitor visual health charts.
  - **Administrators**: Doctor management portal with CRUD operations (Add, View, Modify, Delete).
- **Interactive Visualizations**: Dynamic bar chart graphing for Temperature (°C), Blood Pressure (mmHg), and Weight (kg) across weeks and days.
- **Multi-Database Support**:
  - **PostgreSQL**: Auto-detection, automatic table schema initialization (Auto-DDL), and seed data.
  - **MySQL / MariaDB**: Compatible with standard XAMPP, WAMP, and phpMyAdmin configurations.
  - **Offline Fallback**: In-memory demo mode when database services are offline.
- **Clean UI**: Custom gradient panels, responsive layouts, and modern icon assets.

---

## Tech Stack & Dependencies

- **Language**: Java 21 (Compatible with Java 8+)
- **UI Framework**: Java Swing & SwingX (lib/swingx-all-1.6.4.jar)
- **Database Drivers**:
  - PostgreSQL JDBC Driver (lib/postgresql-42.7.4.jar)
  - MySQL Connector/J (lib/mysql-connector-j-9.1.0.jar)
- **Build / Run**: Batch script (un.bat) or direct javac / java commands

---

## Project Structure

`	ext
├── src/
│   ├── ehealth/                # Primary application package
│   │   ├── PreLoginPage.java   # Application entry point & login dialog
│   │   ├── signup.java         # Patient & Doctor registration with dynamic doctor assignment
│   │   ├── form.java           # Health metrics data entry form
│   │   ├── Select.java         # Health metrics navigation dashboard
│   │   ├── temperature.java    # Temperature bar chart visualization
│   │   ├── tension.java        # Blood pressure bar chart visualization
│   │   ├── weight.java         # Weight bar chart visualization
│   │   ├── patTable.java       # Doctor portal: assigned patients list
│   │   ├── tableuser.java      # Admin portal: doctor CRUD management
│   │   ├── DBConnection.java   # Auto-detecting PostgreSQL / MySQL database manager
│   │   ├── User.java           # User data model
│   │   ├── UserManager.java    # In-memory user management
│   │   └── AppTest.java        # Integration and database test suite
│   └── pages/                  # Compatible page components
├── lib/                        # External dependencies (JARs)
│   ├── mysql-connector-j-9.1.0.jar
│   ├── postgresql-42.7.4.jar
│   └── swingx-all-1.6.4.jar
├── icons/                      # Application icons and background assets
├── schema_postgres.sql         # PostgreSQL DDL and seed data
├── tabib.sql                   # MySQL tabib schema dump
├── ehealth.sql                 # MySQL ehealth schema dump
├── db.properties               # Active database configuration
├── db.properties.example       # Database configuration template
├── run.bat                     # Windows build and run launcher
└── README.md
`

---

## Requirements

- Java Development Kit (JDK) 8 or higher (JDK 21 recommended)
- PostgreSQL (optional, port 5432) or MySQL (optional, port 3306)

---

## Database Setup

### 1. PostgreSQL Setup (Recommended)

1. Make sure your local PostgreSQL service is running on port 5432.
2. Configure credentials in db.properties:
   `properties
   db.type=postgresql
   db.host=localhost
   db.port=5432
   db.name=postgres
   db.user=postgres
   db.password=postgres
   `
3. Tables (dmin, med, patient, status) and default seed data are created automatically on first run. You can also manually import schema_postgres.sql:
   `ash
   psql -U postgres -d postgres -f schema_postgres.sql
   `

### 2. MySQL Setup (Alternative)

1. Start Apache and MySQL in XAMPP / WAMP.
2. Import 	abib.sql into phpMyAdmin or run:
   `ash
   mysql -u root -p tabib < tabib.sql
   `
3. Update db.properties:
   `properties
   db.type=mysql
   db.host=localhost
   db.port=3306
   db.name=tabib
   db.user=root
   db.password=
   `

---

## How to Run

### Option 1: Quick Launch (Windows)
Double-click un.bat or execute in terminal:
`cmd
run.bat
`

### Option 2: Command Line Compilation & Run

`powershell
# Compile all source files
javac -d bin -cp "bin;lib/postgresql-42.7.4.jar;lib/swingx-all-1.6.4.jar;lib/mysql-connector-j-9.1.0.jar" (Get-ChildItem -Path "src" -Recurse -Filter *.java | ForEach-Object { .FullName })

# Launch Application
java -cp "bin;lib/postgresql-42.7.4.jar;lib/swingx-all-1.6.4.jar;lib/mysql-connector-j-9.1.0.jar" ehealth.PreLoginPage
`

### Option 3: Run Automated Tests
`powershell
java -cp "bin;lib/postgresql-42.7.4.jar;lib/swingx-all-1.6.4.jar;lib/mysql-connector-j-9.1.0.jar" ehealth.AppTest
`

---

## Default Credentials

| Role | Username / Email | Password | Landing Screen |
| :--- | :--- | :--- | :--- |
| **Admin** | dmin | dmin | Doctor CRUD Management Table |
| **Doctor** | med@email.com (or doc) | 123 | Doctor's Assigned Patients Portal |
| **Patient** | lice@email.com (or patient1) | 123 | Health Vitals Entry & Charts |
