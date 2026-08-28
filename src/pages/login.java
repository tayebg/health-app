package pages;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pages.signup.Medic;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtEmail;
    private JPasswordField passwordField;
    private List<signup.User> users;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Create a list of sample users here (or get it from somewhere else)
                    List<signup.User> users = new ArrayList<>();
                    // Add sample users, e.g., new signup.User("email", "password", ...)
                    login frame = new login(users); // Pass the users list to the constructor
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructor now accepts the users list
    public login(List<signup.User> users) {
        this.users = users; // Initialize users list

        // Set up the frame and content pane
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 423, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(241, 244, 249));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Panel for login form
        JPanel panel = new JPanel();
        panel.setBackground(new Color(241, 244, 249));
        panel.setBounds(0, 0, 418, 537);
        contentPane.add(panel);
        panel.setLayout(null);

        // Login Label
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("Cooper Black", Font.PLAIN, 37));
        lblLogin.setBounds(164, 43, 159, 62);
        panel.add(lblLogin);

        // Email Label
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(new Font("Alegreya Sans", Font.PLAIN, 23));
        lblEmail.setBounds(51, 164, 75, 22);
        panel.add(lblEmail);

        // Password Label
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Alegreya Sans", Font.PLAIN, 23));
        lblPassword.setBounds(53, 245, 148, 39);
        panel.add(lblPassword);

        // Email TextField
        txtEmail = new JTextField(" example@email.com");
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtEmail.setForeground(Color.GRAY);
        txtEmail.setBounds(61, 196, 250, 39);
        txtEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals(" example@email.com")) {
                    txtEmail.setText("");
                    txtEmail.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().isEmpty()) {
                    txtEmail.setText(" example@email.com");
                    txtEmail.setForeground(Color.GRAY);
                }
            }
        });
        panel.add(txtEmail);

        // Password Field
        passwordField = new JPasswordField("********");
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setBounds(63, 294, 250, 39);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("********")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022');
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
        panel.add(passwordField);

        // Login Button
        JButton btnLogin = new JButton("Log-in");
        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setForeground(new Color(241, 244, 249));
        btnLogin.setFont(new Font("Anonymous Pro", Font.PLAIN, 24));
        btnLogin.setBounds(51, 421, 291, 51);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String password = new String(passwordField.getPassword());

                try (Connection c = DBConnection.getConnection()) {
                    // Check patient table
                    String sqlPatient = "SELECT * FROM patient WHERE (Email_pat = ? OR User_pat = ?) AND Pass_pat = ?";
                    try (PreparedStatement pstmt = c.prepareStatement(sqlPatient)) {
                        pstmt.setString(1, email);
                        pstmt.setString(2, email);
                        pstmt.setString(3, password);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                GlobalData.id_pa = rs.getInt("ID_patient");
                                Form form = new Form(GlobalData.id_pa);
                                form.setVisible(true);
                                dispose();
                                return;
                            }
                        }
                    }

                    // Check medic table
                    String sqlMedic = "SELECT * FROM med WHERE (email_med = ? OR user_med = ?) AND pass_med = ?";
                    try (PreparedStatement pstmt = c.prepareStatement(sqlMedic)) {
                        pstmt.setString(1, email);
                        pstmt.setString(2, email);
                        pstmt.setString(3, password);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                GlobalData.id_med = rs.getInt("ID_med");
                                patTable patientTable = new patTable(GlobalData.id_med);
                                patientTable.setVisible(true);
                                dispose();
                                return;
                            }
                        }
                    }

                    // Check admin table
                    String sqlAdmin = "SELECT * FROM admin WHERE user_admin = ? AND pass_admin = ?";
                    try (PreparedStatement pstmt = c.prepareStatement(sqlAdmin)) {
                        pstmt.setString(1, email);
                        pstmt.setString(2, password);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                tabibTabel doctorTable = new tabibTabel();
                                doctorTable.setVisible(true);
                                dispose();
                                return;
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(btnLogin);
    }
}
