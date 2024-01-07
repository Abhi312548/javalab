package seventh_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginApp extends JFrame {
    private JTextField loginIdField;
    private JTextField nameField;
    private JPasswordField passwordField;

    public LoginApp() {
        setTitle("Login Application");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel loginIdLabel = new JLabel("Login ID:");
        loginIdField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitLoginDetails();
            }
        });

        panel.add(loginIdLabel);
        panel.add(loginIdField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(submitButton);

        add(panel);
    }

    private void submitLoginDetails() {
        String loginId = loginIdField.getText();
        String name = nameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/logindet";
        String user = "root";
        String passwordDb = "Prgj@771";

        try (Connection connection = DriverManager.getConnection(url, user, passwordDb)) {
            // Insert user details
            String insertSQL = "INSERT INTO users (login_id, name, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, loginId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, password);
                preparedStatement.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Login details added to the database.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            // Clear sensitive data from password field
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginApp().setVisible(true);
            }
        });
    }
}
