package pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class patTable extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private int autoIncrementId = 1; // Start with ID 1
    private int loggedInDoctorId; // Logged-in doctor's ID (idmed)

    public patTable(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;

        // Frame Settings
        setTitle("Patient Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Title Label
        JLabel lblNewLabel = new JLabel("Tabib");
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblNewLabel.setBounds(62, 10, 177, 63);
        contentPane.add(lblNewLabel);

        // Panel to contain the table
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245)); // Light grey background
        panel.setBounds(72, 110, 779, 358);
        contentPane.add(panel);
        panel.setLayout(null);

        // Table columns
        String[] columns = {"ID", "Username", "Full Name", "Gender", "Email", "Phone"};

        // Table model with data and columns
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };

        // Set table appearance
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(220, 220, 220)); // Light grey header background
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(135, 206, 235)); // Light blue for selected rows
        table.setSelectionForeground(Color.BLACK);

        // Adjust column widths
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(50); // Adjust width of ID column
        column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(100); // Adjust width of Username column

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 740, 320);
        panel.add(scrollPane);

        JLabel lblNewLabel_1 = new JLabel("Patient Table");
        lblNewLabel_1.setFont(new Font("Alkatra", Font.PLAIN, 45));
        lblNewLabel_1.setBounds(279, 43, 303, 54);
        contentPane.add(lblNewLabel_1);

        // Retrieve and display data from the database
        loadPatientsFromDatabase();
    }

    // Method to load patients from the database based on the logged-in doctor's ID
    private void loadPatientsFromDatabase() {
        List<Patient> patients = new ArrayList<>();
        
        // Database connection variables
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabib", "Tabib", "abc123");

            // Create a statement object
            stmt = c.createStatement();

            // SQL query to get patients assigned to the logged-in doctor
            String sql = "SELECT * FROM patient WHERE ID_med = " + loggedInDoctorId;

            // Execute the query
            rs = stmt.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                // Get patient information from the database
                int patientId = rs.getInt("ID_patient");
                String username = rs.getString("User_pat");
                String fullName = rs.getString("User_pat");
                String gender = rs.getString("Gender_pat");
                String email = rs.getString("Email_pat");
                String phone = rs.getString("phone_pat");

                // Create a Patient object and add it to the list
                Patient patient = new Patient(patientId, username, fullName, gender, email, phone);
                patients.add(patient);
            }

            // Add patients to the table
            for (Patient patient : patients) {
                Object[] row = {autoIncrementId++, patient.getUsername(), patient.getFullName(), patient.getGender(), patient.getEmail(), patient.getPhone()};
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                // Close resources
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Simulate the doctor logged in with ID 123 (idmed)
        int loggedInDoctorId = 123;

        // Create and display the PatTable form with the doctor's ID
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new patTable(loggedInDoctorId).setVisible(true);
            }
        });
    }
}

// Patient class to store patient details
class Patient {
    private int id;
    private String username;
    private String fullName;
    private String gender;
    private String email;
    private String phone;

    public Patient(int id, String username, String fullName, String gender, String email, String phone) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
