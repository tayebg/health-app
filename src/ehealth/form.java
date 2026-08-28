package ehealth;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class form extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField weightT;
    private JTextField tempT;
    private JTextField tenT;
    private JTextField weekT;
    private JTextField dayT;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                form frame = new form();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public form() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 620);
        setTitle("E-Health - Health Data Entry");

        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel lblTitle = new JLabel("Health Data Entry");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 32));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(lblTitle, gbc);

        // Subtitle / User Welcome
        JLabel lblUser = new JLabel("Patient: " + globaldata.currentUserName);
        lblUser.setFont(new Font("Roboto", Font.PLAIN, 16));
        lblUser.setForeground(new Color(220, 240, 255));
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        contentPane.add(lblUser, gbc);

        gbc.gridwidth = 1;

        // Weight
        addLabelAndField("Weight (kg):", weightT = new JTextField("70"), 2, contentPane, gbc);

        // Temperature
        addLabelAndField("Temperature (°C):", tempT = new JTextField("37"), 3, contentPane, gbc);

        // Tension
        addLabelAndField("Tension (mmHg):", tenT = new JTextField("120"), 4, contentPane, gbc);

        // Week
        addLabelAndField("Week (1-7):", weekT = new JTextField("1"), 5, contentPane, gbc);

        // Day
        addLabelAndField("Day (1-7):", dayT = new JTextField("1"), 6, contentPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton btnConfirm = createStyledButton("Save Data");
        JButton btnStats = createStyledButton("View Stats");
        JButton btnLogout = createStyledButton("Logout");

        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnStats);
        buttonPanel.add(btnLogout);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(buttonPanel, gbc);

        // Save Data Action
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int weight = Integer.parseInt(weightT.getText().trim());
                    int temp = Integer.parseInt(tempT.getText().trim());
                    int tension = Integer.parseInt(tenT.getText().trim());
                    int week = Integer.parseInt(weekT.getText().trim());
                    int day = Integer.parseInt(dayT.getText().trim());

                    if (week < 1 || week > 52 || day < 1 || day > 7) {
                        JOptionPane.showMessageDialog(form.this, "Week must be 1-52 and Day must be 1-7.", "Range Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Save to in-memory matrices
                    if (week <= globaldata.WEEKS && day <= globaldata.DAYS) {
                        globaldata.wei[week - 1][day - 1] = weight;
                        globaldata.Temp[week - 1][day - 1] = temp;
                        globaldata.ten[week - 1][day - 1] = tension;
                    }

                    // Save to Database
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                             "INSERT INTO status (Temp, Weight, Tension, day, week, ID_patient) VALUES (?, ?, ?, ?, ?, ?)")) {
                        stmt.setInt(1, temp);
                        stmt.setInt(2, weight);
                        stmt.setInt(3, tension);
                        stmt.setInt(4, day);
                        stmt.setInt(5, week);
                        stmt.setInt(6, globaldata.id_pa);
                        stmt.executeUpdate();
                    } catch (Exception dbEx) {
                        System.err.println("Database insert warning: " + dbEx.getMessage());
                    }

                    JOptionPane.showMessageDialog(form.this, "Health data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    Select selectFrame = new Select();
                    selectFrame.setVisible(true);
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(form.this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // View Stats Action
        btnStats.addActionListener(e -> {
            Select selectFrame = new Select();
            selectFrame.setVisible(true);
            dispose();
        });

        // Logout Action
        btnLogout.addActionListener(e -> {
            dispose();
            new PreLoginPage().setVisible(true);
        });
    }

    private void addLabelAndField(String labelText, JTextField textField, int row, JPanel panel, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(new LineBorder(Color.BLACK, 1));
        textField.setPreferredSize(new Dimension(220, 32));
        textField.setOpaque(true);
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 38));

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
