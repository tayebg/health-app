package ehealth;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class tableuser extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                tableuser frame = new tableuser();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public tableuser() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 620);
        setTitle("Admin Portal - Doctor & User Management");

        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Admin Dashboard - Doctor Management");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnLogout = createStyledButton("Logout", null);
        btnLogout.setPreferredSize(new Dimension(100, 35));
        btnLogout.addActionListener(e -> {
            dispose();
            new PreLoginPage().setVisible(true);
        });
        headerPanel.add(btnLogout, BorderLayout.EAST);

        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(255, 255, 255, 220));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Username", "Full Name", "Gender", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0xFFA500));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(135, 206, 235));
        table.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(tablePanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setOpaque(false);

        ImageIcon editIcon = resizeIcon("icons/edit.png", 22, 22);
        ImageIcon deleteIcon = resizeIcon("icons/delete.png", 22, 22);

        JButton btnAdd = createStyledButton("Add Doctor", null);
        JButton btnEdit = createStyledButton("Modify", editIcon);
        JButton btnDelete = createStyledButton("Delete", deleteIcon);
        JButton btnRefresh = createStyledButton("Refresh", null);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        btnAdd.addActionListener(e -> addDoctor());
        btnEdit.addActionListener(e -> modifyDoctor());
        btnDelete.addActionListener(e -> deleteDoctor());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        boolean loadedFromDb = false;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM med")) {

            while (rs.next()) {
                int id = rs.getInt("ID_med");
                String username = rs.getString("user_med");
                String fullName = rs.getString("name_med");
                String gender = rs.getString("gender_med");
                String email = rs.getString("email_med");
                String phone = rs.getString("phone_med");

                tableModel.addRow(new Object[]{id, username, fullName, gender, email, phone});
                loadedFromDb = true;
            }
        } catch (Exception ex) {
            System.err.println("Database load error: " + ex.getMessage());
        }

        if (!loadedFromDb) {
            Object[][] mockData = {
                {1, "dr_smith", "Dr. John Smith", "Male", "smith@hospital.com", "0551122334"},
                {2, "dr_jane", "Dr. Jane Doe", "Female", "jane@hospital.com", "0559988776"}
            };
            for (Object[] row : mockData) {
                tableModel.addRow(row);
            }
        }
    }

    private void addDoctor() {
        JTextField txtUser = new JTextField();
        JTextField txtName = new JTextField();
        JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField txtEmail = new JTextField();
        JTextField txtPhone = new JTextField();
        JPasswordField txtPass = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.add(new JLabel("Username:")); panel.add(txtUser);
        panel.add(new JLabel("Full Name:")); panel.add(txtName);
        panel.add(new JLabel("Gender:")); panel.add(cmbGender);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Phone:")); panel.add(txtPhone);
        panel.add(new JLabel("Password:")); panel.add(txtPass);

        int res = JOptionPane.showConfirmDialog(this, panel, "Add New Doctor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String user = txtUser.getText().trim();
            String name = txtName.getText().trim();
            String gender = (String) cmbGender.getSelectedItem();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (user.isEmpty() || name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO med (user_med, name_med, email_med, phone_med, pass_med, gender_med, id_admin) VALUES (?, ?, ?, ?, ?, ?, 1)")) {
                stmt.setString(1, user);
                stmt.setString(2, name);
                stmt.setString(3, email);
                stmt.setString(4, phone);
                stmt.setString(5, pass.isEmpty() ? "123456" : pass);
                stmt.setString(6, gender);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Doctor added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } catch (Exception ex) {
                // Add to table locally if DB offline
                int newId = tableModel.getRowCount() + 1;
                tableModel.addRow(new Object[]{newId, user, name, gender, email, phone});
                JOptionPane.showMessageDialog(this, "Doctor added to list.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void modifyDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to modify.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doctorId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String user = tableModel.getValueAt(selectedRow, 1).toString();
        String name = tableModel.getValueAt(selectedRow, 2).toString();
        String gender = tableModel.getValueAt(selectedRow, 3).toString();
        String email = tableModel.getValueAt(selectedRow, 4).toString();
        String phone = tableModel.getValueAt(selectedRow, 5).toString();

        JTextField txtUser = new JTextField(user);
        JTextField txtName = new JTextField(name);
        JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cmbGender.setSelectedItem(gender);
        JTextField txtEmail = new JTextField(email);
        JTextField txtPhone = new JTextField(phone);

        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.add(new JLabel("Username:")); panel.add(txtUser);
        panel.add(new JLabel("Full Name:")); panel.add(txtName);
        panel.add(new JLabel("Gender:")); panel.add(cmbGender);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Phone:")); panel.add(txtPhone);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modify Doctor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String newUser = txtUser.getText().trim();
            String newName = txtName.getText().trim();
            String newGender = (String) cmbGender.getSelectedItem();
            String newEmail = txtEmail.getText().trim();
            String newPhone = txtPhone.getText().trim();

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE med SET user_med=?, name_med=?, gender_med=?, email_med=?, phone_med=? WHERE ID_med=?")) {
                stmt.setString(1, newUser);
                stmt.setString(2, newName);
                stmt.setString(3, newGender);
                stmt.setString(4, newEmail);
                stmt.setString(5, newPhone);
                stmt.setInt(6, doctorId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Doctor updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } catch (Exception ex) {
                tableModel.setValueAt(newUser, selectedRow, 1);
                tableModel.setValueAt(newName, selectedRow, 2);
                tableModel.setValueAt(newGender, selectedRow, 3);
                tableModel.setValueAt(newEmail, selectedRow, 4);
                tableModel.setValueAt(newPhone, selectedRow, 5);
                JOptionPane.showMessageDialog(this, "Updated in list.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void deleteDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doctorId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM med WHERE ID_med=?")) {
                stmt.setInt(1, doctorId);
                stmt.executeUpdate();
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Doctor removed from list.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private JButton createStyledButton(String text, ImageIcon icon) {
        JButton button = new JButton(text, icon);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0xFFA500), 2));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xFFA500));
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255, 200));
                button.setForeground(Color.BLACK);
            }
        });
        return button;
    }

    private ImageIcon resizeIcon(String imagePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        } catch (Exception e) {
            return null;
        }
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            float[] fractions = {0.0f, 0.3f, 0.6f, 1.0f};
            Color[] colors = {
                new Color(0x013A69),
                new Color(0x015E92),
                new Color(0x0270AC),
                new Color(0x5EC2D1)
            };
            LinearGradientPaint gradientPaint = new LinearGradientPaint(0, 0, getWidth(), getHeight(), fractions, colors);
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
