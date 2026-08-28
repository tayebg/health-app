package pages;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class statSelect extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Assuming userId is obtained from login

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    statSelect frame = new statSelect();  // Pass userId here
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructor that accepts userId
    public statSelect() {
         // Store userId
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

        // Panel 2 (Temperature)
        JPanel panelT = new JPanel();
        panelT.setLayout(null);
        panelT.setBackground(Color.WHITE);
        panelT.setBounds(268, 221, 364, 70);
        contentPane.add(panelT);

        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBackground(new Color(255, 0, 128));
        panel_1_1.setBounds(8, 10, 50, 50);
        panelT.add(panel_1_1);

        // Add MouseListener to panel_1_1
        panel_1_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the stat window for temperature and pass the userId
                statT statTs = new statT();  // Pass userId to statT constructor
                statTs.setVisible(true);
                dispose();
            }
        });

        JLabel lblNewLabel_3_1 = new JLabel("Temperature");
        lblNewLabel_3_1.setFont(new Font("Albert Sans", Font.BOLD, 29));
        lblNewLabel_3_1.setBounds(66, 10, 290, 50);
        panelT.add(lblNewLabel_3_1);

        // Panel 3 (Weight)
        JPanel panelW = new JPanel();
        panelW.setLayout(null);
        panelW.setBackground(Color.WHITE);
        panelW.setBounds(268, 312, 364, 70);
        contentPane.add(panelW);

        JPanel panel_1_2 = new JPanel();
        panel_1_2.setBackground(new Color(0, 255, 255));
        panel_1_2.setBounds(8, 10, 50, 50);
        panelW.add(panel_1_2);

        JLabel lblNewLabel_3_2 = new JLabel("Weight");
        lblNewLabel_3_2.setFont(new Font("Albert Sans", Font.BOLD, 29));
        lblNewLabel_3_2.setBounds(66, 10, 290, 50);
        panelW.add(lblNewLabel_3_2);

        // Add MouseListener to panel_1_2
        panel_1_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the stat window for weight and pass the userId
                statW statWs = new statW();  // Pass userId to statW constructor
                statWs.setVisible(true);
                dispose();
            }
        });

        // Panel 4 (Tension)
        JPanel panelTe = new JPanel();
        panelTe.setLayout(null);
        panelTe.setBackground(Color.WHITE);
        panelTe.setBounds(268, 413, 364, 70);
        contentPane.add(panelTe);

        JPanel panel_1_3 = new JPanel();
        panel_1_3.setBackground(new Color(183, 53, 253));
        panel_1_3.setBounds(8, 10, 50, 50);
        panelTe.add(panel_1_3);

        JLabel lblNewLabel_3_3 = new JLabel("Tension");
        lblNewLabel_3_3.setFont(new Font("Albert Sans", Font.BOLD, 29));
        lblNewLabel_3_3.setBounds(66, 10, 290, 50);
        panelTe.add(lblNewLabel_3_3);

        // Add MouseListener to panel_1_3
        panel_1_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the stat window for tension and pass the userId
                StatTe statTEs = new StatTe();  // Pass userId to StatTe constructor
                statTEs.setVisible(true);
                dispose();
            }
        });

        // Back to form button
        JButton btnNewButton = new JButton("Back to form");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Form form = new Form(1);
                form.setVisible(true);
                dispose();
        	}
        });
        btnNewButton.setBounds(229, 39, 169, 21);
        contentPane.add(btnNewButton);
    }
}
