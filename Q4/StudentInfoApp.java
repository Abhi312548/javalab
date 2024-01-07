package fourth_test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StudentInfoApp {
    private JFrame frame;
    private JTextField nameField, usnField, ageField, addressField, sgpa1Field, sgpa2Field, sgpa3Field, sgpa4Field, categoryField;
    private JTextArea displayArea;
    private Map<Integer, Student> studentMap = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new StudentInfoApp().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Student Information System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        createInputPanel();
        createDisplayPanel();

        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(11, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel usnLabel = new JLabel("USN:");
        usnField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();

        JLabel sgpa1Label = new JLabel("SGPA 1:");
        sgpa1Field = new JTextField();

        JLabel sgpa2Label = new JLabel("SGPA 2:");
        sgpa2Field = new JTextField();

        JLabel sgpa3Label = new JLabel("SGPA 3:");
        sgpa3Field = new JTextField();

        JLabel sgpa4Label = new JLabel("SGPA 4:");
        sgpa4Field = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField();

        JButton computeButton = new JButton("Compute");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeButtonClicked();
            }
        });

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doneButtonClicked();
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(usnLabel);
        inputPanel.add(usnField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(sgpa1Label);
        inputPanel.add(sgpa1Field);
        inputPanel.add(sgpa2Label);
        inputPanel.add(sgpa2Field);
        inputPanel.add(sgpa3Label);
        inputPanel.add(sgpa3Field);
        inputPanel.add(sgpa4Label);
        inputPanel.add(sgpa4Field);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(computeButton);
        inputPanel.add(doneButton);

        frame.add(inputPanel);
    }

    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel();
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayPanel.add(scrollPane);
        frame.add(displayPanel);
    }

    private void computeButtonClicked() {
        try {
            String name = nameField.getText();
            String usn = usnField.getText();
            int age = Integer.parseInt(ageField.getText());
            String address = addressField.getText();
            double sgpa1 = Double.parseDouble(sgpa1Field.getText());
            double sgpa2 = Double.parseDouble(sgpa2Field.getText());
            double sgpa3 = Double.parseDouble(sgpa3Field.getText());
            double sgpa4 = Double.parseDouble(sgpa4Field.getText());
            String category = categoryField.getText();

            if (age <= 18 || age > 30 || sgpa1 < 0 || sgpa1 > 10 || sgpa2 < 0 || sgpa2 > 10 ||
                    sgpa3 < 0 || sgpa3 > 10 || sgpa4 < 0 || sgpa4 > 10) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please check age and SGPA values.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                double cgpa = (sgpa1 + sgpa2 + sgpa3 + sgpa4) / 4.0;
                JOptionPane.showMessageDialog(frame, "CGPA computed: " + cgpa, "Compute CGPA", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values for age and SGPA.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doneButtonClicked() {
        try {
            String name = nameField.getText();
            String usn = usnField.getText();
            int age = Integer.parseInt(ageField.getText());
            String address = addressField.getText();
            double sgpa1 = Double.parseDouble(sgpa1Field.getText());
            double sgpa2 = Double.parseDouble(sgpa2Field.getText());
            double sgpa3 = Double.parseDouble(sgpa3Field.getText());
            double sgpa4 = Double.parseDouble(sgpa4Field.getText());
            String category = categoryField.getText();

            if (age <= 18 || age > 30 || sgpa1 < 0 || sgpa1 > 10 || sgpa2 < 0 || sgpa2 > 10 ||
                    sgpa3 < 0 || sgpa3 > 10 || sgpa4 < 0 || sgpa4 > 10) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please check age and SGPA values.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Student student = new Student(name, usn, age, address, sgpa1, sgpa2, sgpa3, sgpa4, category);
                studentMap.put(student.getStudentId(), student);
                JOptionPane.showMessageDialog(frame, "Student details added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields
                clearInputFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values for age and SGPA.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        usnField.setText("");
        ageField.setText("");
        addressField.setText("");
        sgpa1Field.setText("");
        sgpa2Field.setText("");
        sgpa3Field.setText("");
        sgpa4Field.setText("");
        categoryField.setText("");
    }

    private void displayButtonClicked() {
        displayArea.setText("");
        for (Map.Entry<Integer, Student> entry : studentMap.entrySet()) {
            displayArea.append(entry.getValue().toString() + "\n\n");
        }
    }
}
