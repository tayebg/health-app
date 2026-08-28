package ehealth;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class weight extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                weight frame = new weight();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public weight() {
        setTitle("Weight - E-Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 620);

        contentPane = new GradientPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Back Button
        JButton btnBackToSelect = createImageButton("icons/arrow.png", 36, 36);
        btnBackToSelect.setBounds(20, 20, 45, 45);
        btnBackToSelect.addActionListener(e -> {
            Select selectPage = new Select();
            selectPage.setVisible(true);
            dispose();
        });
        contentPane.add(btnBackToSelect);

        // Title Label
        JLabel lblTitle = new JLabel("Weekly Weight Overview (kg)");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(80, 25, 600, 35);
        contentPane.add(lblTitle);

        JButton btnForm = createStyledButton("Data Entry Form");
        btnForm.setBounds(760, 25, 150, 35);
        btnForm.addActionListener(e -> {
            new form().setVisible(true);
            dispose();
        });
        contentPane.add(btnForm);

        // Graph Panel inside ScrollPane
        JPanel graphPanel = createGraphPanel();
        graphPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(graphPanel);
        scrollPane.setBounds(20, 80, 895, 480);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2, true));
        contentPane.add(scrollPane);
    }

    private int[][] loadWeightData() {
        int[][] data = new int[4][7];
        for (int w = 0; w < 4; w++) {
            for (int d = 0; d < 7; d++) {
                data[w][d] = (globaldata.wei != null && w < globaldata.wei.length && d < globaldata.wei[w].length)
                    ? globaldata.wei[w][d] : 70;
            }
        }

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT week, day, Weight FROM status WHERE ID_patient=" + globaldata.id_pa)) {
            while (rs.next()) {
                int w = rs.getInt("week");
                int d = rs.getInt("day");
                int val = rs.getInt("Weight");
                if (w >= 1 && w <= 4 && d >= 1 && d <= 7) {
                    data[w - 1][d - 1] = val;
                }
            }
        } catch (Exception ignored) {}

        return data;
    }

    private JButton createImageButton(String imagePath, int width, int height) {
        JButton button = new JButton();
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0xFFA500), 2));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createGraphPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(860, 440));

        int[][] data = loadWeightData();

        int barWidth = 20;
        int spacing = 6;
        int weekSpacing = 35;
        int graphBottom = 340;
        int startX = 30;

        for (int week = 0; week < 4; week++) {
            int xOffset = startX + week * (7 * (barWidth + spacing) + weekSpacing);

            for (int day = 0; day < 7; day++) {
                int weiVal = data[week][day];
                int barHeight = Math.max(10, Math.min(260, (int)(weiVal * 2.2)));

                JPanel bar = new JPanel();
                bar.setBackground(new Color(0, 190, 210));
                bar.setBounds(xOffset + day * (barWidth + spacing), graphBottom - barHeight, barWidth, barHeight);
                bar.setToolTipText("Week " + (week + 1) + " Day " + (day + 1) + ": " + weiVal + " kg");
                panel.add(bar);

                JLabel valLabel = new JLabel(String.valueOf(weiVal), SwingConstants.CENTER);
                valLabel.setFont(new Font("Roboto", Font.BOLD, 9));
                valLabel.setBounds(xOffset + day * (barWidth + spacing) - 2, graphBottom - barHeight - 16, barWidth + 4, 14);
                panel.add(valLabel);

                JLabel dayLabel = new JLabel("D" + (day + 1), SwingConstants.CENTER);
                dayLabel.setFont(new Font("Roboto", Font.PLAIN, 11));
                dayLabel.setBounds(xOffset + day * (barWidth + spacing), graphBottom + 6, barWidth, 15);
                panel.add(dayLabel);
            }

            JLabel weekLabel = new JLabel("Week " + (week + 1), SwingConstants.CENTER);
            weekLabel.setFont(new Font("Roboto", Font.BOLD, 13));
            weekLabel.setForeground(new Color(1, 58, 105));
            weekLabel.setBounds(xOffset + 20, graphBottom + 30, 120, 20);
            panel.add(weekLabel);
        }

        return panel;
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
