# 🏥 E-Health / Tabib - Medical & Health Management System

A Java Swing desktop application designed for patient health monitoring, doctor consultations, and medical administration. Features database integration with **PostgreSQL** and **MySQL**, role-based authentication, and visual metric graphs.

---

## 🚀 Features

- **🔐 Role-Based Access Control**:
  - **Patients**: Record weekly vitals, view temperature/tension/weight graph trends.
  - **Doctors (Médecins)**: Manage and monitor assigned patients, view patient health histories and charts.
  - **Administrators**: Manage doctor accounts with full CRUD (Create, Read, Update, Delete) operations.
- **📊 Interactive Health Visualizations**: Dynamic bar charts for weekly Temperature (°C), Blood Pressure / Tension (mmHg), and Weight (kg).
- **🗄️ Multi-Database Support**:
  - **PostgreSQL**: Auto-detection, table creation (Auto-DDL), and seed data.
  - **MySQL / MariaDB**: Compatible with XAMPP, WAMP, and phpMyAdmin setups.
  - **Offline Fallback**: In-memory demo mode when database services are offline.
- **🎨 Modern UI**: Gradient-styled Swing components, custom hover buttons, and card layouts.

---

## 🛠️ Tech Stack & Dependencies

- **Language**: Java 21 (Compatible with Java 8+)
- **UI Framework**: Java Swing & SwingX (swingx-all-1.6.4.jar)
- **Databases**:
  - PostgreSQL (via postgresql-42.7.4.jar)
  - MySQL / MariaDB (via mysql-connector-j-9.1.0.jar)
- **Build & Run**: PowerShell script / Batch launcher (un.bat)

---

## ⚡ Quick Start

### 1. Run with Launcher (Windows)
Double-click **un.bat** or run:
`cmd
run.bat
`

### 2. Compile & Run Manually
`powershell
# Compile
javac -d bin -cp "bin;postgresql-42.7.4.jar;swingx-all-1.6.4.jar;mysql-connector-j-9.1.0/mysql-connector-j-9.1.0.jar" src\ehealth\*.java src\pages\*.java

# Launch Application
java -cp "bin;postgresql-42.7.4.jar;swingx-all-1.6.4.jar;mysql-connector-j-9.1.0/mysql-connector-j-9.1.0.jar" ehealth.PreLoginPage
`

---

## ⚙️ Database Configuration

Configuration is managed in **db.properties**:

`properties
# Type: postgresql or mysql
db.type=postgresql
db.host=localhost
db.port=5432
db.name=tabib
db.user=postgres
db.password=postgres
`

### SQL Schemas Included
- **schema_postgres.sql**: PostgreSQL DDL and initial sample data.
- **	abib.sql**: MySQL / MariaDB dump for 	abib database.
- **ehealth.sql**: MySQL dump for unified user schema.

---

## 📁 Project Structure

`	ext
├── src/
│   ├── ehealth/          # Core modern application package
│   │   ├── PreLoginPage.java   # Entry point & Login dialog
│   │   ├── signup.java         # Dynamic patient & doctor registration
│   │   ├── form.java           # Health data entry form
│   │   ├── Select.java         # Metric dashboard & cards
│   │   ├── temperature.java    # Temperature charts
│   │   ├── tension.java        # Blood pressure charts
│   │   ├── weight.java         # Weight charts
│   │   ├── patTable.java       # Doctor patient management portal
│   │   ├── tableuser.java      # Admin doctor management portal
│   │   ├── DBConnection.java   # Auto-detecting multi-database driver
│   │   └── User.java           # OOP User data models
│   └── pages/            # Compatible page layer
├── icons/                # Graphical assets & illustrations
├── schema_postgres.sql   # PostgreSQL database schema
├── tabib.sql             # MySQL database schema
├── db.properties         # Database connection settings
├── run.bat               # One-click compilation and execution script
└── README.md
`

---

## 👥 Default Demo Credentials

| Role | Username / Email | Password | Landing Page |
| :--- | :--- | :--- | :--- |
| **Admin** | dmin | dmin | Doctor Management Table |
| **Doctor** | med@email.com (or doc) | 123 | Doctor's Patients Portal |
| **Patient** | lice@email.com (or patient1) | 123 | Health Entry & Charts |
