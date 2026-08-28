package pages;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Form extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField weightT;
    private JTextField TempT;
    private JTextField tenT;
    private JTextField WeekT;
    private JTextField DayT;

    private int patientId; // Store the patient ID

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Form frame = new Form(1); // Replace 1 with a test patient ID
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructor that accepts the patient ID.
     */
    public Form(int id_pa) {
        this.patientId = id_pa; // Save the patient ID
        initialize();
    }

    /**
     * Initialize the frame components.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(241, 244, 249));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ehealth");
        lblNewLabel.setIcon(new ImageIcon("icons/arrow.png"));
        lblNewLabel.setFont(new Font("Montserrat Medium", Font.BOLD, 32));
        lblNewLabel.setBounds(62, 10, 177, 63);
        contentPane.add(lblNewLabel);

        JLabel lblEmail_1_1 = new JLabel("Weight");
        lblEmail_1_1.setFont(new Font("Alegreya Sans", Font.PLAIN, 23));
        lblEmail_1_1.setBounds(286, 213, 75, 22);
        contentPane.add(lblEmail_1_1);

        JLabel lblEmail_1_2 = new JLabel("Temperature");
        lblEmail_1_2.setFont(new Font("Alegreya Sans", Font.PLAIN, 23));
        lblEmail_1_2.setBounds(286, 302, 200, 22);
        contentPane.add(lblEmail_1_2);

        JLabel lblEmail_1_3 = new JLabel("Tension");
        lblEmail_1_3.setFont(new Font("Alegreya Sans", Font.PLAIN, 23));
        lblEmail_1_3.setBounds(286, 388, 200, 22);
        contentPane.add(lblEmail_1_3);

        JButton btnConfirmed = new JButton("Confirmed");
        btnConfirmed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDataToDatabase();
            }
        });
        btnConfirmed.setForeground(new Color(241, 244, 249));
        btnConfirmed.setFont(new Font("Anonymous Pro", Font.PLAIN, 24));
        btnConfirmed.setBackground(new Color(37, 99, 235));
        btnConfirmed.setBounds(737, 474, 177, 51);
        contentPane.add(btnConfirmed);

        JLabel lblKg = new JLabel("Kg");
        lblKg.setForeground(new Color(128, 128, 128));
        lblKg.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblKg.setBounds(526, 247, 20, 34);
        contentPane.add(lblKg);

        weightT = new JTextField("");
        weightT.setHorizontalAlignment(SwingConstants.LEFT);
        weightT.setForeground(Color.GRAY);
        weightT.setFont(new Font("Tahoma", Font.PLAIN, 15));
        weightT.setBounds(296, 245, 250, 39);
        contentPane.add(weightT);

        JLabel lblC = new JLabel("CÂ°");
        lblC.setForeground(Color.GRAY);
        lblC.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblC.setBounds(526, 351, 20, 15);
        contentPane.add(lblC);

        TempT = new JTextField("");
        TempT.setHorizontalAlignment(SwingConstants.LEFT);
        TempT.setForeground(Color.GRAY);
        TempT.setFont(new Font("Tahoma", Font.PLAIN, 15));
        TempT.setBounds(296, 339, 250, 39);
        contentPane.add(TempT);

        JLabel lblMmhg = new JLabel("mmHg");
        lblMmhg.setForeground(Color.GRAY);
        lblMmhg.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblMmhg.setBounds(493, 426, 52, 27);
        contentPane.add(lblMmhg);

        tenT = new JTextField("");
        tenT.setHorizontalAlignment(SwingConstants.LEFT);
        tenT.setForeground(Color.GRAY);
        tenT.setFont(new Font("Tahoma", Font.PLAIN, 15));
        tenT.setBounds(296, 420, 250, 39);
        contentPane.add(tenT);

        WeekT = new JTextField("");
        WeekT.setHorizontalAlignment(SwingConstants.LEFT);
        WeekT.setForeground(Color.GRAY);
        WeekT.setFont(new Font("Tahoma", Font.PLAIN, 15));
        WeekT.setBounds(270, 494, 85, 39);
        contentPane.add(WeekT);

        DayT = new JTextField("");
        DayT.setHorizontalAlignment(SwingConstants.LEFT);
        DayT.setForeground(Color.GRAY);
        DayT.setFont(new Font("Tahoma", Font.PLAIN, 15));
        DayT.setBounds(363, 494, 250, 39);
        contentPane.add(DayT);
        
        JButton btnNewButton = new JButton("status");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 statSelect statSelects = new statSelect(); // Pass the ID to the Form
                 statSelects.setVisible(true);
                 dispose(); 
        		
        	}
        });
        btnNewButton.setFont(new Font("Alegreya Sans Black", Font.PLAIN, 24));
        btnNewButton.setBounds(450, 26, 137, 34);
        contentPane.add(btnNewButton);
        
        JLabel lblEmail_1_1_1 = new JLabel("Week");
        lblEmail_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 23));
        lblEmail_1_1_1.setBounds(249, 462, 75, 22);
        contentPane.add(lblEmail_1_1_1);
        
        JLabel lblEmail_1_1_1_1 = new JLabel("Day");
        lblEmail_1_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 23));
        lblEmail_1_1_1_1.setBounds(363, 462, 75, 22);
        contentPane.add(lblEmail_1_1_1_1);
    }

    /**
     * Saves the data from the form to the database.
     */
    private void saveDataToDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO status (Temp, Weight, Tension, day, week, ID_patient) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(TempT.getText().trim()));
                stmt.setInt(2, Integer.parseInt(weightT.getText().trim()));
                stmt.setInt(3, Integer.parseInt(tenT.getText().trim()));
                stmt.setInt(4, Integer.parseInt(DayT.getText().trim()));
                stmt.setInt(5, Integer.parseInt(WeekT.getText().trim()));
                stmt.setInt(6, GlobalData.id_pa);

                stmt.executeUpdate();
            }
            statSelect statSelects = new statSelect();
            statSelects.setVisible(true);
            dispose();
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
