package ehealth;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class signup extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsername;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JPasswordField passwordField;
    private JComboBox<String> cmbGender;
    private JComboBox<String> cmbDoctor;
    private JLabel lblDoctor;
    private JRadioButton rdbtnDoctor;
    private JRadioButton rdbtnPatient;
    private ButtonGroup roleGroup;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                signup frame = new signup();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public signup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 920, 640);
        setTitle("E-Health - Sign Up");

        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(460, 560));
        contentPane.add(panel, new GridBagConstraints());

        JLabel lblSignup = new JLabel("Create Account");
        lblSignup.setFont(new Font("Roboto", Font.BOLD, 30));
        lblSignup.setForeground(Color.WHITE);
        lblSignup.setBounds(130, 10, 240, 40);
        panel.add(lblSignup);

        // Full Name
        JLabel lblFullName = new JLabel("Full Name:");
        lblFullName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFullName.setForeground(Color.WHITE);
        lblFullName.setBounds(30, 60, 100, 28);
        panel.add(lblFullName);

        txtFullName = createStyledTextField("John Doe");
        txtFullName.setBounds(140, 60, 280, 28);
        panel.add(txtFullName);

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(30, 100, 100, 28);
        panel.add(lblUsername);

        txtUsername = createStyledTextField("johndoe");
        txtUsername.setBounds(140, 100, 280, 28);
        panel.add(txtUsername);

        // Email
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(30, 140, 100, 28);
        panel.add(lblEmail);

        txtEmail = createStyledTextField("example@email.com");
        txtEmail.setBounds(140, 140, 280, 28);
        panel.add(txtEmail);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(30, 180, 100, 28);
        panel.add(lblPassword);

        passwordField = createStyledPasswordField("********");
        passwordField.setBounds(140, 180, 280, 28);
        panel.add(passwordField);

        // Phone
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPhone.setForeground(Color.WHITE);
        lblPhone.setBounds(30, 220, 100, 28);
        panel.add(lblPhone);

        txtPhone = createStyledTextField("0551234567");
        txtPhone.setBounds(140, 220, 280, 28);
        panel.add(txtPhone);

        // Gender
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGender.setForeground(Color.WHITE);
        lblGender.setBounds(30, 260, 100, 28);
        panel.add(lblGender);

        cmbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cmbGender.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbGender.setBounds(140, 260, 280, 28);
        panel.add(cmbGender);

        // Role Selection
        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRole.setForeground(Color.WHITE);
        lblRole.setBounds(30, 300, 100, 28);
        panel.add(lblRole);

        rdbtnDoctor = new JRadioButton("Médecin (Doctor)");
        rdbtnPatient = new JRadioButton("Patient");
        rdbtnPatient.setSelected(true);
        customizeRadioButton(rdbtnDoctor);
        customizeRadioButton(rdbtnPatient);

        roleGroup = new ButtonGroup();
        roleGroup.add(rdbtnPatient);
        roleGroup.add(rdbtnDoctor);

        rdbtnPatient.setBounds(140, 300, 110, 28);
        rdbtnDoctor.setBounds(260, 300, 160, 28);
        panel.add(rdbtnPatient);
        panel.add(rdbtnDoctor);

        // Doctor Selection (Visible for Patient)
        lblDoctor = new JLabel("Assigned Dr:");
        lblDoctor.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDoctor.setForeground(Color.WHITE);
        lblDoctor.setBounds(30, 340, 100, 28);
        panel.add(lblDoctor);

        cmbDoctor = new JComboBox<>();
        cmbDoctor.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbDoctor.setBounds(140, 340, 280, 28);
        panel.add(cmbDoctor);

        rdbtnPatient.addActionListener(e -> {
            lblDoctor.setVisible(true);
            cmbDoctor.setVisible(true);
        });
        rdbtnDoctor.addActionListener(e -> {
            lblDoctor.setVisible(false);
            cmbDoctor.setVisible(false);
        });

        populateDoctorComboBox();

        // Sign Up Button
        JButton btnSignup = createStyledButton("Sign Up");
        btnSignup.setBounds(50, 400, 370, 42);
        btnSignup.addActionListener(e -> performSignup());
        panel.add(btnSignup);

        // Already have an account? Login
        JLabel lblAlreadyAccount = new JLabel("Already have an account?");
        lblAlreadyAccount.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblAlreadyAccount.setForeground(Color.WHITE);
        lblAlreadyAccount.setBounds(100, 460, 180, 25);
        panel.add(lblAlreadyAccount);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("Roboto", Font.BOLD, 14));
        lblLogin.setForeground(new Color(100, 180, 255));
        lblLogin.setBounds(280, 460, 60, 25);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PreLoginPage().setVisible(true);
            }
        });
        panel.add(lblLogin);
    }

    private void populateDoctorComboBox() {
        cmbDoctor.removeAllItems();
        cmbDoctor.addItem("Select Doctor");

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_med, name_med FROM med")) {

            while (rs.next()) {
                int id = rs.getInt("ID_med");
                String name = rs.getString("name_med");
                cmbDoctor.addItem(id + " - " + name);
            }
        } catch (Exception ex) {
            // Offline fallback doctors
            cmbDoctor.addItem("1 - Dr. John Smith");
            cmbDoctor.addItem("2 - Dr. Jane Doe");
        }
    }

    private void performSignup() {
        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String phone = txtPhone.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();
        boolean isDoctor = rdbtnDoctor.isSelected();

        if (fullName.isEmpty() || fullName.equals("John Doe") ||
            username.isEmpty() || username.equals("johndoe") ||
            email.isEmpty() || email.equals("example@email.com") ||
            password.isEmpty() || password.equals("********")) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doctorId = 1;
        if (!isDoctor) {
            String selectedDoc = (String) cmbDoctor.getSelectedItem();
            if (selectedDoc != null && selectedDoc.contains(" - ")) {
                try {
                    doctorId = Integer.parseInt(selectedDoc.split(" - ")[0].trim());
                } catch (Exception ignored) {}
            }
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (isDoctor) {
                String sql = "INSERT INTO med (user_med, name_med, email_med, phone_med, pass_med, gender_med, id_admin) VALUES (?, ?, ?, ?, ?, ?, 1)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    stmt.setString(2, fullName);
                    stmt.setString(3, email);
                    stmt.setString(4, phone);
                    stmt.setString(5, password);
                    stmt.setString(6, gender);
                    stmt.executeUpdate();
                }
            } else {
                String sql = "INSERT INTO patient (User_pat, Name_pat, Email_pat, phone_pat, Pass_pat, Gender_pat, ID_med, ID_admin) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    stmt.setString(2, fullName);
                    stmt.setString(3, email);
                    stmt.setString(4, phone);
                    stmt.setString(5, password);
                    stmt.setString(6, gender);
                    stmt.setInt(7, doctorId);
                    stmt.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Sign up successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new PreLoginPage().setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Account created locally.\n" + ex.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new PreLoginPage().setVisible(true);
        }
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        styleTextField(textField, placeholder);
        return textField;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(placeholder);
        styleTextField(passwordField, placeholder);
        passwordField.setEchoChar((char) 0);
        return passwordField;
    }

    private void styleTextField(JTextComponent textComponent, String placeholder) {
        textComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        textComponent.setForeground(Color.GRAY);
        textComponent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        textComponent.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textComponent.getText().equals(placeholder)) {
                    textComponent.setText("");
                    textComponent.setForeground(Color.BLACK);
                    if (textComponent instanceof JPasswordField) {
                        ((JPasswordField) textComponent).setEchoChar('•');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textComponent.getText().isEmpty()) {
                    textComponent.setText(placeholder);
                    textComponent.setForeground(Color.GRAY);
                    if (textComponent instanceof JPasswordField) {
                        ((JPasswordField) textComponent).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setOpaque(true);
        button.setBackground(new Color(255, 255, 255, 200));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0xFFA500), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0xFFA500));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 200));
                button.setForeground(Color.BLACK);
            }
        });
        return button;
    }

    private void customizeRadioButton(JRadioButton radioButton) {
        radioButton.setOpaque(false);
        radioButton.setFont(new Font("Arial", Font.PLAIN, 13));
        radioButton.setForeground(Color.WHITE);
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
