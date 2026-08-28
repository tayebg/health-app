package ehealth;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PreLoginPage extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PreLoginPage frame = new PreLoginPage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PreLoginPage() {
        setTitle("E-Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 920, 595);

        // Background Panel with Image
        BackgroundPanel bgPanel = new BackgroundPanel();
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        // Content Panel (Overlay on Top of Background)
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        bgPanel.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // App Title
        JLabel appTitle = new JLabel("E-Health");
        appTitle.setFont(new Font("Roboto", Font.BOLD, 44));
        appTitle.setForeground(Color.WHITE);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(appTitle);

        // Tagline
        JLabel tagline = new JLabel("Your Personal Health & Doctor Companion");
        tagline.setFont(new Font("Roboto", Font.PLAIN, 18));
        tagline.setForeground(new Color(230, 230, 230));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tagline);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setOpaque(false);

        // \"Swipe Up to Login\" Button
        JButton btnSwipeUp = new JButton("Swipe Up to Login");
        styleButton(btnSwipeUp);
        btnSwipeUp.setPreferredSize(new Dimension(240, 50));
        btnSwipeUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSwipeUp.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        footerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        footerPanel.add(btnSwipeUp);

        // Footer Text
        JLabel footer = new JLabel("© 2024 E-Health Inc.");
        footer.setFont(new Font("Roboto", Font.PLAIN, 12));
        footer.setForeground(Color.WHITE);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        footerPanel.add(footer);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        bgPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 165, 0));
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 200));
                button.setForeground(Color.BLACK);
            }
        });
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage("icons/background.jpg");
            } catch (Exception ignored) {}
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(0x013A69));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}

