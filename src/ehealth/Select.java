package ehealth;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Select extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Select frame = new Select();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Select() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 620);
        setTitle("E-Health - Health Metrics Overview");

        JPanel rootPane = new GradientPanel();
        rootPane.setLayout(new BorderLayout(15, 15));
        rootPane.setBorder(new EmptyBorder(15, 20, 15, 20));
        setContentPane(rootPane);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Health Metrics Dashboard");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerBtns.setOpaque(false);

        if ("medcine".equalsIgnoreCase(globaldata.currentUserRole)) {
            JButton btnBackToPatients = createHeaderButton("Doctor Portal");
            btnBackToPatients.addActionListener(e -> {
                dispose();
                new patTable(globaldata.id_med).setVisible(true);
            });
            headerBtns.add(btnBackToPatients);
        }

        JButton btnLogout = createHeaderButton("Logout");
        btnLogout.addActionListener(e -> {
            dispose();
            new PreLoginPage().setVisible(true);
        });
        headerBtns.add(btnLogout);

        headerPanel.add(headerBtns, BorderLayout.EAST);
        rootPane.add(headerPanel, BorderLayout.NORTH);

        // Cards Container
        JPanel gridCards = new JPanel(new GridLayout(2, 2, 20, 20));
        gridCards.setOpaque(false);

        addCreativeSection(gridCards, "Temperature", "icons/temperature.png", e -> openStat(new temperature()));
        addCreativeSection(gridCards, "Tension", "icons/tension.png", e -> openStat(new tension()));
        addCreativeSection(gridCards, "Weight", "icons/weight.png", e -> openStat(new weight()));
        addCreativeSection(gridCards, "Back to Form", "icons/back.png", e -> {
            form form = new form();
            form.setVisible(true);
            dispose();
        });

        rootPane.add(gridCards, BorderLayout.CENTER);
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0xFFA500), 2));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 32));

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

    private void addCreativeSection(JPanel container, String title, String iconPath, ActionListener action) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(new Color(255, 255, 255, 220));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 100, 0), 2, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        cardPanel.setOpaque(true);
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(140, 110, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception ignored) {}
        cardPanel.add(iconLabel, BorderLayout.CENTER);

        // Title
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        lblTitle.setForeground(new Color(1, 58, 105));
        cardPanel.add(lblTitle, BorderLayout.SOUTH);

        cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.actionPerformed(null);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cardPanel.setBorder(BorderFactory.createLineBorder(new Color(0x009933), 3, true));
                cardPanel.setBackground(new Color(245, 255, 245, 240));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cardPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 100, 0), 2, true));
                cardPanel.setBackground(new Color(255, 255, 255, 220));
            }
        });

        container.add(cardPanel);
    }

    private void openStat(JFrame statFrame) {
        statFrame.setVisible(true);
        dispose();
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
