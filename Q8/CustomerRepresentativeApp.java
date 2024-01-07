package eighth_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerRepresentativeApp {
    private Connection connection;
    private JTextField custNameField, custStateField, repNameField, repStateField, repComissionField, repRateField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CustomerRepresentativeApp().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        establishConnection();

        JFrame frame = new JFrame("Customer Representative App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        createInputPanel(frame);

        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }

    private void establishConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/cust";
            String user = "root";
            String password = "Prgj@771";

            connection = DriverManager.getConnection(url, user, password);

            createTablesIfNotExist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTablesIfNotExist() {
        try (Statement statement = connection.createStatement()) {
            String createRepresentativeTable = "CREATE TABLE IF NOT EXISTS Representative (" +
                    "RepNo INT PRIMARY KEY AUTO_INCREMENT," +
                    "RepName VARCHAR(255)," +
                    "State VARCHAR(255)," +
                    "Comission DOUBLE," +
                    "Rate DOUBLE)";
            statement.executeUpdate(createRepresentativeTable);

            String createCustomerTable = "CREATE TABLE IF NOT EXISTS Customer (" +
                    "CustNo INT PRIMARY KEY AUTO_INCREMENT," +
                    "CustName VARCHAR(255)," +
                    "State VARCHAR(255)," +
                    "Credit_Limit DOUBLE," +
                    "RepNo INT)";
            statement.executeUpdate(createCustomerTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        JLabel custNameLabel = new JLabel("Customer Name:");
        custNameField = new JTextField();

        JLabel custStateLabel = new JLabel("Customer State:");
        custStateField = new JTextField();

        JLabel repNameLabel = new JLabel("Representative Name:");
        repNameField = new JTextField();

        JLabel repStateLabel = new JLabel("Representative State:");
        repStateField = new JTextField();

        JLabel repComissionLabel = new JLabel("Representative Comission:");
        repComissionField = new JTextField();

        JLabel repRateLabel = new JLabel("Representative Rate:");
        repRateField = new JTextField();

        JButton insertButton = new JButton("Insert Data");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        JButton displayButton = new JButton("Display by State");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDataByState();
            }
        });

        inputPanel.add(custNameLabel);
        inputPanel.add(custNameField);
        inputPanel.add(custStateLabel);
        inputPanel.add(custStateField);
        inputPanel.add(repNameLabel);
        inputPanel.add(repNameField);
        inputPanel.add(repStateLabel);
        inputPanel.add(repStateField);
        inputPanel.add(repComissionLabel);
        inputPanel.add(repComissionField);
        inputPanel.add(repRateLabel);
        inputPanel.add(repRateField);
        inputPanel.add(insertButton);
        inputPanel.add(displayButton);

        frame.add(inputPanel);
    }

    private void insertData() {
        try {
            String custName = custNameField.getText();
            String custState = custStateField.getText();
            String repName = repNameField.getText();
            String repState = repStateField.getText();
            double repComission = Double.parseDouble(repComissionField.getText());
            double repRate = Double.parseDouble(repRateField.getText());

            insertRepresentative(repName, repState, repComission, repRate);
            int repNo = getRepresentativeId(repName);

            insertCustomer(custName, custState, repNo);

            JOptionPane.showMessageDialog(null, "Data inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check the values.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void insertRepresentative(String repName, String repState, double repComission, double repRate) throws SQLException {
        String insertQuery = "INSERT INTO Representative (RepName, State, Comission, Rate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, repName);
            preparedStatement.setString(2, repState);
            preparedStatement.setDouble(3, repComission);
            preparedStatement.setDouble(4, repRate);

            preparedStatement.executeUpdate();
        }
    }

    private void insertCustomer(String custName, String custState, int repNo) throws SQLException {
        String insertQuery = "INSERT INTO Customer (CustName, State, RepNo) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custState);
            preparedStatement.setInt(3, repNo);

            preparedStatement.executeUpdate();
        }
    }

    private int getRepresentativeId(String repName) throws SQLException {
        String selectQuery = "SELECT RepNo FROM Representative WHERE RepName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, repName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("RepNo");
            }
        }
        return -1;
    }

    private void displayDataByState() {
        String state = JOptionPane.showInputDialog(null, "Enter state to display data:");

        if (state != null) {
            StringBuilder displayMessage = new StringBuilder("Data for state '" + state + "':\n");

            displayMessage.append("Representatives:\n");
            displayMessage.append(getRepresentativesByState(state));

            displayMessage.append("\nCustomers:\n");
            displayMessage.append(getCustomersByState(state));

            JOptionPane.showMessageDialog(null, displayMessage.toString(), "Data by State", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getRepresentativesByState(String state) {
        StringBuilder result = new StringBuilder();

        try {
            String selectQuery = "SELECT * FROM Representative WHERE State = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, state);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.append("RepNo: ").append(resultSet.getInt("RepNo"))
                            .append(", RepName: ").append(resultSet.getString("RepName"))
                            .append(", Comission: ").append(resultSet.getDouble("Comission"))
                            .append(", Rate: ").append(resultSet.getDouble("Rate"))
                            .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private String getCustomersByState(String state) {
        StringBuilder result = new StringBuilder();

        try {
            String selectQuery = "SELECT * FROM Customer WHERE State = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, state);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.append("CustNo: ").append(resultSet.getInt("CustNo"))
                            .append(", CustName: ").append(resultSet.getString("CustName"))
                            .append(", RepNo: ").append(resultSet.getInt("RepNo"))
                            .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}