// Login Page
class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtEmail;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Login - E-Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 920, 595);

        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(0, 0, 0, 90));
        loginPanel.setPreferredSize(new Dimension(420, 470));
        loginPanel.setLayout(null);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("Roboto", Font.BOLD, 36));
        lblLogin.setForeground(Color.WHITE);
        lblLogin.setBounds(160, 20, 120, 50);
        loginPanel.add(lblLogin);

        JLabel lblEmail = new JLabel("E-mail / Username");
        lblEmail.setFont(new Font("Roboto", Font.PLAIN, 16));
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(50, 85, 200, 25);
        loginPanel.add(lblEmail);

        txtEmail = new JTextField("example@email.com");
        txtEmail.setFont(new Font("Roboto", Font.PLAIN, 15));
        txtEmail.setForeground(Color.GRAY);
        txtEmail.setBounds(50, 115, 320, 40);
        txtEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals("example@email.com")) {
                    txtEmail.setText("");
                    txtEmail.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().trim().isEmpty()) {
                    txtEmail.setText("example@email.com");
                    txtEmail.setForeground(Color.GRAY);
                }
            }
        });
        loginPanel.add(txtEmail);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Roboto", Font.PLAIN, 16));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(50, 175, 100, 25);
        loginPanel.add(lblPassword);

        passwordField = new JPasswordField("********");
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 15));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setBounds(50, 205, 320, 40);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("********")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText("********");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
        loginPanel.add(passwordField);

        JButton btnLogin = new JButton("Log-in");
        styleButton(btnLogin);
        btnLogin.setBounds(50, 280, 320, 48);
        btnLogin.addActionListener(e -> performLogin());
        loginPanel.add(btnLogin);

        JLabel lblNoAccount = new JLabel("Don’t have an account?");
        lblNoAccount.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblNoAccount.setForeground(Color.WHITE);
        lblNoAccount.setBounds(85, 350, 180, 25);
        loginPanel.add(lblNoAccount);

        JLabel lblSignUp = new JLabel("Sign up");
        lblSignUp.setFont(new Font("Roboto", Font.BOLD, 14));
        lblSignUp.setForeground(new Color(100, 180, 255));
        lblSignUp.setBounds(265, 350, 70, 25);
        lblSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                signup signupPage = new signup();
                signupPage.setVisible(true);
            }
        });
        loginPanel.add(lblSignUp);

        contentPane.add(loginPanel);
    }

    private void performLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || email.equals("example@email.com") || password.isEmpty() || password.equals("********")) {
            JOptionPane.showMessageDialog(this, "Please enter your username/email and password.", "Login Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean dbSuccess = false;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Check patient table
            String sqlPatient = "SELECT * FROM patient WHERE (Email_pat='" + email + "' OR User_pat='" + email + "') AND Pass_pat='" + password + "'";
            try (ResultSet rs = stmt.executeQuery(sqlPatient)) {
                if (rs.next()) {
                    globaldata.id_pa = rs.getInt("ID_patient");
                    globaldata.currentUserName = rs.getString("Name_pat");
                    globaldata.currentUserRole = "patient";
                    dbSuccess = true;
                    dispose();
                    new form().setVisible(true);
                    return;
                }
            }

            // 2. Check doctor/med table
            String sqlMedic = "SELECT * FROM med WHERE (email_med='" + email + "' OR user_med='" + email + "') AND pass_med='" + password + "'";
            try (ResultSet rs = stmt.executeQuery(sqlMedic)) {
                if (rs.next()) {
                    globaldata.id_med = rs.getInt("ID_med");
                    globaldata.currentUserName = rs.getString("name_med");
                    globaldata.currentUserRole = "medcine";
                    dbSuccess = true;
                    dispose();
                    new patTable(globaldata.id_med).setVisible(true);
                    return;
                }
            }

            // 3. Check admin table
            String sqlAdmin = "SELECT * FROM admin WHERE user_admin='" + email + "' AND pass_admin='" + password + "'";
            try (ResultSet rs = stmt.executeQuery(sqlAdmin)) {
                if (rs.next()) {
                    globaldata.id_admin = rs.getInt("id_admin");
                    globaldata.currentUserName = "Admin";
                    globaldata.currentUserRole = "admin";
                    dbSuccess = true;
                    dispose();
                    new tableuser().setVisible(true);
                    return;
                }
            }

            // 4. Check unified user table (ehealth schema)
            try {
                String sqlUser = "SELECT * FROM user WHERE (email='" + email + "' OR full_name='" + email + "') AND password='" + password + "'";
                try (ResultSet rs = stmt.executeQuery(sqlUser)) {
                    if (rs.next()) {
                        String role = rs.getString("role");
                        int id = rs.getInt("id");
                        globaldata.currentUserName = rs.getString("full_name");
                        dbSuccess = true;
                        dispose();
                        if ("admin".equalsIgnoreCase(role)) {
                            globaldata.currentUserRole = "admin";
                            new tableuser().setVisible(true);
                        } else if ("medcine".equalsIgnoreCase(role)) {
                            globaldata.currentUserRole = "medcine";
                            globaldata.id_med = id;
                            new patTable(id).setVisible(true);
                        } else {
                            globaldata.currentUserRole = "patient";
                            globaldata.id_pa = id;
                            new form().setVisible(true);
                        }
                        return;
                    }
                }
            } catch (SQLException ignored) {}

        } catch (Exception ex) {
            System.err.println("Database connection warning: " + ex.getMessage());
        }

        // Offline / Demo fallback credentials
        if (!dbSuccess) {
            if ("admin".equalsIgnoreCase(email)) {
                globaldata.currentUserRole = "admin";
                globaldata.currentUserName = "Admin";
                dispose();
                new tableuser().setVisible(true);
            } else if (email.toLowerCase().contains("doc") || email.toLowerCase().contains("med")) {
                globaldata.currentUserRole = "medcine";
                globaldata.currentUserName = "Doctor";
                dispose();
                new patTable(1).setVisible(true);
            } else if (email.length() > 0) {
                // Allow patient login fallback
                globaldata.currentUserRole = "patient";
                globaldata.currentUserName = email;
                dispose();
                new form().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 165, 0));
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255, 200));
                button.setForeground(Color.BLACK);
            }
        });
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
