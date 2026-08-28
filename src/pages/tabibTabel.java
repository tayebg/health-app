package pages;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class tabibTabel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;

    // Customizable settings for table appearance
    private int rowHeight = 40;
    private int[] columnWidths = {50, 100, 150, 100, 200, 100}; // Default widths for columns

    public tabibTabel() {
        // Frame Settings
        setTitle("Doctor Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Title Label
        JLabel lblNewLabel = new JLabel("ehealth");
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
        table.setRowHeight(rowHeight);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(220, 220, 220)); // Light grey header background
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(135, 206, 235)); // Light blue for selected rows
        table.setSelectionForeground(Color.BLACK);

        // Adjust column widths dynamically
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 740, 320);
        panel.add(scrollPane);

        JLabel lblNewLabel_1 = new JLabel("Doctor Table");
        lblNewLabel_1.setFont(new Font("Alkatra", Font.PLAIN, 45));
        lblNewLabel_1.setBounds(279, 43, 303, 54);
        contentPane.add(lblNewLabel_1);

        // Add data to the table from the database
        loadDoctorData();

        // Buttons for Delete and Modify
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDelete.setBounds(780, 20, 120, 30);
        btnDelete.addActionListener(e -> deleteDoctor());
        contentPane.add(btnDelete);

        JButton btnModify = new JButton("Modify");
        btnModify.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnModify.setBounds(600, 20, 120, 30);
        btnModify.addActionListener(e -> modifyDoctor());
        contentPane.add(btnModify);
    }

    // Method to load doctor data from the database and add it to the table
    private void loadDoctorData() {
        List<String[]> doctorList = new ArrayList<>();

        // Database connection
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

            // SQL query to fetch doctor data
            String sql = "SELECT * FROM med";
            rs = stmt.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("ID_med");
                String username = rs.getString("user_med");
                String fullName = rs.getString("name_med");
                String gender = rs.getString("gender_med");
                String email = rs.getString("email_med");
                String phone = rs.getString("phone_med");

                // Add each row of doctor data to the list
                doctorList.add(new String[]{String.valueOf(id), username, fullName, gender, email, phone});
            }

            // Add each doctor from the list to the table model
            for (String[] doctor : doctorList) {
                tableModel.addRow(doctor);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                // Close resources in finally block to ensure they are always closed
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Method to delete a selected doctor from the table and database
    private void deleteDoctor() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get doctor ID from the selected row
        int doctorId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

        // Database connection
        Connection c = null;
        PreparedStatement stmt = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabib", "Tabib", "abc123");

            // SQL query to delete the doctor by ID
            String sql = "DELETE FROM med WHERE ID_med = ?";
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, doctorId);

            // Execute the delete query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Remove the row from the table if deletion is successful
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting doctor.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Method to modify a selected doctor's information
    private void modifyDoctor() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get current data of the selected doctor
        int doctorId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        String username = table.getValueAt(selectedRow, 1).toString();
        String fullName = table.getValueAt(selectedRow, 2).toString();
        String gender = table.getValueAt(selectedRow, 3).toString();
        String email = table.getValueAt(selectedRow, 4).toString();
        String phone = table.getValueAt(selectedRow, 5).toString();

        // Show a dialog to modify doctor details
        
        JTextField txtUsername = new JTextField(username);
        JTextField txtFullName = new JTextField(fullName);
        JTextField txtGender = new JTextField(gender);
        JTextField txtEmail = new JTextField(email);
        JTextField txtPhone = new JTextField(phone);

        JPanel modifyPanel = new JPanel();
        modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));
        modifyPanel.add(new JLabel("Username:"));
        modifyPanel.add(txtUsername);
        modifyPanel.add(new JLabel("Full Name:"));
        modifyPanel.add(txtFullName);
        modifyPanel.add(new JLabel("Gender:"));
        modifyPanel.add(txtGender);
        modifyPanel.add(new JLabel("Email:"));
        modifyPanel.add(txtEmail);
        modifyPanel.add(new JLabel("Phone:"));
        modifyPanel.add(txtPhone);

        int option = JOptionPane.showConfirmDialog(this, modifyPanel, "Modify Doctor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // Get updated information from text fields
            String newUsername = txtUsername.getText();
            String newFullName = txtFullName.getText();
            String newGender = txtGender.getText();
            String newEmail = txtEmail.getText();
            String newPhone = txtPhone.getText();

            // Database connection
            Connection c = null;
            PreparedStatement stmt = null;

            try {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection to the database
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabib", "Tabib", "abc123");

                // SQL query to update the doctor information
                String sql = "UPDATE med SET user_med = ?, name_med = ?, gender_med = ?, email_med = ?, phone_med = ? WHERE ID_med = ?";
                stmt = c.prepareStatement(sql);
                stmt.setString(1, newUsername);
                stmt.setString(2, newFullName);
                stmt.setString(3, newGender);
                stmt.setString(4, newEmail);
                stmt.setString(5, newPhone);
                stmt.setInt(6, doctorId);

                // Execute the update query
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Update the table with the new data
                    tableModel.setValueAt(newUsername, selectedRow, 1);
                    tableModel.setValueAt(newFullName, selectedRow, 2);
                    tableModel.setValueAt(newGender, selectedRow, 3);
                    tableModel.setValueAt(newEmail, selectedRow, 4);
                    tableModel.setValueAt(newPhone, selectedRow, 5);

                    JOptionPane.showMessageDialog(this, "Doctor information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating doctor.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (c != null) {
                        c.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            tabibTabel frame = new tabibTabel();
            frame.setVisible(true);
        });
    }
}

