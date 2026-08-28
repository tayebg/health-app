package pages;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class signup extends JFrame {
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailFeild;
    private JTextField phoneField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> medicComboBox;
    

    
    // Radio buttons for selecting role (Medic or Patient)
    private JRadioButton medicRadioButton;
    private JRadioButton patientRadioButton;
    private ButtonGroup roleGroup;
    
    // This will hold the list of users
    private List<User> users;
    private JTextField passFie;

    public signup() {
    	
    	GradientPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane); 
    	
        // Initialize the user list
        users = new ArrayList<>();
        
        contentPane.setLayout(null);
        setContentPane(contentPane); 
        getContentPane().setBackground(new Color(241, 244, 249));
        setTitle("Sign Up");
        setSize(495, 786);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create form components
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblUsername.setBounds(8, 85, 205, 48);
        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblFullName.setBounds(226, 85, 178, 48);
        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblGender.setBounds(8, 191, 150, 48);
        JLabel lblEmail = new JLabel("Password");
        lblEmail.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblEmail.setBounds(8, 381, 227, 48);
        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblPhone.setBounds(8, 485, 150, 48);

        usernameField = new JTextField(20);
        usernameField.setBackground(new Color(200, 201, 213));
        usernameField.setFont(new Font("Albert Sans", Font.PLAIN, 24));
        usernameField.setBounds(33, 129, 184, 48);
        fullNameField = new JTextField(20);
        fullNameField.setBackground(new Color(200, 201, 213));
        fullNameField.setFont(new Font("Albert Sans", Font.PLAIN, 24));
        fullNameField.setBounds(245, 129, 178, 48);
        emailFeild = new JTextField(20);
        emailFeild.setBackground(new Color(200, 201, 213));
        emailFeild.setFont(new Font("Albert Sans", Font.PLAIN, 24));
        emailFeild.setBounds(40, 339, 383, 48);
        phoneField = new JTextField(20);
        phoneField.setBackground(new Color(200, 201, 213));
        phoneField.setFont(new Font("Albert Sans", Font.PLAIN, 24));
        phoneField.setBounds(40, 543, 362, 48);
        
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setBackground(new Color(200, 201, 213));
        genderComboBox.setForeground(new Color(0, 128, 255));
        genderComboBox.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        genderComboBox.setBounds(33, 234, 183, 48);
        
     // Create and configure radio buttons for Medic and Patient roles
        medicRadioButton = new JRadioButton("Medic");
        medicRadioButton.setBackground(new Color(200, 201, 213));
        medicRadioButton.setFont(new Font("Albert Sans", Font.PLAIN, 18));
        medicRadioButton.setBounds(114, 602, 99, 31);

        patientRadioButton = new JRadioButton("Patient");
        patientRadioButton.setBackground(new Color(200, 201, 213));
        patientRadioButton.setFont(new Font("Albert Sans", Font.PLAIN, 18));
        patientRadioButton.setBounds(263, 602, 141, 31);

        // Group the radio buttons so only one can be selected at a time
        roleGroup = new ButtonGroup();
        roleGroup.add(medicRadioButton);
        roleGroup.add(patientRadioButton);

        // Add action listeners to toggle the visibility of the medicComboBox
        medicRadioButton.addActionListener(e -> medicComboBox.setVisible(false)); // Hide combobox for Medic
        patientRadioButton.addActionListener(e -> medicComboBox.setVisible(true)); // Show combobox for Patient

        // Add radio buttons to the frame
        getContentPane().add(medicRadioButton);
        getContentPane().add(patientRadioButton);

        // Create and configure the Medic ComboBox (Doctor selection, visible only for Patient)
        medicComboBox = new JComboBox<>();
        medicComboBox.setBackground(new Color(200, 201, 213));
        medicComboBox.setForeground(new Color(0, 128, 255));
        medicComboBox.setFont(new Font("Albert Sans", Font.PLAIN, 18));
        medicComboBox.setBounds(245, 234, 178, 48);
        medicComboBox.setVisible(false); // Initially hidden
        getContentPane().add(medicComboBox);

        // Populate the medicComboBox with doctors when "Patient" is selected
        populateMedicComboBox(); // Ensure this method populates the ComboBox with available doctors

        // Optional: Label for the "Specialist" (Doctor) field
        JLabel lblEmail_1_1 = new JLabel("Doctor");
        lblEmail_1_1.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblEmail_1_1.setBounds(226, 187, 150, 48);
        getContentPane().add(lblEmail_1_1);

        // Fix the password field (use JPasswordField for password input)
        passFie = new JPasswordField(20); // Use JPasswordField for security
        passFie.setBackground(new Color(200, 201, 213));
        passFie.setFont(new Font("Albert Sans", Font.PLAIN, 24));
        passFie.setBounds(40, 427, 388, 48);
        getContentPane().add(passFie);


    
        

        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setForeground(new Color(255, 255, 255));
        btnSignUp.setFont(new Font("Anonymous Pro", Font.PLAIN, 24));
        btnSignUp.setBackground(new Color(33, 99, 235));
        btnSignUp.setBounds(91, 681, 285, 48);
        getContentPane().setLayout(null);

        getContentPane().add(lblUsername);
        getContentPane().add(usernameField);
        getContentPane().add(lblFullName);
        getContentPane().add(fullNameField);
        getContentPane().add(lblGender);
        getContentPane().add(genderComboBox);
        getContentPane().add(lblEmail);
        getContentPane().add(emailFeild);
        getContentPane().add(lblPhone);
        getContentPane().add(phoneField);
        getContentPane().add(medicRadioButton);
        getContentPane().add(patientRadioButton);
        getContentPane().add(btnSignUp);
        
        JLabel lblNewLabel_2 = new JLabel("Sign Up");
        lblNewLabel_2.setFont(new Font("Eras Demi ITC", Font.PLAIN, 37));
        lblNewLabel_2.setBounds(144, 10, 135, 97);
        getContentPane().add(lblNewLabel_2);
        
        JLabel lblEmail_1 = new JLabel("Email");
        lblEmail_1.setFont(new Font("Albert Sans", Font.PLAIN, 20));
        lblEmail_1.setBounds(8, 292, 150, 48);
        getContentPane().add(lblEmail_1);

       





        // Sign Up button logic
        btnSignUp.addActionListener(new ActionListener() {
        	  @Override
        	    public void actionPerformed(ActionEvent e) {
        	        String username = usernameField.getText().trim();
        	        String fullName = fullNameField.getText().trim();
        	        String gender = (String) genderComboBox.getSelectedItem();
        	        String email = emailFeild.getText().trim();
        	        String pass = new String(passFie.getText()).trim();
        	        if (pass.isEmpty()) {
        	        	System.out.println("Password (raw): " + passFie.getText());  // Print the char[] array

        	            JOptionPane.showMessageDialog(signup.this, "Password is required.");
        	            return;
        	        }// Get password correctly
        	        String phone = phoneField.getText().trim();
        	        System.out.println("Username: " + username);
                    System.out.println("Full Name: " + fullName);
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + pass);
        	        
        	        // Validate fields
        	        if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty()) {
        	            JOptionPane.showMessageDialog(signup.this, "All fields are required.");
        	            return; // Prevent further execution
        	        }

        	        // Determine the selected role
        	        String role = null;
        	        if (medicRadioButton.isSelected()) {
        	            role = "Medic";
        	        } else if (patientRadioButton.isSelected()) {
        	            role = "Patient";
        	        }

        	        if (role == null) {
        	            JOptionPane.showMessageDialog(signup.this, "Please select a role.");
        	            return; // Exit if no role is selected
        	        }

        	        // For patients, validate medic selection
        	        int medicId = -1; // Default invalid ID
        	        if ("Patient".equals(role)) {
        	            String selectedMedic = (String) medicComboBox.getSelectedItem();
        	            if ("Select Medic".equals(selectedMedic)) {
        	                JOptionPane.showMessageDialog(signup.this, "Please assign a medic.");
        	                return; // Exit if no medic is selected
        	            }
        	            medicId = Integer.parseInt(selectedMedic.split(" - ")[0]); // Extract medic ID
        	        }
        	         

                    
                    System.out.println("Role: " + role);
                    System.out.println("Gender: " + gender);
                // Database connection and insertion
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabib", "Tabib", "abc123")) {

                    String sql = "";
                    if ("Medic".equals(role)) {
                        sql = "INSERT INTO med (user_med, name_med, email_med, phone_med, pass_med, gender_med, id_admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    } else if ("Patient".equals(role)) {
                        sql = "INSERT INTO patient (User_pat, Name_pat, Email_pat, phone_pat, Pass_pat, Gender_pat, ID_med, ID_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    }
                    


                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.setString(2, fullName);
                        stmt.setString(3, email);
                        stmt.setString(4, phone);
                        stmt.setString(5, pass);
                        stmt.setString(6, gender);

                        if ("Patient".equals(role)) {
                            stmt.setInt(7, medicId); // Assigned medic ID
                            stmt.setInt(8, 1); // Example: Admin ID set to 1
                        } else if ("Medic".equals(role)) {
                            stmt.setInt(7, 1); // Example: Admin ID set to 1
                        }

                        int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(signup.this, "Sign up successful!");
                        } else {
                            JOptionPane.showMessageDialog(signup.this, "Error signing up, please try again.");
                        }

                        // Proceed to login
                        login logins = new login(users); // Ensure login constructor accepts users list
                        logins.setVisible(true);
                        dispose();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(signup.this, "Database error: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the fields
                usernameField.setText("");
                fullNameField.setText("");
                emailFeild.setText("");
                phoneField.setText("");
                passFie.setText("");
                roleGroup.clearSelection();
                medicComboBox.setSelectedIndex(0); // Reset combo box to default
            }
        });






    }

    // User class to store user information
    public class User {
        private String username;
        private String fullName;
        private String gender;
        private String email;
        private String pass;
        private String phone;

        public User(String username,String pass, String fullName, String gender, String email, String phone) {
            this.username = username;
            this.fullName = fullName;
            this.gender = gender;
            this.email = email;
            this.phone = phone;
            this.pass = pass;
        }

        public User(int id, String name, String email2) {
			// TODO Auto-generated constructor stub
		}

		// Getters for user properties
        public String getUsername() { return username; }
        public String getPassword() { return pass; }
        public String getFullName() { return fullName; }
        public String getGender() { return gender; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
    }

    // Medic class that extends User
    public class Medic extends User {
        public Medic(String username,String pass, String fullName, String gender, String email, String phone) {
            super(username, pass,fullName, gender, email, phone);
        }
    }

    // Patient class that extends User
    public class Patient extends User {
        public Patient(String username,String pass, String fullName, String gender, String email, String phone) {
            super(username,pass, fullName, gender, email, phone);
        }
    }

    public static void main(String[] args) {
        // Create and display the sign-up form
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new signup().setVisible(true);
            }
        });
    }
 // Method to populate the medic combo box
    private void populateMedicComboBox() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabib", "Tabib", "abc123");
             PreparedStatement stmt = conn.prepareStatement("SELECT id_med, name_med FROM med");
             ResultSet rs = stmt.executeQuery()) {

            medicComboBox.addItem("Select Medic"); // Default option
            while (rs.next()) {
                int medicId = rs.getInt("id_med");
                String medicName = rs.getString("name_med");
                medicComboBox.addItem(medicId + " - " + medicName); // Display ID and Name
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching medics: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // Enable high-quality rendering
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // Define a smooth gradient with harmonious colors
            float[] fractions = {0.0f, 0.3f, 0.6f, 1.0f}; // Positions of color stops
            Color[] colors = {
                new Color(0x013A69),  // Start color (deep blue, darkest point)
                new Color(0x015E92),  // Slightly less dark blue
                new Color(0x0270AC),  // Mid-light blue
                new Color(0x5EC2D1)   // Soft teal (lightest and smooth fit for the gradient)
            };

            // Use LinearGradientPaint for smooth transitions
            LinearGradientPaint gradientPaint = new LinearGradientPaint(
                0, 0, getWidth(), getHeight(), // Gradient start and end points
                fractions,                     // Positions for each color stop
                colors                         // Colors at each stop
            );

            // Apply the gradient paint
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
