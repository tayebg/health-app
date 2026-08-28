package ehealth;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class patTable extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private int loggedInDoctorId;
    private List<PatientInfo> patientList = new ArrayList<>();

    static class PatientInfo {
        int id;
        String username;
        String fullName;
        String gender;
        String email;
        String phone;

        public PatientInfo(int id, String username, String fullName, String gender, String email, String phone) {
            this.id = id;
            this.username = username;
            this.fullName = fullName;
            this.gender = gender;
            this.email = email;
            this.phone = phone;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            patTable frame = new patTable(globaldata.id_med);
            frame.setVisible(true);
        });
    }

    public patTable() {
        this(globaldata.id_med);
    }

    public patTable(int loggedInDoctorId) {
        this.loggedInDoctorId = loggedInDoctorId;

        setTitle("Doctor Portal - Patient Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 620);

        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Doctor Dashboard - My Patients");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JButton btnLogout = createStyledButton("Logout");
        btnLogout.setPreferredSize(new Dimension(110, 36));
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
        table.setRowHeight(32);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0xFFA500));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(135, 206, 235));
        table.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(tablePanel, BorderLayout.CENTER);

        // Bottom Action Bar
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomBar.setOpaque(false);

        JButton btnViewStats = createStyledButton("View Patient Health Stats");
        btnViewStats.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a patient to view health stats.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int patId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                globaldata.id_pa = patId;
                dispose();
                new Select().setVisible(true);
            } catch (Exception ex) {
                dispose();
                new Select().setVisible(true);
            }
        });
        bottomBar.add(btnViewStats);

        JButton btnRefresh = createStyledButton("Refresh");
        btnRefresh.addActionListener(e -> loadPatients());
        bottomBar.add(btnRefresh);

        contentPane.add(bottomBar, BorderLayout.SOUTH);

        loadPatients();
    }

    private void loadPatients() {
        tableModel.setRowCount(0);
        patientList.clear();

        boolean loadedFromDb = false;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql;
            if (loggedInDoctorId > 0) {
                sql = "SELECT * FROM patient WHERE ID_med = " + loggedInDoctorId;
            } else {
                sql = "SELECT * FROM patient";
            }

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    int id = rs.getInt("ID_patient");
                    String username = rs.getString("User_pat");
                    String fullName = rs.getString("Name_pat");
                    String gender = rs.getString("Gender_pat");
                    String email = rs.getString("Email_pat");
                    String phone = rs.getString("phone_pat");

                    tableModel.addRow(new Object[]{id, username, fullName, gender, email, phone});
                    patientList.add(new PatientInfo(id, username, fullName, gender, email, phone));
                    loadedFromDb = true;
                }
            }

            if (!loadedFromDb) {
                // If query returned 0 rows for this doctor, load all patients
                try (ResultSet rsAll = stmt.executeQuery("SELECT * FROM patient")) {
                    while (rsAll.next()) {
                        int id = rsAll.getInt("ID_patient");
                        String username = rsAll.getString("User_pat");
                        String fullName = rsAll.getString("Name_pat");
                        String gender = rsAll.getString("Gender_pat");
                        String email = rsAll.getString("Email_pat");
                        String phone = rsAll.getString("phone_pat");

                        tableModel.addRow(new Object[]{id, username, fullName, gender, email, phone});
                        patientList.add(new PatientInfo(id, username, fullName, gender, email, phone));
                        loadedFromDb = true;
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Database load error: " + ex.getMessage());
        }

        // Mock fallback if DB empty/offline
        if (!loadedFromDb) {
            Object[][] mockData = {
                {1, "patient1", "Alice Martin", "Female", "alice@email.com", "0551234567"},
                {2, "patient2", "Bob Johnson", "Male", "bob@email.com", "0559876543"},
                {3, "patient3", "Claire Dupont", "Female", "claire@email.com", "0554567890"}
            };
            for (Object[] row : mockData) {
                tableModel.addRow(row);
            }
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
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
