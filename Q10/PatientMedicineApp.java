package tenth_test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientMedicineApp {
    private Connection connection;
    private JTextField patientNameField, patientTypeField, medicineTypeField, treatmentTypeField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PatientMedicineApp().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        establishConnection();

        JFrame frame = new JFrame("Patient Medicine App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        createInputPanel(frame);

        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }

    private void establishConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/med";
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
            String createPatientTable = "CREATE TABLE IF NOT EXISTS Patient (" +
                    "PatientID INT PRIMARY KEY AUTO_INCREMENT," +
                    "PatientName VARCHAR(255)," +
                    "PatientType VARCHAR(255))";
            statement.executeUpdate(createPatientTable);

            String createMedicineTable = "CREATE TABLE IF NOT EXISTS Medicine (" +
                    "MedicineID INT PRIMARY KEY AUTO_INCREMENT," +
                    "MedicineType VARCHAR(255)," +
                    "TreatmentType VARCHAR(255))";
            statement.executeUpdate(createMedicineTable);

            createPatientMedicineTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPatientMedicineTable() {
        try (Statement statement = connection.createStatement()) {
            String createPatientMedicineTable = "CREATE TABLE IF NOT EXISTS PatientMedicine (" +
                    "PatientID INT," +
                    "MedicineID INT," +
                    "FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)," +
                    "FOREIGN KEY (MedicineID) REFERENCES Medicine(MedicineID)," +
                    "PRIMARY KEY (PatientID, MedicineID))";
            statement.executeUpdate(createPatientMedicineTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        JLabel patientNameLabel = new JLabel("Patient Name:");
        patientNameField = new JTextField();

        JLabel patientTypeLabel = new JLabel("Patient Type:");
        patientTypeField = new JTextField();

        JLabel medicineTypeLabel = new JLabel("Medicine Type:");
        medicineTypeField = new JTextField();

        JLabel treatmentTypeLabel = new JLabel("Treatment Type:");
        treatmentTypeField = new JTextField();

        JButton insertButton = new JButton("Insert Data");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        JButton listPatientsButton = new JButton("List Patients");
        listPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPatients();
            }
        });

        inputPanel.add(patientNameLabel);
        inputPanel.add(patientNameField);
        inputPanel.add(patientTypeLabel);
        inputPanel.add(patientTypeField);
        inputPanel.add(medicineTypeLabel);
        inputPanel.add(medicineTypeField);
        inputPanel.add(treatmentTypeLabel);
        inputPanel.add(treatmentTypeField);
        inputPanel.add(insertButton);
        inputPanel.add(listPatientsButton);

        frame.add(inputPanel);
    }

    private void insertData() {
        try {
            String patientName = patientNameField.getText();
            String patientType = patientTypeField.getText();
            String medicineType = medicineTypeField.getText();
            String treatmentType = treatmentTypeField.getText();

            if (patientName.isEmpty() || patientType.isEmpty() || medicineType.isEmpty() || treatmentType.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            insertPatient(patientName, patientType);
            int patientID = getPatientId(patientName, patientType);

            if (patientID == -1) {
                JOptionPane.showMessageDialog(null, "Failed to retrieve Patient ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            insertMedicine(medicineType, treatmentType);
            int medicineID = getMedicineId(medicineType, treatmentType);

            if (medicineID == -1) {
                JOptionPane.showMessageDialog(null, "Failed to retrieve Medicine ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            assignTreatment(patientID, medicineID);

            JOptionPane.showMessageDialog(null, "Data inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check the values.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void insertPatient(String patientName, String patientType) throws SQLException {
        String insertQuery = "INSERT INTO Patient (PatientName, PatientType) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, patientName);
            preparedStatement.setString(2, patientType);

            preparedStatement.executeUpdate();
        }
    }

    private void insertMedicine(String medicineType, String treatmentType) throws SQLException {
        String insertQuery = "INSERT INTO Medicine (MedicineType, TreatmentType) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, medicineType);
            preparedStatement.setString(2, treatmentType);

            preparedStatement.executeUpdate();
        }
    }

    private void assignTreatment(int patientID, int medicineID) throws SQLException {
        String insertQuery = "INSERT INTO PatientMedicine (PatientID, MedicineID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, patientID);
            preparedStatement.setInt(2, medicineID);

            preparedStatement.executeUpdate();
        }
    }

    private int getPatientId(String patientName, String patientType) throws SQLException {
        String selectQuery = "SELECT PatientID FROM Patient WHERE PatientName = ? AND PatientType = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, patientName);
            preparedStatement.setString(2, patientType);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("PatientID");
            }
        }
        return -1;
    }

    private int getMedicineId(String medicineType, String treatmentType) throws SQLException {
        String selectQuery = "SELECT MedicineID FROM Medicine WHERE MedicineType = ? AND TreatmentType = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, medicineType);
            preparedStatement.setString(2, treatmentType);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("MedicineID");
            }
        }
        return -1;
    }

    private void listPatients() {
        String medicineType = JOptionPane.showInputDialog(null, "Enter medicine type:");

        if (medicineType != null) {
            StringBuilder displayMessage = new StringBuilder("Patients taking " + medicineType + ":\n");

            displayMessage.append(getPatientsByMedicineType(medicineType));

            JOptionPane.showMessageDialog(null, displayMessage.toString(), "Patients by Medicine Type", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getPatientsByMedicineType(String medicineType) {
        StringBuilder result = new StringBuilder();

        try {
            String selectQuery = "SELECT Patient.PatientName, Patient.PatientType FROM Patient " +
                    "INNER JOIN PatientMedicine ON Patient.PatientID = PatientMedicine.PatientID " +
                    "INNER JOIN Medicine ON PatientMedicine.MedicineID = Medicine.MedicineID " +
                    "WHERE Medicine.MedicineType = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, medicineType);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.append("PatientName: ").append(resultSet.getString("PatientName"))
                            .append(", PatientType: ").append(resultSet.getString("PatientType"))
                            .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
