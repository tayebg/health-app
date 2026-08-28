package pages;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class statW extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tabib";
    private static final String USER = "Tabib";
    private static final String PASSWORD = "abc123";

    // Method to fetch the Weight data from the database
    private int[][] getWeightDataFromDatabase() {
        int[][] Data = new int[7][7]; // Assuming 7 weeks and 7 days per week

        // SQL query to fetch the weight data for each week and day
        String query = "SELECT week, day, Weight FROM status WHERE ID_patient = '" + GlobalData.id_pa + "';";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int week = rs.getInt("week");
                int day = rs.getInt("day");
                int Weight = rs.getInt("Weight");  // Fetching the Weight column

                // Store the Weight value in the array (default 0 if not found)
                if (week >= 1 && week <= 7 && day >= 1 && day <= 7) {
                    Data[week - 1][day - 1] = Weight;  // Store the Weight value
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Data;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                statW frame = new statW();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public statW() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 800);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(241, 244, 249));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Label for "Tabib" with image
        JLabel lblNewLabel = new JLabel("ehealth");
        lblNewLabel.setIcon(new ImageIcon("icons/arrow.png"));
        lblNewLabel.setFont(new Font("Montserrat Medium", Font.BOLD, 32));
        lblNewLabel.setBounds(62, 10, 177, 63);
        contentPane.add(lblNewLabel);

        // Main panel to hold the graph
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Make the panel scrollable
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(50, 50, 1100, 600);
        contentPane.add(scrollPane);

        JButton btnNewButton = new JButton("go back to form");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Form form = new Form(1);
                form.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setBounds(198, 10, 130, 30);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("list");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statSelect statSelects = new statSelect();
                statSelects.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setBounds(340, 10, 81, 21);
        contentPane.add(btnNewButton_1);

        // Fetch the data from the database
        int[][] weightData = getWeightDataFromDatabase(); // Fetch the 2D array from the database
        int numWeeks = weightData.length; // Number of weeks
        int barWidth = 15; // Width of each bar
        int spacing = 5; // Space between bars
        int weekSpacing = 20; // Adjusted space between weeks to make the graph fit better
        int startX = 20; // Starting X position for the first week

        // Graph dimensions
        int graphHeight = 400; // Height of the graph area
        int graphBottomY = 500; // Y position for the bottom of the graph

        // Loop through each week and day to plot bars
        for (int week = 0; week < numWeeks; week++) {
            int weekStartX = startX + week * (7 * (barWidth + spacing) + weekSpacing); // X position for each week
            for (int day = 0; day < 7; day++) {
                int barHeight = weightData[week][day]; // Data for each day (Weight value)
                int x = weekStartX + day * (barWidth + spacing); // X position for each dayâ€™s bar
                int y = graphBottomY - barHeight; // Y position for the bar, based on its height

                // Create a panel to represent each bar for the day
                JPanel bar = new JPanel();
                bar.setBackground(new Color(0, 255, 255)); // Bar color
                bar.setBounds(x, y, barWidth, barHeight); // Position and size of the bar
                panel.add(bar);

                // Day label below each bar
                JLabel dayLabel = new JLabel("D" + (day + 1)); // Day labels (D1, D2, ..., D7)
                dayLabel.setFont(new Font("Albert Sans", Font.PLAIN, 10));
                dayLabel.setBounds(x, graphBottomY + 10, barWidth, 15); // Position of the day label
                panel.add(dayLabel);
            }

            // Week label for each week
            JLabel weekLabel = new JLabel("Week " + (week + 1)); // Week labels (Week 1, Week 2, ...)
            weekLabel.setFont(new Font("Albert Sans", Font.BOLD, 14));
            weekLabel.setBounds(weekStartX + 20, graphBottomY + 30, 80, 20); // Position of the week label
            panel.add(weekLabel);
        }
    }
}
